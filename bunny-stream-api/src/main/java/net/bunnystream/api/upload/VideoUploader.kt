package net.bunnystream.api.upload

import android.net.Uri
import net.bunnystream.api.upload.service.UploadListener

/**
 * Component that manages video uploads
 */
interface VideoUploader {

    /**
     * Uploads video represented by Uri
     * @param libraryId Video library ID
     * @param videoUri Uri of vide to be uploaded
     * @param listener listener to keep get info about upload
     * @see UploadListener
     */
    fun uploadVideo(libraryId: Long, videoUri: Uri, listener: UploadListener)

    /**
     * Cancels video upload
     * @param uploadId ID of the upload received from [UploadListener.onUploadStarted]
     */
    fun cancelUpload(uploadId: String)
}