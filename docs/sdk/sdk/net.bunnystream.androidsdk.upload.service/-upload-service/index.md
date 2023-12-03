//[sdk](../../../index.md)/[net.bunnystream.androidsdk.upload.service](../index.md)/[UploadService](index.md)

# UploadService

interface [UploadService](index.md)

Component that does executes upload

#### Inheritors

| |
|---|
| [BasicUploaderService](../../net.bunnystream.androidsdk.upload.service.basic/-basic-uploader-service/index.md) |
| [TusUploaderService](../../net.bunnystream.androidsdk.upload.service.tus/-tus-uploader-service/index.md) |

## Functions

| Name | Summary |
|---|---|
| [upload](upload.md) | [androidJvm]<br>abstract suspend fun [upload](upload.md)(libraryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), videoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), fileInfo: [FileInfo](../../net.bunnystream.androidsdk.upload.model/-file-info/index.md), listener: [UploadListener](../-upload-listener/index.md)): [UploadRequest](../-upload-request/index.md)<br>Uploads content in for of [InputStream](https://docs.oracle.com/javase/8/docs/api/java/io/InputStream.html) to provided url |
