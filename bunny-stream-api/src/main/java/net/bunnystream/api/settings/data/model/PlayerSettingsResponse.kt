package net.bunnystream.api.settings.data.model

import android.graphics.Color
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.bunnystream.api.settings.domain.model.PlayerSettings
import net.bunnystream.api.settings.toColorOrDefault
import net.bunnystream.api.settings.PlaybackSpeedManager

@Serializable
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

    // "playbackSpeeds": "0.5,0.75,1,1.25,1.5,1.75,2,3,4"
    @SerialName("playbackSpeeds")
    val playbackSpeeds: String? , // "0.5,0.75,1,1.25,1.5,1.75,2,3,4"

    @SerialName("enableDRM")
    val drmEnabled: Boolean,

    @SerialName("vastTagUrl")
    val vastTagUrl: String?,

    @SerialName("captionsPath")
    val captionsPath: String,

    @SerialName("seekPath")
    val seekPath: String,

    @SerialName("videoPlaylistUrl")
    val videoUrl: String
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
        playbackSpeeds = parsePlaybackSpeeds(),
        drmEnabled = drmEnabled,
        vastTagUrl = vastTagUrl,
        captionsPath = captionsPath,
        seekPath = seekPath,
        videoUrl = videoUrl
    )

    private fun parsePlaybackSpeeds(): List<Float> {
        return PlaybackSpeedManager().parsePlaybackSpeeds(playbackSpeeds)
    }
}
