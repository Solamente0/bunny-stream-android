package net.bunnystream.androidsdk.upload

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import arrow.core.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.bunnystream.androidsdk.upload.model.FileInfo
import net.bunnystream.androidsdk.upload.model.HttpStatusCodes
import net.bunnystream.androidsdk.upload.model.UploadError
import net.bunnystream.androidsdk.upload.service.UploadService
import org.openapitools.client.apis.ManageVideosApi
import org.openapitools.client.infrastructure.ClientException
import org.openapitools.client.infrastructure.ServerException
import org.openapitools.client.models.CreateVideoModel
import java.util.UUID

class DefaultVideoUploader(
    private val context: Context,
    private val videoUploadService: UploadService,
    ioDispatcher: CoroutineDispatcher,
    mainDispatcher: CoroutineDispatcher,
) : VideoUploader {

    companion object {
        private const val TAG = "VideoUploader"
    }

    private val videosApi = ManageVideosApi()
    private val scope = CoroutineScope(ioDispatcher)
    private val mainScope = CoroutineScope(mainDispatcher)

    private val uploadsInProgress: MutableMap<String, Job> = mutableMapOf()

    override fun uploadVideo(libraryId: Long, videoUri: Uri, listener: VideoUploadListener) {

        val inputStream = context.contentResolver.openInputStream(videoUri)

        if(inputStream == null) {
            mainScope.launch {
                listener.onVideoUploadError(UploadError.ErrorReadingFile)
            }
            return
        }

        val fileInfo = getFileInfo(videoUri)

        Log.d(TAG, "fileInfo: $fileInfo")

        val title = fileInfo?.fileName ?: UUID.randomUUID().toString()

        scope.launch {
            val videoId: String?
            try {
                videoId = createVideo(libraryId, title)
            } catch (e: Exception) {
                Log.e(TAG, "could not create video: ${e.message}")
                e.printStackTrace()

                val error = when(e) {
                    is ClientException -> {
                        when(e.statusCode) {
                            HttpStatusCodes.UNAUTHORIZED -> UploadError.Unauthorized
                            HttpStatusCodes.NOT_FOUND -> UploadError.VideoNotFound
                            else -> UploadError.UnknownError("${e.statusCode} ${e.message}")
                        }
                    }
                    is ServerException -> UploadError.ServerError
                    else ->  UploadError.UnknownError(e.message ?: e.toString())
                }

                mainScope.launch {
                    listener.onVideoUploadError(error)
                }
                return@launch
            }

            if(videoId.isNullOrEmpty()) {
                mainScope.launch {
                    listener.onVideoUploadError(UploadError.ErrorCreating)
                }
                return@launch
            }

            val uploadId = UUID.randomUUID().toString()

            val uploadJob = scope.launch {

                val response = videoUploadService.upload(
                    "https://video.bunnycdn.com/library/$libraryId/videos/$videoId",
                    inputStream
                ) { progress ->
                    mainScope.launch {
                        listener.onUploadProgress(progress)
                    }
                }

                when(response){
                    is Either.Right -> mainScope.launch {
                        listener.onVideoUploadDone()
                    }
                    is Either.Left -> mainScope.launch {
                        listener.onVideoUploadError(response.value)
                    }
                }

                uploadsInProgress.remove(uploadId)
                Log.d(TAG, "uploadsInProgress: $uploadsInProgress")
            }

            synchronized(uploadsInProgress) {
                uploadsInProgress[uploadId] = uploadJob
                mainScope.launch { listener.onVideoUploadStarted(uploadId) }
            }

            Log.d(TAG, "uploadsInProgress: $uploadsInProgress")
        }
    }

    override fun cancelUpload(uploadId: String) {
        Log.d(TAG, "cancelUpload: $uploadId")
        synchronized(uploadsInProgress) {
            uploadsInProgress.remove(uploadId)?.cancel()
        }
        Log.d(TAG, "uploadsInProgress: $uploadsInProgress")
    }

    private fun createVideo(
        libraryId: Long,
        title: String,
        collectionId: String? = null,
        thumbnailTime: Int? = null
    ): String? {
        val createVideoModel = CreateVideoModel(
            title = title,
            collectionId = collectionId,
            thumbnailTime = thumbnailTime
        )
        val result = videosApi.videoCreateVideo(
            libraryId = libraryId,
            createVideoModel = createVideoModel
        )

        return result.guid
    }

    private fun getFileInfo(uri: Uri): FileInfo? {

        val cursor = context.contentResolver.query(uri, null, null, null, null) ?: return null

        cursor.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
            it.moveToFirst()

            val name = it.getString(nameIndex)
            val size = it.getLong(sizeIndex)

            return FileInfo(name, size)
        }
    }
}