package net.bunnystream.stream

import android.view.View

interface StreamView {
    /**
     * Hides default controls, if you plan to use your own
     */
    var hideDefaultControls: Boolean

    /**
     * Click listener to receive close clicked event so you can handle it,
     * e.g. finish hosting activity or navigate to some other screen
     */
    var closeStreamClickListener: View.OnClickListener?

    /**
     * Listener to receive events about stream status
     * @see StreamStateListener
     */
    var streamStateListener: StreamStateListener?

    /**
     * Listener to receive stream duration
     * @see StreamDurationListener
     */
    var streamDurationListener: StreamDurationListener?

    /**
     * Starts camera preview
     */
    fun startPreview()

    /**
     * Initiates streaming
     * @param videoId Optional video ID to be used for recording. Should be new ID for each call
     */
    fun startStreaming(videoId: String?)

    /**
     * Stops streaming
     */
    fun stopStreaming()

    /**
     * Switches stream camera
     */
    fun switchCamera()

    /**
     * Mutes/un-mutes audio
     */
    fun setAudioMuted(muted: Boolean)

    /**
     * Check if streaming is in progress
     */
    fun isStreaming(): Boolean
}