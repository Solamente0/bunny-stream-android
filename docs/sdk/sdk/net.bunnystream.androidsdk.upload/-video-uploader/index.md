//[sdk](../../../index.md)/[net.bunnystream.androidsdk.upload](../index.md)/[VideoUploader](index.md)

# VideoUploader

interface [VideoUploader](index.md)

Component that manages video uploads

#### Inheritors

| |
|---|
| [DefaultVideoUploader](../-default-video-uploader/index.md) |

## Functions

| Name | Summary |
|---|---|
| [cancelUpload](cancel-upload.md) | [androidJvm]<br>abstract fun [cancelUpload](cancel-upload.md)(uploadId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Cancels video upload |
| [uploadVideo](upload-video.md) | [androidJvm]<br>abstract fun [uploadVideo](upload-video.md)(libraryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), videoUri: [Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html), listener: [UploadListener](../../net.bunnystream.androidsdk.upload.service/-upload-listener/index.md))<br>Uploads video represented by Uri |
