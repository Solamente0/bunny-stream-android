package net.bunnystream.android.library

import android.net.Uri
import net.bunnystream.androidsdk.upload.service.UploadListener

interface VideoUploadService {

    var uploadListener: UploadListener?

    fun uploadVideo(libraryId: Long, videoUri: Uri)

    fun cancelUpload(uploadId: String)
}