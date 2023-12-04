package net.bunnystream.androidsdk

import net.bunnystream.androidsdk.api.ManageVideosApi
import net.bunnystream.androidsdk.upload.VideoUploader

interface StreamSdk {
    /**
     * API endpoints for managing videos and collections
     * @see ManageVideosApi
     */
    val streamApi: StreamApi

    /**
     * Component for managing video uploads
     * @see VideoUploader
     */
    val videoUploader: VideoUploader

    /**
     * Component for managing TUS video uploads
     * @see VideoUploader
     */
    val tusVideoUploader: VideoUploader
}