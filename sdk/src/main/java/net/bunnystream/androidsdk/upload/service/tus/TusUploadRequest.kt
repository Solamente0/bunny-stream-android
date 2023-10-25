package net.bunnystream.androidsdk.upload.service.tus

import android.util.Log
import io.tus.java.client.TusUploader
import net.bunnystream.androidsdk.upload.service.UploadListener
import net.bunnystream.androidsdk.upload.service.UploadRequest

class TusUploadRequest(
    override val libraryId: Long,
    override val videoId: String,
    private val uploader: TusUploader,
    private val listener: UploadListener
) : UploadRequest(libraryId, videoId) {

    @Suppress("PrintStackTrace")
    override suspend fun cancel() {
        Log.d("TusUploadRequest", "cancel")
        try {
            uploader.finish()
        } catch (e: Exception){
            Log.e("TusUploadRequest", "cancel exception: ${e.message}")
            e.printStackTrace()
        }
        listener.onUploadCancelled()
    }
}