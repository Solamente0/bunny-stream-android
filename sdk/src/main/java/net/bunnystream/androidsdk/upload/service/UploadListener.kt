package net.bunnystream.androidsdk.upload.service

import net.bunnystream.androidsdk.upload.model.UploadError

/**
 * Collection of callbacks to get info about video uploads
 */
interface UploadListener {
    /**
     * Called when video uplaod fails
     * @param error
     * @see UploadError
     */
    fun onUploadError(error: UploadError)

    /**
     * Called when video upload is finished
     */
    fun onUploadDone()

    /**
     * Called when video upload starts
     * @param uploadId ID of the upload that can be used to cancel the upload
     */
    fun onUploadStarted(uploadId: String)

    /**
     * Called when upload progress changes
     */
    fun onProgressUpdated(percentage: Int)

    /**
     * Called when upload gets cancelled
     */
    fun onUploadCancelled()
}