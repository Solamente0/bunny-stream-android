//[sdk](../../../index.md)/[net.bunnystream.androidsdk.upload.service.basic](../index.md)/[BasicUploaderService](index.md)

# BasicUploaderService

[androidJvm]\
class [BasicUploaderService](index.md)(httpClient: HttpClient, coroutineDispatcher: CoroutineDispatcher) : [UploadService](../../net.bunnystream.androidsdk.upload.service/-upload-service/index.md)

## Constructors

| | |
|---|---|
| [BasicUploaderService](-basic-uploader-service.md) | [androidJvm]<br>constructor(httpClient: HttpClient, coroutineDispatcher: CoroutineDispatcher) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [upload](upload.md) | [androidJvm]<br>open suspend override fun [upload](upload.md)(libraryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), videoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), fileInfo: [FileInfo](../../net.bunnystream.androidsdk.upload.model/-file-info/index.md), listener: [UploadListener](../../net.bunnystream.androidsdk.upload.service/-upload-listener/index.md)): [UploadRequest](../../net.bunnystream.androidsdk.upload.service/-upload-request/index.md)<br>Uploads content in for of InputStream to provided url |
