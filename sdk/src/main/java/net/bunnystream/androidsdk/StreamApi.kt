package net.bunnystream.androidsdk

import net.bunnystream.androidsdk.api.ManageCollectionsApi
import net.bunnystream.androidsdk.api.ManageVideosApi

class StreamApi(private val baseApi: String) {

    val collectionsApi = ManageCollectionsApi(baseApi)

    val videosApi = ManageVideosApi(baseApi)
}