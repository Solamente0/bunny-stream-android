//[sdk](../../../index.md)/[net.bunnystream.androidsdk.upload.service](../index.md)/[UploadListener](index.md)

# UploadListener

[androidJvm]\
interface [UploadListener](index.md)

Collection of callbacks to get info about video uploads

## Functions

| Name | Summary |
|---|---|
| [onProgressUpdated](on-progress-updated.md) | [androidJvm]<br>abstract fun [onProgressUpdated](on-progress-updated.md)(percentage: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), videoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Called when upload progress changes |
| [onUploadCancelled](on-upload-cancelled.md) | [androidJvm]<br>abstract fun [onUploadCancelled](on-upload-cancelled.md)(videoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Called when upload gets cancelled |
| [onUploadDone](on-upload-done.md) | [androidJvm]<br>abstract fun [onUploadDone](on-upload-done.md)(videoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Called when video upload is finished |
| [onUploadError](on-upload-error.md) | [androidJvm]<br>abstract fun [onUploadError](on-upload-error.md)(error: [UploadError](../../net.bunnystream.androidsdk.upload.model/-upload-error/index.md), videoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)<br>Called when video uplaod fails |
| [onUploadStarted](on-upload-started.md) | [androidJvm]<br>abstract fun [onUploadStarted](on-upload-started.md)(uploadId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), videoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Called when video upload starts |
