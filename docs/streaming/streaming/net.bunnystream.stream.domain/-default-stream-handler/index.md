//[streaming](../../../index.md)/[net.bunnystream.stream.domain](../index.md)/[DefaultStreamHandler](index.md)

# DefaultStreamHandler

[androidJvm]\
class [DefaultStreamHandler](index.md)(streamRepository: [StreamRepository](../-stream-repository/index.md), coroutineDispatcher: CoroutineDispatcher) : [StreamHandler](../-stream-handler/index.md)

## Constructors

| | |
|---|---|
| [DefaultStreamHandler](-default-stream-handler.md) | [androidJvm]<br>constructor(streamRepository: [StreamRepository](../-stream-repository/index.md), coroutineDispatcher: CoroutineDispatcher) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [streamDurationListener](stream-duration-listener.md) | [androidJvm]<br>open override var [streamDurationListener](stream-duration-listener.md): [StreamDurationListener](../../net.bunnystream.stream/-stream-duration-listener/index.md)? |
| [streamStateListener](stream-state-listener.md) | [androidJvm]<br>open override var [streamStateListener](stream-state-listener.md): [StreamStateListener](../../net.bunnystream.stream/-stream-state-listener/index.md)? |

## Functions

| Name | Summary |
|---|---|
| [initialize](initialize.md) | [androidJvm]<br>open override fun [initialize](initialize.md)(container: [ViewGroup](https://developer.android.com/reference/kotlin/android/view/ViewGroup.html), deviceCamera: [DeviceCamera](../../net.bunnystream.stream/-device-camera/index.md)) |
| [isMuted](is-muted.md) | [androidJvm]<br>open override fun [isMuted](is-muted.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isStreaming](is-streaming.md) | [androidJvm]<br>open override fun [isStreaming](is-streaming.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [selectCamera](select-camera.md) | [androidJvm]<br>open override fun [selectCamera](select-camera.md)(deviceCamera: [DeviceCamera](../../net.bunnystream.stream/-device-camera/index.md)) |
| [setMuted](set-muted.md) | [androidJvm]<br>open override fun [setMuted](set-muted.md)(muted: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [startStreaming](start-streaming.md) | [androidJvm]<br>open override fun [startStreaming](start-streaming.md)(libraryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)) |
| [stopStreaming](stop-streaming.md) | [androidJvm]<br>open override fun [stopStreaming](stop-streaming.md)() |
| [switchCamera](switch-camera.md) | [androidJvm]<br>open override fun [switchCamera](switch-camera.md)() |
