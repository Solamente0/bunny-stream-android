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
    fun setFullscreenListener(listener: (FullscreenMode) -> Unit)

    /* Toggle the visibility of rewind button. */
    fun setRewindButtonVisibility(isVisible: Boolean)

    /* Toggle the visibility of fast forward button. */
    fun setForwardButtonVisibility(isVisible: Boolean)

    /* Toggle the visibility of play/pause button. */
    fun setPlayButtonVisibility(isVisible: Boolean)

    /* Toggle the visibility of captions button. */
    fun setCaptionsVisibility(isVisible: Boolean)

    /* Toggle the visibility of current time progress. */
    fun setCurrentTimeVisibility(isVisible: Boolean)

    /* Toggle the visibility of total video duration. */
    fun setDurationVisibility(isVisible: Boolean)

    /* Toggle the visibility of fullscreen button. */
    fun setFullscreenButtonVisibility(isVisible: Boolean)

    /* Toggle the visibility of mute/un-mute button. */
    fun setMuteButtonVisibility(isVisible: Boolean)

    /* Toggle the visibility of casting button. */
    fun setCastButtonVisibility(isVisible: Boolean)

    /* Toggle the visibility of progress. */
    fun setProgressVisibility(isVisible: Boolean)

    /* Toggle the visibility of settings button. */
    fun setSettingsVisibility(isVisible: Boolean)

}
