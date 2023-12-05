//[sdk](../../../index.md)/[net.bunnystream.androidsdk.upload.model](../index.md)/[UploadError](index.md)

# UploadError

sealed class [UploadError](index.md)

Class describing video upload failures

#### Inheritors

| |
|---|
| [Unauthorized](-unauthorized/index.md) |
| [VideoNotFound](-video-not-found/index.md) |
| [ServerError](-server-error/index.md) |
| [ErrorCreating](-error-creating/index.md) |
| [ErrorReadingFile](-error-reading-file/index.md) |
| [UnknownError](-unknown-error/index.md) |

## Types

| Name | Summary |
|---|---|
| [ErrorCreating](-error-creating/index.md) | [androidJvm]<br>object [ErrorCreating](-error-creating/index.md) : [UploadError](index.md)<br>Video cannot be created for upload |
| [ErrorReadingFile](-error-reading-file/index.md) | [androidJvm]<br>object [ErrorReadingFile](-error-reading-file/index.md) : [UploadError](index.md)<br>Selected video file cannot be read (e.g. when file gets deleted or becomes inaccessible) |
| [ServerError](-server-error/index.md) | [androidJvm]<br>object [ServerError](-server-error/index.md) : [UploadError](index.md)<br>General server error |
| [Unauthorized](-unauthorized/index.md) | [androidJvm]<br>object [Unauthorized](-unauthorized/index.md) : [UploadError](index.md)<br>AccessKey is not valid |
| [UnknownError](-unknown-error/index.md) | [androidJvm]<br>data class [UnknownError](-unknown-error/index.md)(val message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [UploadError](index.md)<br>Unknown error |
| [VideoNotFound](-video-not-found/index.md) | [androidJvm]<br>object [VideoNotFound](-video-not-found/index.md) : [UploadError](index.md)<br>Video cannot be uploaded since target video ID is not found. Happens when upload is attempted without creating a video first |
