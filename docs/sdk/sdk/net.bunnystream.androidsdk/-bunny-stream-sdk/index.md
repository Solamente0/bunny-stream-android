//[sdk](../../../index.md)/[net.bunnystream.androidsdk](../index.md)/[BunnyStreamSdk](index.md)

# BunnyStreamSdk

[androidJvm]\
class [BunnyStreamSdk](index.md) : [StreamSdk](../-stream-sdk/index.md)

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [settingsRepository](settings-repository.md) | [androidJvm]<br>open override val [settingsRepository](settings-repository.md): [DefaultSettingsRepository](../../net.bunnystream.androidsdk.settings.data/-default-settings-repository/index.md) |
| [streamApi](stream-api.md) | [androidJvm]<br>open override val [streamApi](stream-api.md): [StreamApi](../-stream-api/index.md)<br>API endpoints for managing videos and collections |
| [tusVideoUploader](tus-video-uploader.md) | [androidJvm]<br>open override val [tusVideoUploader](tus-video-uploader.md): [DefaultVideoUploader](../../net.bunnystream.androidsdk.upload/-default-video-uploader/index.md)<br>Component for managing TUS video uploads |
| [videoUploader](video-uploader.md) | [androidJvm]<br>open override val [videoUploader](video-uploader.md): [DefaultVideoUploader](../../net.bunnystream.androidsdk.upload/-default-video-uploader/index.md)<br>Component for managing video uploads |

## Functions

| Name | Summary |
|---|---|
| [fetchPlayerSettings](fetch-player-settings.md) | [androidJvm]<br>open suspend override fun [fetchPlayerSettings](fetch-player-settings.md)(libraryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), videoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Either&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [PlayerSettings](../../net.bunnystream.androidsdk.settings.domain.model/-player-settings/index.md)&gt; |
