//[sdk](../../../index.md)/[net.bunnystream.androidsdk.upload](../index.md)/[DefaultVideoUploader](index.md)

# DefaultVideoUploader

[androidJvm]\
class [DefaultVideoUploader](index.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), videoUploadService: [UploadService](../../net.bunnystream.androidsdk.upload.service/-upload-service/index.md), ioDispatcher: CoroutineDispatcher, videosApi: ManageVideosApi) : [VideoUploader](../-video-uploader/index.md)

## Constructors

| | |
|---|---|
| [DefaultVideoUploader](-default-video-uploader.md) | [androidJvm]<br>constructor(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), videoUploadService: [UploadService](../../net.bunnystream.androidsdk.upload.service/-upload-service/index.md), ioDispatcher: CoroutineDispatcher, videosApi: ManageVideosApi) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [cancelUpload](cancel-upload.md) | [androidJvm]<br>open override fun [cancelUpload](cancel-upload.md)(uploadId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Cancels video upload |
| [uploadVideo](upload-video.md) | [androidJvm]<br>open override fun [uploadVideo](upload-video.md)(libraryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), videoUri: [Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html), listener: [UploadListener](../../net.bunnystream.androidsdk.upload.service/-upload-listener/index.md))<br>Uploads video represented by Uri |
