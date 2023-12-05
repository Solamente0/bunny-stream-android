//[sdk](../../../index.md)/[net.bunnystream.androidsdk](../index.md)/[StreamSdk](index.md)

# StreamSdk

interface [StreamSdk](index.md)

#### Inheritors

| |
|---|
| [BunnyStreamSdk](../-bunny-stream-sdk/index.md) |

## Properties

| Name | Summary |
|---|---|
| [settingsRepository](settings-repository.md) | [androidJvm]<br>abstract val [settingsRepository](settings-repository.md): [SettingsRepository](../../net.bunnystream.androidsdk.settings.domain/-settings-repository/index.md) |
| [streamApi](stream-api.md) | [androidJvm]<br>abstract val [streamApi](stream-api.md): [StreamApi](../-stream-api/index.md)<br>API endpoints for managing videos and collections |
| [tusVideoUploader](tus-video-uploader.md) | [androidJvm]<br>abstract val [tusVideoUploader](tus-video-uploader.md): [VideoUploader](../../net.bunnystream.androidsdk.upload/-video-uploader/index.md)<br>Component for managing TUS video uploads |
| [videoUploader](video-uploader.md) | [androidJvm]<br>abstract val [videoUploader](video-uploader.md): [VideoUploader](../../net.bunnystream.androidsdk.upload/-video-uploader/index.md)<br>Component for managing video uploads |

## Functions

| Name | Summary |
|---|---|
| [fetchPlayerSettings](fetch-player-settings.md) | [androidJvm]<br>abstract suspend fun [fetchPlayerSettings](fetch-player-settings.md)(libraryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), videoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Either&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [PlayerSettings](../../net.bunnystream.androidsdk.settings.domain.model/-player-settings/index.md)&gt; |
