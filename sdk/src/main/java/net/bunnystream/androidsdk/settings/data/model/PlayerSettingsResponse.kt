package net.bunnystream.androidsdk.settings.data.model

import android.graphics.Color
import kotlinx.serialization.SerialName
import net.bunnystream.androidsdk.settings.domain.model.PlayerSettings
import net.bunnystream.androidsdk.settings.toColorOrDefault

data class PlayerSettingsResponse(
    @SerialName("thumbnailUrl")
    val thumbnailUrl: String,

    @SerialName("controls")
    val controls: String,

    @SerialName("playerKeyColor")
    val keyColor: String,

    @SerialName("captionsFontSize")
    val captionsFontSize: Int,

    @SerialName("captionsFontColor")
    val captionsFontColor: String?,

    @SerialName("captionsBackground")
    val captionsBackgroundColor: String?,

    @SerialName("uiLanguage")
    val uiLanguage: String,

    @SerialName("showHeatmap")
    val showHeatmap: Boolean,

    @SerialName("fontFamily")
    val fontFamily: String,

    @SerialName("playbackSpeeds")
    val playbackSpeeds: String,

    @SerialName("enableDRM")
    val drmEnabled: Boolean,

    @SerialName("vastTagUrl")
    val vastTagUrl: String?
) {
    fun toModel() = PlayerSettings(
        thumbnailUrl = thumbnailUrl,
        controls = controls,
        keyColor = keyColor.toColorOrDefault(Color.WHITE)!!,
        captionsFontSize = captionsFontSize,
        captionsFontColor = captionsFontColor?.toColorOrDefault(null),
        captionsBackgroundColor = captionsBackgroundColor?.toColorOrDefault(null),
        uiLanguage = uiLanguage,
        showHeatmap = showHeatmap,
        fontFamily = fontFamily,
        playbackSpeeds = playbackSpeeds,
        drmEnabled = drmEnabled,
        vastTagUrl = vastTagUrl
    )
}
