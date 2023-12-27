package net.bunnystream.androidsdk.upload.service.basic

import android.util.Log
import kotlinx.coroutines.Job
import net.bunnystream.androidsdk.upload.service.UploadListener
import net.bunnystream.androidsdk.upload.service.UploadRequest

class BasicUploadRequest(
    override val libraryId: Long,
    override val videoId: String,
    private val uploadJob: Job,
    private val listener: UploadListener
) : UploadRequest(libraryId, videoId) {

    @Suppress("PrintStackTrace")
    override suspend fun cancel() {
        Log.d("BasicUploadRequest", "cancel")
        try {
            uploadJob.cancel()
        } catch (e: Exception){
            Log.e("BasicUploadRequest","cancel exception: ${e.message}")
            e.printStackTrace()
        }
        listener.onUploadCancelled(videoId)
    }
}