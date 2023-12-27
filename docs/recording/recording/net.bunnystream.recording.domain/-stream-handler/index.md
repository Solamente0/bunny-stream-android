//[recording](../../../index.md)/[net.bunnystream.recording.domain](../index.md)/[StreamHandler](index.md)

# StreamHandler

interface [StreamHandler](index.md)

#### Inheritors

| |
|---|
| [DefaultStreamHandler](../-default-stream-handler/index.md) |

## Properties

| Name | Summary |
|---|---|
| [recordingDurationListener](recording-duration-listener.md) | [androidJvm]<br>abstract var [recordingDurationListener](recording-duration-listener.md): [RecordingDurationListener](../../net.bunnystream.recording/-recording-duration-listener/index.md)? |
| [recordingStateListener](recording-state-listener.md) | [androidJvm]<br>abstract var [recordingStateListener](recording-state-listener.md): [RecordingStateListener](../../net.bunnystream.recording/-recording-state-listener/index.md)? |

## Functions

| Name | Summary |
|---|---|
| [initialize](initialize.md) | [androidJvm]<br>abstract fun [initialize](initialize.md)(container: [ViewGroup](https://developer.android.com/reference/kotlin/android/view/ViewGroup.html), deviceCamera: [DeviceCamera](../../net.bunnystream.recording/-device-camera/index.md)) |
| [isMuted](is-muted.md) | [androidJvm]<br>abstract fun [isMuted](is-muted.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isStreaming](is-streaming.md) | [androidJvm]<br>abstract fun [isStreaming](is-streaming.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [selectCamera](select-camera.md) | [androidJvm]<br>abstract fun [selectCamera](select-camera.md)(deviceCamera: [DeviceCamera](../../net.bunnystream.recording/-device-camera/index.md)) |
| [setMuted](set-muted.md) | [androidJvm]<br>abstract fun [setMuted](set-muted.md)(muted: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [startStreaming](start-streaming.md) | [androidJvm]<br>abstract fun [startStreaming](start-streaming.md)(libraryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), videoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) |
| [stopStreaming](stop-streaming.md) | [androidJvm]<br>abstract fun [stopStreaming](stop-streaming.md)() |
| [switchCamera](switch-camera.md) | [androidJvm]<br>abstract fun [switchCamera](switch-camera.md)() |
