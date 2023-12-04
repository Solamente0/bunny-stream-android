package net.bunnystream.androidsdk

import net.bunnystream.androidsdk.api.ManageCollectionsApi
import net.bunnystream.androidsdk.api.ManageVideosApi

class StreamApi {

    val collectionsApi = ManageCollectionsApi()

    val videosApi = ManageVideosApi()
}