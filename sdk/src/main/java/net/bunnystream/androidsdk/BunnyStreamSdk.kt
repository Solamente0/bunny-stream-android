package net.bunnystream.androidsdk

import org.openapitools.client.apis.ManageCollectionsApi
import org.openapitools.client.apis.ManageVideosApi
import org.openapitools.client.infrastructure.ApiClient

class BunnyStreamSdk(accessKey: String) : IBunnyStreamSdk {

    init {
        ApiClient.apiKey["AccessKey"] = accessKey
    }

    override val collectionsApi = ManageCollectionsApi()

    override val videosApi = ManageVideosApi()
}