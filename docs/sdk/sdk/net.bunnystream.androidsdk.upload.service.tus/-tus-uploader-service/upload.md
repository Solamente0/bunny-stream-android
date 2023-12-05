//[sdk](../../../index.md)/[net.bunnystream.androidsdk.upload.service.tus](../index.md)/[TusUploaderService](index.md)/[upload](upload.md)

# upload

[androidJvm]\
open suspend override fun [upload](upload.md)(libraryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), videoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), fileInfo: [FileInfo](../../net.bunnystream.androidsdk.upload.model/-file-info/index.md), listener: [UploadListener](../../net.bunnystream.androidsdk.upload.service/-upload-listener/index.md)): [UploadRequest](../../net.bunnystream.androidsdk.upload.service/-upload-request/index.md)

Uploads content in for of InputStream to provided url

#### Return

[UploadRequest](../../net.bunnystream.androidsdk.upload.service/-upload-request/index.md) containing info about submitted upload

#### Parameters

androidJvm

| | |
|---|---|
| libraryId | target library ID |
| videoId | target video ID |
| fileInfo | details about the file to be uploaded, see [FileInfo](../../net.bunnystream.androidsdk.upload.model/-file-info/index.md) |
| listener | interface to get notified about upload status changes, see [UploadListener](../../net.bunnystream.androidsdk.upload.service/-upload-listener/index.md) |
