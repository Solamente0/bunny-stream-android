package net.bunnystream.androidsdk.settings.data

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import net.bunnystream.androidsdk.BunnyStreamSdk
import net.bunnystream.androidsdk.settings.data.model.PlayerSettingsResponse
import net.bunnystream.androidsdk.settings.domain.SettingsRepository
import net.bunnystream.androidsdk.settings.domain.model.PlayerSettings

class DefaultSettingsRepository(
    private val httpClient: HttpClient,
    private val coroutineDispatcher: CoroutineDispatcher
) : SettingsRepository {

    override suspend fun fetchSettings(libraryId: Long, videoId: String): Either<String, PlayerSettings> = withContext(coroutineDispatcher) {
        val endpoint = "${BunnyStreamSdk.baseApi}/library/$libraryId/videos/$videoId/play"

        return@withContext try {
            val result: PlayerSettingsResponse = httpClient.get(endpoint).body()
            Either.Right(result.toModel())
        } catch (e: Exception) {
            e.printStackTrace()
            Either.Left("")
        }
    }
}