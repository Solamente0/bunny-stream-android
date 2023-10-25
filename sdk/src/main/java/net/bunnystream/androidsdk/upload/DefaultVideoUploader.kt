package net.bunnystream.androidsdk.upload

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import net.bunnystream.androidsdk.api.ManageVideosApi
import net.bunnystream.androidsdk.upload.model.FileInfo
import net.bunnystream.androidsdk.upload.model.HttpStatusCodes
import net.bunnystream.androidsdk.upload.model.UploadError
import net.bunnystream.androidsdk.upload.service.UploadListener
import net.bunnystream.androidsdk.upload.service.UploadRequest
import net.bunnystream.androidsdk.upload.service.UploadService
import org.openapitools.client.infrastructure.ClientException
import org.openapitools.client.infrastructure.ServerException
import org.openapitools.client.models.CreateVideoModel
import java.util.UUID

class DefaultVideoUploader(
    private val context: Context,
    private val videoUploadService: UploadService,
    ioDispatcher: CoroutineDispatcher,
) : VideoUploader {

    companion object {
        private const val TAG = "DefaultVideoUploader"
    }

    private val videosApi = ManageVideosApi()

    private val supervisorJob = SupervisorJob()
    private val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        exception.printStackTrace()
        Log.d(TAG, "CoroutineExceptionHandler: context=$context exception=$exception")
    }
    private val scope = CoroutineScope(ioDispatcher + exceptionHandler + supervisorJob)

    private val lock = Any()
    private val uploadsInProgress: MutableMap<String, UploadRequest> = mutableMapOf()

    override fun uploadVideo(libraryId: Long, videoUri: Uri, listener: UploadListener) {
        Log.d(TAG, "uploadVideo libraryId=$libraryId videoUri=$videoUri")

        val fileInfo = getFileInfo(videoUri)

        if(fileInfo == null) {
            listener.onUploadError(UploadError.ErrorReadingFile)
            return
        }

        Log.d(TAG, "fileInfo: $fileInfo")

        scope.launch {
            val videoId: String?
            try {
                videoId = createVideo(libraryId, fileInfo.fileName)
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
                listener.onUploadError(error)
                return@launch
            }

            if(videoId.isNullOrEmpty()) {
                listener.onUploadError(UploadError.ErrorCreating)
                return@launch
            }

            val uploadId = UUID.randomUUID().toString()

            val request = videoUploadService.upload(
                libraryId, videoId, fileInfo, object : UploadListener {
                    override fun onProgressUpdated(percentage: Int) {
                        Log.d(TAG, "onProgressUpdated: $percentage")
                        listener.onProgressUpdated(percentage)
                    }

                    override fun onUploadDone() {
                        Log.d(TAG, "onUploadDone")
                        listener.onUploadDone()
                    }

                    override fun onUploadStarted(uploadId: String) {
                        Log.d(TAG, "onUploadStarted uploadId=$uploadId")
                    }

                    override fun onUploadError(error: UploadError) {
                        Log.d(TAG, "onUploadError: $error")
                        listener.onUploadError(error)
                    }

                    override fun onUploadCancelled() {
                        Log.d(TAG, "onUploadCancelled")
                        listener.onUploadCancelled()
                    }
                }
            )

            if(request != null) {
                synchronized(lock) {
                    uploadsInProgress[uploadId] = request
                    listener.onUploadStarted(uploadId)
                }
            }

            Log.d(TAG, "uploadsInProgress: $uploadsInProgress")
        }
    }

    override fun cancelUpload(uploadId: String) {
        Log.d(TAG, "cancelUpload: $uploadId")

        val request: UploadRequest?

        synchronized(lock) {
            request = uploadsInProgress.remove(uploadId)
        }

        if(request == null){
            Log.w(TAG, "Cannot cancel, upload ID $uploadId not found")
            return
        }

        scope.launch { request.cancel() }

        scope.launch { deleteVideo(request.libraryId, request.videoId) }
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

    @Suppress("PrintStackTrace")
    private fun deleteVideo(libraryId: Long, videoId: String) {
        Log.d(TAG, "deleteVideo libraryId=$libraryId videoId=$videoId")
        try {
            val result = videosApi.videoDeleteVideo(libraryId, videoId)
            if(result.success) {
                Log.d(TAG, "Video deleted")
            } else {
                Log.e(TAG, "Error deleting video: $result")
            }
        } catch (e: Exception) {
            Log.e(TAG, "deleteVideo exception: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun getFileInfo(uri: Uri): FileInfo? {

        val inputStream = context.contentResolver.openInputStream(uri) ?: return null

        val cursor = context.contentResolver.query(uri, null, null, null, null) ?: return null

        cursor.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
            it.moveToFirst()

            val name = it.getString(nameIndex)
            val size = it.getLong(sizeIndex)

            return FileInfo(name, size, inputStream)
        }
    }
}