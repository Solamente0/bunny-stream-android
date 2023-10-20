package net.bunnystream.androidsdk.upload.service.tus

import android.content.SharedPreferences
import android.util.Log
import io.tus.android.client.TusPreferencesURLStore
import io.tus.java.client.TusClient
import io.tus.java.client.TusUpload
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import net.bunnystream.androidsdk.BuildConfig
import net.bunnystream.androidsdk.upload.model.FileInfo
import net.bunnystream.androidsdk.upload.model.UploadError
import net.bunnystream.androidsdk.upload.service.UploadListener
import net.bunnystream.androidsdk.upload.service.UploadRequest
import net.bunnystream.androidsdk.upload.service.UploadService
import java.net.URL
import java.security.MessageDigest
import java.util.UUID

class TusUploaderService(
    private val preferences: SharedPreferences,
    private val chunkSize: Int,
    private val accessKey: String,
    private val dispatcher: CoroutineDispatcher
) : UploadService {

    companion object {
        private const val TAG = "TusUploaderService"
    }

    private val supervisorJob = SupervisorJob()

    private val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        exception.printStackTrace()
        Log.d(TAG, "CoroutineExceptionHandler: context=$context exception=$exception")
    }

    private val scope = CoroutineScope(dispatcher + exceptionHandler + supervisorJob)

    override suspend fun upload(
        libraryId: Long, videoId: String, fileInfo: FileInfo, listener: UploadListener
    ): UploadRequest {

        val tusClient = TusClient()
        tusClient.enableResuming(TusPreferencesURLStore(preferences))
        tusClient.uploadCreationURL = URL(BuildConfig.TUS_UPLOAD_ENDPOINT)

        val upload = TusUpload()
        upload.size = fileInfo.size
        upload.inputStream = fileInfo.inputStream
        upload.metadata = mapOf(
            "filetype" to "video/*",
            "title" to videoId
        )
        upload.fingerprint = UUID.randomUUID().toString()

        val expire = System.currentTimeMillis() / 1000 + 3600
        val signature = "$libraryId$accessKey$expire$videoId"

        tusClient.headers = mapOf(
            "AuthorizationSignature" to sha256(signature),
            "AuthorizationExpire" to expire.toString(),
            "LibraryId" to libraryId.toString(),
            "VideoId" to videoId
        )

        val uploader = tusClient.resumeOrCreateUpload(upload)
        uploader.chunkSize = chunkSize

        scope.launch {
            var uploadProgress = 0
            try {
                do {
                    val bytesUploaded = uploader.offset
                    val progress = ((bytesUploaded.toDouble() / upload.size) * 100).toInt()

                    if(progress != uploadProgress) {
                        uploadProgress = progress
                        listener.onProgressUpdated(progress)
                    }
                } while (uploader.uploadChunk() > -1)

                uploader.finish()
                Log.d(TAG, "upload done")
                listener.onUploadDone()
            } catch (e: Exception) {
                Log.e(TAG, "error uploading: ${e.message}")
                e.printStackTrace()
                listener.onUploadError(UploadError.UnknownError(e.message ?: e.toString()))
            }
        }

        return TusUploadRequest(libraryId, videoId, uploader, listener)
    }

    private fun sha256(input: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        md.update(input.toByteArray())
        val digest = md.digest()
        return digest.fold("") { str, b -> str + "%02x".format(b) }
    }
}