package net.bunnystream.androidsdk

import android.content.Context
import kotlinx.coroutines.Dispatchers
import net.bunnystream.androidsdk.ktor.initHttpClient
import net.bunnystream.androidsdk.upload.DefaultVideoUploader
import net.bunnystream.androidsdk.upload.service.VideoUploaderService
import org.openapitools.client.apis.ManageCollectionsApi
import org.openapitools.client.apis.ManageVideosApi
import org.openapitools.client.infrastructure.ApiClient

class BunnyStreamSdk(context: Context, accessKey: String) : StreamSdk {

    init {
        ApiClient.apiKey["AccessKey"] = accessKey
    }

    private val ktorClient = initHttpClient(accessKey)

    private val videoUploaderService = VideoUploaderService(ktorClient)

    override val collectionsApi = ManageCollectionsApi()

    override val videosApi = ManageVideosApi()

    override val videoUploader = DefaultVideoUploader(
        context = context,
        videoUploadService = videoUploaderService,
        ioDispatcher = Dispatchers.IO,
        mainDispatcher = Dispatchers.Main
    )
}