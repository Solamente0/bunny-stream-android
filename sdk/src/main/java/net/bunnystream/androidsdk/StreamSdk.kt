package net.bunnystream.androidsdk

import net.bunnystream.androidsdk.upload.VideoUploader
import org.openapitools.client.apis.ManageCollectionsApi
import org.openapitools.client.apis.ManageVideosApi

interface StreamSdk {

    /**
     * Check if AccessKey has been provided
     */
    val isInitialized: Boolean

    /**
     * API endpoints for managing video collections
     * @see ManageCollectionsApi
     */
    val collectionsApi: ManageCollectionsApi

    /**
     * API endpoints for managing videos
     * @see ManageVideosApi
     */
    val videosApi: ManageVideosApi

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