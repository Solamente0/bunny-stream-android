package net.bunnystream.androidsdk

import org.openapitools.client.apis.ManageCollectionsApi
import org.openapitools.client.apis.ManageVideosApi

interface IBunnyStreamSdk {

    /**
     * API endpoints for managing video collections
     */
    val collectionsApi: ManageCollectionsApi

    /**
     * API endpoints for managing videos
     */
    val videosApi: ManageVideosApi
}