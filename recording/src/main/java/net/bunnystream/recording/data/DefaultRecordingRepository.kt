package net.bunnystream.recording.data

import arrow.core.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import net.bunnystream.androidsdk.BuildConfig
import net.bunnystream.androidsdk.BunnyStreamSdk
import net.bunnystream.recording.domain.RecordingRepository
import org.openapitools.client.infrastructure.ApiClient
import org.openapitools.client.models.VideoCreateVideoRequest

class DefaultRecordingRepository(
   private val coroutineDispatcher: CoroutineDispatcher
) : RecordingRepository {

    companion object {
        private const val TAG = "DefaultRecordingRepository"
    }

    override suspend fun prepareRecording(libraryId: Long): Either<String, String> = withContext(coroutineDispatcher) {
        val createVideoRequest = VideoCreateVideoRequest(
            title = "recording-${System.currentTimeMillis()}",
            collectionId = null,
            thumbnailTime = null
        )

        try {
            val result = BunnyStreamSdk.getInstance().streamApi.videosApi.videoCreateVideo(
                libraryId = libraryId,
                videoCreateVideoRequest = createVideoRequest
            )

            val endpoint = "${BuildConfig.RTMP_ENDPOINT}?vid=${result.guid}&accessKey=${ApiClient.apiKey["AccessKey"]}&lib=$libraryId"

            Either.Right(endpoint)
        } catch (e: Exception) {
            Either.Left(e.message ?: e.toString())
        }
    }
}