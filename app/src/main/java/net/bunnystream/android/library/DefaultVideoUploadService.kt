package net.bunnystream.android.library

import android.net.Uri
import android.util.Log
import net.bunnystream.androidsdk.upload.VideoUploadListener
import net.bunnystream.androidsdk.upload.VideoUploader
import net.bunnystream.androidsdk.upload.model.UploadError

class DefaultVideoUploadService(
    private val videoUploader: VideoUploader
) : VideoUploadService {

    companion object {
        private const val TAG = "VideoUploadService"
    }

    override var uploadListener: VideoUploadListener? = null

    override fun uploadVideo(libraryId: Long, videoUri: Uri) {
        videoUploader.uploadVideo(libraryId, videoUri, object : VideoUploadListener {
            override fun onVideoUploadError(error: UploadError) {
                Log.d(TAG, "onVideoUploadError: $error")
                uploadListener?.onVideoUploadError(error)
            }

            override fun onVideoUploadDone() {
                Log.d(TAG, "onVideoUploadDone")
                uploadListener?.onVideoUploadDone()
            }

            override fun onVideoUploadStarted(uploadId: String) {
                Log.d(TAG, "onVideoUploadStarted: uploadId=$uploadId")
                uploadListener?.onVideoUploadStarted(uploadId)
            }

            override fun onUploadProgress(percentage: Int) {
                Log.d(TAG, "onUploadProgress: percentage=$percentage")
                uploadListener?.onUploadProgress(percentage)
            }
        })
    }
}