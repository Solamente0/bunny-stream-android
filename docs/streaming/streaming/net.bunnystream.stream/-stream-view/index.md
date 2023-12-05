//[streaming](../../../index.md)/[net.bunnystream.stream](../index.md)/[StreamView](index.md)

# StreamView

interface [StreamView](index.md)

#### Inheritors

| |
|---|
| [BunnyStreamView](../-bunny-stream-view/index.md) |

## Properties

| Name | Summary |
|---|---|
| [closeStreamClickListener](close-stream-click-listener.md) | [androidJvm]<br>abstract var [closeStreamClickListener](close-stream-click-listener.md): [View.OnClickListener](https://developer.android.com/reference/kotlin/android/view/View.OnClickListener.html)?<br>Click listener to receive close clicked event so you can handle it, e.g. finish hosting activity or navigate to some other screen |
| [hideDefaultControls](hide-default-controls.md) | [androidJvm]<br>abstract var [hideDefaultControls](hide-default-controls.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Hides default controls, if you plan to use your own |
| [streamDurationListener](stream-duration-listener.md) | [androidJvm]<br>abstract var [streamDurationListener](stream-duration-listener.md): [StreamDurationListener](../-stream-duration-listener/index.md)?<br>Listener to receive stream duration |
| [streamStateListener](stream-state-listener.md) | [androidJvm]<br>abstract var [streamStateListener](stream-state-listener.md): [StreamStateListener](../-stream-state-listener/index.md)?<br>Listener to receive events about stream status |

## Functions

| Name | Summary |
|---|---|
| [isStreaming](is-streaming.md) | [androidJvm]<br>abstract fun [isStreaming](is-streaming.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Check if streaming is in progress |
| [setAudioMuted](set-audio-muted.md) | [androidJvm]<br>abstract fun [setAudioMuted](set-audio-muted.md)(muted: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))<br>Mutes/un-mutes audio |
| [startPreview](start-preview.md) | [androidJvm]<br>abstract fun [startPreview](start-preview.md)()<br>Starts camera preview |
| [startStreaming](start-streaming.md) | [androidJvm]<br>abstract fun [startStreaming](start-streaming.md)()<br>Initiates streaming |
| [stopStreaming](stop-streaming.md) | [androidJvm]<br>abstract fun [stopStreaming](stop-streaming.md)()<br>Stops streaming |
| [switchCamera](switch-camera.md) | [androidJvm]<br>abstract fun [switchCamera](switch-camera.md)()<br>Switches stream camera |
