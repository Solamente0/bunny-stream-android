package net.bunnystream.recording.domain

import android.view.ViewGroup
import net.bunnystream.recording.DeviceCamera
import net.bunnystream.recording.RecordingDurationListener
import net.bunnystream.recording.RecordingStateListener

internal interface StreamHandler {
    var recordingStateListener: RecordingStateListener?

    var recordingDurationListener: RecordingDurationListener?

    fun initialize(container: ViewGroup, deviceCamera: DeviceCamera)

    fun startStreaming(libraryId: Long, videoId: String?)

    fun stopStreaming()

    fun isStreaming(): Boolean

    fun selectCamera(deviceCamera: DeviceCamera)

    fun switchCamera()

    fun isMuted(): Boolean

    fun setMuted(muted: Boolean)
}