package net.bunnystream.android.library

import android.net.Uri
import net.bunnystream.androidsdk.upload.VideoUploadListener

interface VideoUploadService {

    var uploadListener: VideoUploadListener?

    fun uploadVideo(libraryId: Long, videoUri: Uri)
}