//[streaming](../../../index.md)/[net.bunnystream.stream](../index.md)/[StreamStateListener](index.md)

# StreamStateListener

[androidJvm]\
interface [StreamStateListener](index.md)

## Functions

| Name | Summary |
|---|---|
| [onAudioMuted](on-audio-muted.md) | [androidJvm]<br>abstract fun [onAudioMuted](on-audio-muted.md)(muted: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))<br>Called when audio mute status changes |
| [onCameraChanged](on-camera-changed.md) | [androidJvm]<br>abstract fun [onCameraChanged](on-camera-changed.md)(deviceCamera: [DeviceCamera](../-device-camera/index.md))<br>Called when camera changes |
| [onStreamAuthError](on-stream-auth-error.md) | [androidJvm]<br>abstract fun [onStreamAuthError](on-stream-auth-error.md)()<br>Called when stream authentication fails |
| [onStreamConnected](on-stream-connected.md) | [androidJvm]<br>abstract fun [onStreamConnected](on-stream-connected.md)()<br>Called when stream is connected to server, effectively making the stream live |
| [onStreamConnectionFailed](on-stream-connection-failed.md) | [androidJvm]<br>abstract fun [onStreamConnectionFailed](on-stream-connection-failed.md)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Called when stream connection fails |
| [onStreamDisconnected](on-stream-disconnected.md) | [androidJvm]<br>abstract fun [onStreamDisconnected](on-stream-disconnected.md)()<br>Called when stream is disconnected |
| [onStreamInitializing](on-stream-initializing.md) | [androidJvm]<br>abstract fun [onStreamInitializing](on-stream-initializing.md)()<br>Called stream is being initialized |
| [onStreamStopped](on-stream-stopped.md) | [androidJvm]<br>abstract fun [onStreamStopped](on-stream-stopped.md)()<br>Called when stream is stopped |
