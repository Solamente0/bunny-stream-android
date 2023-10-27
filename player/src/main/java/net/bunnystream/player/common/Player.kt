package net.bunnystream.player.common

import androidx.annotation.FloatRange

interface Player {

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
    fun setVolume(@FloatRange(from = 0.0, to = 1.0) volume: Float)

    /* Returns the current volume. */
    @FloatRange(from = 0.0, to = 1.0)
    fun getVolume(): Float

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

    /* Returns the percentage of the video that has been buffered. */
    fun getBufferedPercentage(): Int

    /* ets a listener to be notified of playback state changes and errors. */
    fun setPlaybackStateListener(listener: PlaybackState)

    /* Returns the current playback state. */
    fun getPlaybackState()

    /* Sets the desired quality/resolution. */
    fun setVideoQuality(videoQuality: VideoQuality)

    /* Returns a list of available qualities/resolutions. */
    fun getAvailableVideoQualities(): VideoQuality

    /* Sets whether the player should be in fullscreen mode. */
    fun setFullscreen(fullscreen: FullscreenMode)

    /* Sets the aspect ratio of the video. */
    fun setAspectRatio(aspectRatio: Float)

    /* Listener that state of player's fullscreen mode. */
    fun setFullScreenListener(listener: (FullscreenMode) -> Unit)

}
