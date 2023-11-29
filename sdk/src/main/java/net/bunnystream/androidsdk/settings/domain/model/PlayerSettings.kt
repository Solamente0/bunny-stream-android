package net.bunnystream.androidsdk.settings.domain.model

data class PlayerSettings(
    val thumbnailUrl: String,
    val controls: String,
    val keyColor: Int,
    val captionsFontSize: Int,
    val captionsFontColor: Int?,
    val captionsBackgroundColor: Int?,
    val uiLanguage: String,
    val showHeatmap: Boolean,
    val fontFamily: String,
    val playbackSpeeds: String,
    val drmEnabled: Boolean,
    val vastTagUrl: String?
)
