package net.bunnystream.androidsdk

import arrow.core.Either
import net.bunnystream.androidsdk.api.ManageCollectionsApi
import net.bunnystream.androidsdk.api.ManageVideosApi
import net.bunnystream.androidsdk.settings.domain.model.PlayerSettings
import net.bunnystream.androidsdk.settings.domain.SettingsRepository
import net.bunnystream.androidsdk.upload.VideoUploader

interface StreamSdk {
    /**
     * API endpoints for managing video collections
     * @see ManageCollectionsApi
     */
    val collectionsApi: ManageCollectionsApi

    /**
     * API endpoints for managing videos
     * @see ManageVideosApi
     */
    val videosApi: ManageVideosApi

    /**
     * Component for managing video uploads
     * @see VideoUploader
     */
    val videoUploader: VideoUploader

    /**
     * Component for managing TUS video uploads
     * @see VideoUploader
     */
    val tusVideoUploader: VideoUploader

    val settingsRepository: SettingsRepository

    suspend fun fetchPlayerSettings(libraryId: Long, videoId: String): Either<String, PlayerSettings>
}