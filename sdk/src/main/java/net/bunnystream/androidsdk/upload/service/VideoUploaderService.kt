package net.bunnystream.androidsdk.upload.service

import android.util.Log
import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.plugins.onUpload
import io.ktor.client.plugins.timeout
import io.ktor.client.request.preparePut
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import net.bunnystream.androidsdk.upload.model.HttpStatusCodes
import net.bunnystream.androidsdk.upload.model.StreamContent
import net.bunnystream.androidsdk.upload.model.UploadError
import java.io.InputStream
import kotlin.time.Duration

class VideoUploaderService(private val httpClient: HttpClient): UploadService {

    companion object {
        private const val TAG = "VideoUploaderService"
    }

    @Suppress("PrintStackTrace")
    override suspend fun upload(
        url: String, inputStream: InputStream, onProgress: (Int) -> Unit
    ): Either<UploadError, Any> {
        var uploadProcess = 0
        val request = httpClient.preparePut(url) {
            contentType(ContentType.Application.OctetStream)
            setBody(StreamContent(inputStream))
            timeout {
                requestTimeoutMillis = Duration.INFINITE.inWholeMilliseconds
            }
            onUpload { bytesSentTotal, contentLength ->
                val percentage = ((bytesSentTotal / contentLength.toFloat()) * 100).toInt()
                if(percentage != uploadProcess) {
                    uploadProcess = percentage
                    onProgress(percentage)
                }
            }
        }

        return try {
            val response = request.execute()

            if(response.status.isSuccess()) {
                Either.Right(Any())
            } else {
                when (response.status.value) {
                    HttpStatusCodes.UNAUTHORIZED -> Either.Left(UploadError.Unauthorized)
                    HttpStatusCodes.NOT_FOUND -> Either.Left(UploadError.VideoNotFound)
                    HttpStatusCodes.SERVER_ERROR -> Either.Left(UploadError.ServerError)
                    else -> Either.Left(UploadError.UnknownError(response.status.toString()))
                }
            }

        } catch (e: Exception) {
            Log.w(TAG, "error uploading: ${e.message}")
            e.printStackTrace()
            Either.Left(UploadError.UnknownError(e.message ?: e.toString()))
        }
    }
}