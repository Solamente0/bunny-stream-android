package net.bunnystream.android.library

import android.net.Uri
import android.util.Log
import net.bunnystream.androidsdk.upload.service.UploadListener
import net.bunnystream.androidsdk.upload.VideoUploader
import net.bunnystream.androidsdk.upload.model.UploadError

class DefaultVideoUploadService(
    private val videoUploader: VideoUploader
) : VideoUploadService {

    companion object {
        private const val TAG = "DefaultVideoUploadService"
    }

    override var uploadListener: UploadListener? = null

    override fun uploadVideo(libraryId: Long, videoUri: Uri) {
        Log.d(TAG, "uploadVideo videoUri=$videoUri videoUploader=$videoUploader")
        videoUploader.uploadVideo(libraryId, videoUri, object : UploadListener {
            override fun onUploadError(error: UploadError) {
                Log.d(TAG, "onVideoUploadError: $error")
                uploadListener?.onUploadError(error)
            }

            override fun onUploadDone() {
                Log.d(TAG, "onVideoUploadDone")
                uploadListener?.onUploadDone()
            }

            override fun onUploadStarted(uploadId: String) {
                Log.d(TAG, "onVideoUploadStarted: uploadId=$uploadId")
                uploadListener?.onUploadStarted(uploadId)
            }

            override fun onProgressUpdated(percentage: Int) {
                Log.d(TAG, "onUploadProgress: percentage=$percentage")
                uploadListener?.onProgressUpdated(percentage)
            }

            override fun onUploadCancelled() {
                Log.d(TAG, "onUploadProgress: onVideoUploadCancelled")
                uploadListener?.onUploadCancelled()
            }
        })
    }

    override fun cancelUpload(uploadId: String) {
        Log.d(TAG, "cancelUpload videoUploader=$videoUploader")
        videoUploader.cancelUpload(uploadId)
    }
}