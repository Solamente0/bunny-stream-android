package net.bunnystream.androidsdk.upload

import android.net.Uri

/**
 * Component that manages video uploads
 */
interface VideoUploader {

    /**
     * Uploads video represented by Uri
     * @param libraryId Video library ID
     * @param videoUri Uri of vide to be uploaded
     * @param listener listener to keep get info about upload
     * @see VideoUploadListener
     */
    fun uploadVideo(libraryId: Long, videoUri: Uri, listener: VideoUploadListener)

    /**
     * Cancels video upload
     * @param uploadId ID of the upload received from [VideoUploadListener.onVideoUploadStarted]
     */
    fun cancelUpload(uploadId: String)
}