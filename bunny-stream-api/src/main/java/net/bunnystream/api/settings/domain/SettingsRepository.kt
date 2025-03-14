package net.bunnystream.api.settings.domain

import arrow.core.Either
import net.bunnystream.api.settings.domain.model.PlayerSettings

interface SettingsRepository {
    suspend fun fetchSettings(libraryId: Long, videoId: String): Either<String, PlayerSettings>
}