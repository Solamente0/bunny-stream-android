package net.bunnystream.stream.domain

import android.view.ViewGroup
import net.bunnystream.stream.DeviceCamera
import net.bunnystream.stream.StreamDurationListener
import net.bunnystream.stream.StreamStateListener

interface StreamHandler {
    var streamStateListener: StreamStateListener?

    var streamDurationListener: StreamDurationListener?

    fun initialize(container: ViewGroup, deviceCamera: DeviceCamera)

    fun startStreaming(libraryId: Long, videoId: String?)

    fun stopStreaming()

    fun isStreaming(): Boolean

    fun selectCamera(deviceCamera: DeviceCamera)

    fun switchCamera()

    fun isMuted(): Boolean

    fun setMuted(muted: Boolean)
}