package net.bunnystream.androidsdk

import android.content.Context
import arrow.core.Either
import kotlinx.coroutines.Dispatchers
import net.bunnystream.androidsdk.ktor.initHttpClient
import net.bunnystream.androidsdk.settings.data.DefaultSettingsRepository
import net.bunnystream.androidsdk.settings.domain.model.PlayerSettings
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

        const val baseApi = BuildConfig.BASE_API

        lateinit var cdnHostname: String
            private set

        var libraryId: Long = -1
            private set

        @Volatile
        private var instance: StreamSdk? = null

        fun initialize(context: Context, accessKey: String, cdnHostname: String, libraryId: Long) {
            instance = BunnyStreamSdk(
                context.applicationContext,
                accessKey,
            )

            this.cdnHostname = if(cdnHostname.startsWith("https://")){
                cdnHostname
            } else if(cdnHostname.startsWith("http://")) {
                cdnHostname.replace("http://", "https://", true)
            } else {
                "https://$cdnHostname"
            }
            this.libraryId = libraryId
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

    override val streamApi = StreamApi(baseApi)

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

    override val videoUploader = DefaultVideoUploader(
        context = context,
        videoUploadService = basicUploaderService,
        ioDispatcher = Dispatchers.IO,
        streamApi.videosApi
    )

    override val tusVideoUploader = DefaultVideoUploader(
        context = context,
        videoUploadService = tusVideoUploaderService,
        ioDispatcher = Dispatchers.IO,
        streamApi.videosApi
    )

    override val settingsRepository = DefaultSettingsRepository(
        httpClient = ktorClient,
        coroutineDispatcher = Dispatchers.IO
    )

    override suspend fun fetchPlayerSettings(libraryId: Long, videoId: String): Either<String, PlayerSettings> {
        return settingsRepository.fetchSettings(libraryId, videoId)
    }
}
