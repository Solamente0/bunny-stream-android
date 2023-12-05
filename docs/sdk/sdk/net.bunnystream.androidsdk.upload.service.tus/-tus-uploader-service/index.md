//[sdk](../../../index.md)/[net.bunnystream.androidsdk.upload.service.tus](../index.md)/[TusUploaderService](index.md)

# TusUploaderService

[androidJvm]\
class [TusUploaderService](index.md)(preferences: [SharedPreferences](https://developer.android.com/reference/kotlin/android/content/SharedPreferences.html), chunkSize: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), accessKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), dispatcher: CoroutineDispatcher) : [UploadService](../../net.bunnystream.androidsdk.upload.service/-upload-service/index.md)

## Constructors

| | |
|---|---|
| [TusUploaderService](-tus-uploader-service.md) | [androidJvm]<br>constructor(preferences: [SharedPreferences](https://developer.android.com/reference/kotlin/android/content/SharedPreferences.html), chunkSize: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), accessKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), dispatcher: CoroutineDispatcher) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [upload](upload.md) | [androidJvm]<br>open suspend override fun [upload](upload.md)(libraryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), videoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), fileInfo: [FileInfo](../../net.bunnystream.androidsdk.upload.model/-file-info/index.md), listener: [UploadListener](../../net.bunnystream.androidsdk.upload.service/-upload-listener/index.md)): [UploadRequest](../../net.bunnystream.androidsdk.upload.service/-upload-request/index.md)<br>Uploads content in for of InputStream to provided url |
