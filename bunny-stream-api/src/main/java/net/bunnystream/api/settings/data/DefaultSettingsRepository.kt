package net.bunnystream.api.settings.data

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import net.bunnystream.api.BunnyStreamApi
import net.bunnystream.api.settings.data.model.PlayerSettingsResponse
import net.bunnystream.api.settings.domain.SettingsRepository
import net.bunnystream.api.settings.domain.model.PlayerSettings

class DefaultSettingsRepository(
    private val httpClient: HttpClient,
    private val coroutineDispatcher: CoroutineDispatcher
) : SettingsRepository {

    override suspend fun fetchSettings(libraryId: Long, videoId: String):
            Either<String, PlayerSettings> = withContext(coroutineDispatcher) {
        val endpoint = "${BunnyStreamApi.baseApi}/library/$libraryId/videos/$videoId/play"

        return@withContext try {
            val response = httpClient.get(endpoint)
            when (response.status.value) {
                200 -> {
                    val result: PlayerSettingsResponse = response.body()
                    Either.Right(result.toModel())
                }
                401 -> Either.Left("Authorization required Unauthorized")
                403 -> Either.Left("Forbidden")
                404 -> Either.Left("Not Found")
                else -> Either.Left("Error: ${response.status.value}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Either.Left("Unknown exception: ${e.message}")
        }
    }
}