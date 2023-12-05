package net.bunnystream.stream.data

import arrow.core.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import net.bunnystream.androidsdk.BuildConfig
import net.bunnystream.androidsdk.BunnyStreamSdk
import net.bunnystream.stream.domain.StreamRepository
import org.openapitools.client.infrastructure.ApiClient
import org.openapitools.client.models.CreateVideoModel

class DefaultStreamRepository(
   private val coroutineDispatcher: CoroutineDispatcher
) : StreamRepository {

    companion object {
        private const val TAG = "DefaultStreamRepository"
    }

    override suspend fun prepareStreaming(libraryId: Long): Either<String, String> = withContext(coroutineDispatcher) {
        val createVideoModel = CreateVideoModel(
            title = "stream-${System.currentTimeMillis()}",
            collectionId = null,
            thumbnailTime = null
        )

        try {
            val result = BunnyStreamSdk.getInstance().streamApi.videosApi.videoCreateVideo(
                libraryId = libraryId,
                createVideoModel = createVideoModel
            )

            val endpoint = "${BuildConfig.RTMP_ENDPOINT}?vid=${result.guid}&accessKey=${ApiClient.apiKey["AccessKey"]}&lib=$libraryId"

            Either.Right(endpoint)
        } catch (e: Exception) {
            Either.Left(e.message ?: e.toString())
        }
    }
}