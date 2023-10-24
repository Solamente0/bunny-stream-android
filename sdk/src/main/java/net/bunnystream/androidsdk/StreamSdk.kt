package net.bunnystream.androidsdk

import net.bunnystream.androidsdk.upload.VideoUploader
import org.openapitools.client.apis.ManageCollectionsApi
import org.openapitools.client.apis.ManageVideosApi

interface StreamSdk {

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
}