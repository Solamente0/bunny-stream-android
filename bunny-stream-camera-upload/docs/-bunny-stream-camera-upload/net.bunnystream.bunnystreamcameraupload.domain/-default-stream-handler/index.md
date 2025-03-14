//[BunnyStreamCameraUpload](../../../index.md)/[net.bunnystream.bunnystreamcameraupload.domain](../index.md)/[DefaultStreamHandler](index.md)

# DefaultStreamHandler

[androidJvm]\
class [DefaultStreamHandler](index.md)(streamRepository: [RecordingRepository](../-recording-repository/index.md), coroutineDispatcher: CoroutineDispatcher) : StreamHandler

## Constructors

| | |
|---|---|
| [DefaultStreamHandler](-default-stream-handler.md) | [androidJvm]<br>constructor(streamRepository: [RecordingRepository](../-recording-repository/index.md), coroutineDispatcher: CoroutineDispatcher) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [recordingDurationListener](recording-duration-listener.md) | [androidJvm]<br>open override var [recordingDurationListener](recording-duration-listener.md): [RecordingDurationListener](../../net.bunnystream.bunnystreamcameraupload/-recording-duration-listener/index.md)? |
| [recordingStateListener](recording-state-listener.md) | [androidJvm]<br>open override var [recordingStateListener](recording-state-listener.md): [RecordingStateListener](../../net.bunnystream.bunnystreamcameraupload/-recording-state-listener/index.md)? |

## Functions

| Name | Summary |
|---|---|
| [initialize](initialize.md) | [androidJvm]<br>open override fun [initialize](initialize.md)(container: [ViewGroup](https://developer.android.com/reference/kotlin/android/view/ViewGroup.html), deviceCamera: [DeviceCamera](../../net.bunnystream.bunnystreamcameraupload/-device-camera/index.md)) |
| [isMuted](is-muted.md) | [androidJvm]<br>open override fun [isMuted](is-muted.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) |
| [isStreaming](is-streaming.md) | [androidJvm]<br>open override fun [isStreaming](is-streaming.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) |
| [selectCamera](select-camera.md) | [androidJvm]<br>open override fun [selectCamera](select-camera.md)(deviceCamera: [DeviceCamera](../../net.bunnystream.bunnystreamcameraupload/-device-camera/index.md)) |
| [setMuted](set-muted.md) | [androidJvm]<br>open override fun [setMuted](set-muted.md)(muted: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html)) |
| [startStreaming](start-streaming.md) | [androidJvm]<br>open override fun [startStreaming](start-streaming.md)(libraryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-long/index.html), videoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)?) |
| [stopStreaming](stop-streaming.md) | [androidJvm]<br>open override fun [stopStreaming](stop-streaming.md)() |
| [switchCamera](switch-camera.md) | [androidJvm]<br>open override fun [switchCamera](switch-camera.md)() |
