//[sdk](../../../index.md)/[net.bunnystream.androidsdk.upload.service](../index.md)/[UploadRequest](index.md)

# UploadRequest

abstract class [UploadRequest](index.md)(val libraryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val videoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

Class containing info about video that is submitted for upload

#### Parameters

androidJvm

| | |
|---|---|
| libraryId | Library ID |
| videoId | Video ID |

#### Inheritors

| |
|---|
| [BasicUploadRequest](../../net.bunnystream.androidsdk.upload.service.basic/-basic-upload-request/index.md) |
| [TusUploadRequest](../../net.bunnystream.androidsdk.upload.service.tus/-tus-upload-request/index.md) |

## Constructors

| | |
|---|---|
| [UploadRequest](-upload-request.md) | [androidJvm]<br>constructor(libraryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), videoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [libraryId](library-id.md) | [androidJvm]<br>open val [libraryId](library-id.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [videoId](video-id.md) | [androidJvm]<br>open val [videoId](video-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Functions

| Name | Summary |
|---|---|
| [cancel](cancel.md) | [androidJvm]<br>abstract suspend fun [cancel](cancel.md)()<br>Cancels upload |
