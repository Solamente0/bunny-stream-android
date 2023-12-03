package net.bunnystream.player.common

import androidx.annotation.FloatRange
import androidx.media3.common.Player
import androidx.media3.ui.PlayerView
import net.bunnystream.androidsdk.settings.domain.model.PlayerSettings
import net.bunnystream.player.PlayerStateListener
import net.bunnystream.player.model.SeekThumbnail
import net.bunnystream.player.model.SubtitleInfo
import net.bunnystream.player.model.Subtitles
import net.bunnystream.player.model.VideoQuality
import net.bunnystream.player.model.VideoQualityOptions
import org.openapitools.client.models.VideoModel

interface BunnyPlayer {

    var playerStateListener: PlayerStateListener?

    var currentPlayer: Player?

    var seekThumbnail: SeekThumbnail?

    var autoPaused: Boolean

    var playerSettings: PlayerSettings?

    /* Releases the resources held by the player, such as codecs. */
    fun release()

    /* Starts or resumes playback. */
    fun play()

    /* Pauses playback. */
    fun pause(autoPaused: Boolean = false)

    /* Stops playback and resets the player to its initial state. */
    fun stop()

    /* Seeks to a specified position in the video. */
    fun seekTo(positionMs: Long)

    /*  Sets the volume. Volume should be a float value between 0 (mute) and 1 (maximum volume). */
    fun setVolume(
        @FloatRange(from = 0.0, to = 1.0)
        volume: Float
    )

    /* Returns the current volume. */
    @FloatRange(from = 0.0, to = 1.0)
    fun getVolume(): Float

    fun isMuted(): Boolean

    /* Mutes the player. */
    fun mute()

    /* Unmutes the player. */
    fun unmute()

    /* Returns whether the player is currently playing. */
    fun isPlaying(): Boolean

    /* Returns the duration of the video. */
    fun getDuration(): Long

    /* Returns the current playback position. */
    fun getCurrentPosition(): Long

    fun playVideo(playerView: PlayerView, libraryId: Long, video: VideoModel, retentionData: Map<Int, Int>, playerSettings: PlayerSettings?)

    fun skipForward()

    fun replay()

    fun setSpeed(speed: Float)

    fun getSpeed(): Float

    fun getSubtitles(): Subtitles

    fun selectSubtitle(subtitleInfo: SubtitleInfo)

    fun setSubtitlesEnabled(enabled: Boolean)

    fun areSubtitlesEnabled(): Boolean

    fun getVideoQualityOptions(): VideoQualityOptions?

    fun selectQuality(quality: VideoQuality)
}
