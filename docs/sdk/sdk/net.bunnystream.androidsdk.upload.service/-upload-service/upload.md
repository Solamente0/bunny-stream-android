//[sdk](../../../index.md)/[net.bunnystream.androidsdk.upload.service](../index.md)/[UploadService](index.md)/[upload](upload.md)

# upload

[androidJvm]\
abstract suspend fun [upload](upload.md)(libraryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), videoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), fileInfo: [FileInfo](../../net.bunnystream.androidsdk.upload.model/-file-info/index.md), listener: [UploadListener](../-upload-listener/index.md)): [UploadRequest](../-upload-request/index.md)

Uploads content in for of [InputStream](https://docs.oracle.com/javase/8/docs/api/java/io/InputStream.html) to provided url

#### Return

[UploadRequest](../-upload-request/index.md) containing info about submitted upload

#### Parameters

androidJvm

| | |
|---|---|
| libraryId | target library ID |
| videoId | target video ID |
| fileInfo | details about the file to be uploaded, see [FileInfo](../../net.bunnystream.androidsdk.upload.model/-file-info/index.md) |
| listener | interface to get notified about upload status changes, see [UploadListener](../-upload-listener/index.md) |
