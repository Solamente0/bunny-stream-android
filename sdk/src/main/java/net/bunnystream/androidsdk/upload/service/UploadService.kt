package net.bunnystream.androidsdk.upload.service

import arrow.core.Either
import net.bunnystream.androidsdk.upload.model.UploadError
import java.io.InputStream

/**
 * Component that does executes upload
 */
interface UploadService {

    /**
     * Uploads content in for of [InputStream] to provided url
     * @param target URL
     * @param inputStream content to be uploaded
     * @param onProgress function that's called when progress changes
     * @return [Either] containing error if upload fails or [Any] on success
     */
    suspend fun upload(
        url: String, inputStream: InputStream, onProgress: (Int) -> Unit
    ): Either<UploadError, Any>
}