package net.bunnystream.androidsdk.upload

import net.bunnystream.androidsdk.upload.model.UploadError

/**
 * Collection of callbacks to get info about video uploads
 */
interface VideoUploadListener {
    /**
     * Called when video uplaods fails
     * @param error
     * @see UploadError
     */
    fun onVideoUploadError(error: UploadError)

    /**
     * Called when video upload is finished
     */
    fun onVideoUploadDone()

    /**
     * Called when video upload starts
     * @param uploadId ID of the upload that can be used to cancel the upload
     */
    fun onVideoUploadStarted(uploadId: String)

    /**
     * Called when upload progress changes
     */
    fun onUploadProgress(percentage: Int)
}