package net.bunnystream.stream

import net.bunnystream.androidsdk.BunnyStreamSdk
import org.openapitools.client.models.CreateVideoModel

class StreamRepository {

    companion object {
        private const val TAG = "StreamRepository"
    }

    fun prepareStreaming(libraryId: Long): String {
        val createVideoModel = CreateVideoModel(
            title = "stream-${System.currentTimeMillis()}",
            collectionId = null,
            thumbnailTime = null
        )

        val result = BunnyStreamSdk.getInstance().videosApi.videoCreateVideo(
            libraryId = libraryId,
            createVideoModel = createVideoModel
        )

        return result.guid!!
    }
}