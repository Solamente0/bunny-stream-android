package net.bunnystream.player.common

import androidx.annotation.FloatRange
import androidx.media3.common.Player
import net.bunnystream.player.PlayerStateListener
import org.openapitools.client.models.VideoModel

interface BunnyPlayer {

    var playerStateListener: PlayerStateListener?

    var currentPlayer: Player?

    /* Releases the resources held by the player, such as codecs. */
    fun release()

    /* Starts or resumes playback. */
    fun play()

    /* Pauses playback. */
    fun pause()

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

    fun playVideo(libraryId: Long, video: VideoModel)

    fun skipForward()

    fun replay()
}
