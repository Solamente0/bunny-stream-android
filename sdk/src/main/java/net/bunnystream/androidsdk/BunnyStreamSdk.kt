package net.bunnystream.androidsdk

import android.content.Context
import kotlinx.coroutines.Dispatchers
import net.bunnystream.androidsdk.api.ManageCollectionsApi
import net.bunnystream.androidsdk.api.ManageVideosApi
import net.bunnystream.androidsdk.ktor.initHttpClient
import net.bunnystream.androidsdk.upload.DefaultVideoUploader
import net.bunnystream.androidsdk.upload.service.basic.BasicUploaderService
import net.bunnystream.androidsdk.upload.service.tus.TusUploaderService
import org.openapitools.client.infrastructure.ApiClient

class BunnyStreamSdk private constructor(
    context: Context,
    accessKey: String,
) : StreamSdk {

    companion object {
        private const val TUS_PREFS_FILE = "tusPrefs"

        lateinit var cdnHostname: String
            private set

        @Volatile
        private var instance: StreamSdk? = null

        fun initialize(context: Context, accessKey: String, cdnHostname: String) {
            instance = BunnyStreamSdk(
                context.applicationContext,
                accessKey,
            )

            this.cdnHostname = cdnHostname
            ApiClient.apiKey["AccessKey"] = accessKey
        }

        fun getInstance(): StreamSdk {
            return instance!!
        }

        fun isInitialized(): Boolean {
            return instance != null
        }

        fun release() {
            instance = null
        }
    }

    private val prefs = context.getSharedPreferences(TUS_PREFS_FILE, Context.MODE_PRIVATE)

    private val ktorClient = initHttpClient(accessKey)

    private val basicUploaderService = BasicUploaderService(
        ktorClient,
        Dispatchers.IO
    )

    private val tusVideoUploaderService = TusUploaderService(
        preferences = prefs,
        chunkSize = 1024,
        accessKey = accessKey,
        dispatcher = Dispatchers.IO
    )

    override val collectionsApi = ManageCollectionsApi()

    override val videosApi = ManageVideosApi()

    override val videoUploader = DefaultVideoUploader(
        context = context,
        videoUploadService = basicUploaderService,
        ioDispatcher = Dispatchers.IO,
    )

    override val tusVideoUploader = DefaultVideoUploader(
        context = context,
        videoUploadService = tusVideoUploaderService,
        ioDispatcher = Dispatchers.IO,
    )
}
