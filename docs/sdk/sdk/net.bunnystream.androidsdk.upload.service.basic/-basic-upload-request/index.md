//[sdk](../../../index.md)/[net.bunnystream.androidsdk.upload.service.basic](../index.md)/[BasicUploadRequest](index.md)

# BasicUploadRequest

[androidJvm]\
class [BasicUploadRequest](index.md)(val libraryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val videoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), uploadJob: Job, listener: [UploadListener](../../net.bunnystream.androidsdk.upload.service/-upload-listener/index.md)) : [UploadRequest](../../net.bunnystream.androidsdk.upload.service/-upload-request/index.md)

## Constructors

| | |
|---|---|
| [BasicUploadRequest](-basic-upload-request.md) | [androidJvm]<br>constructor(libraryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), videoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), uploadJob: Job, listener: [UploadListener](../../net.bunnystream.androidsdk.upload.service/-upload-listener/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [libraryId](library-id.md) | [androidJvm]<br>open override val [libraryId](library-id.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [videoId](video-id.md) | [androidJvm]<br>open override val [videoId](video-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Functions

| Name | Summary |
|---|---|
| [cancel](cancel.md) | [androidJvm]<br>open suspend override fun [cancel](cancel.md)()<br>Cancels upload |
