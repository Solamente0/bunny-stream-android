//[sdk](../../index.md)/[net.bunnystream.androidsdk.upload.model](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [FileInfo](-file-info/index.md) | [androidJvm]<br>data class [FileInfo](-file-info/index.md)(val fileName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val size: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val inputStream: [InputStream](https://developer.android.com/reference/kotlin/java/io/InputStream.html))<br>Information about file selected for upload |
| [HttpStatusCodes](-http-status-codes/index.md) | [androidJvm]<br>object [HttpStatusCodes](-http-status-codes/index.md) |
| [StreamContent](-stream-content/index.md) | [androidJvm]<br>class [StreamContent](-stream-content/index.md)(inputStream: [InputStream](https://developer.android.com/reference/kotlin/java/io/InputStream.html)) : OutgoingContent.ReadChannelContent |
| [UploadError](-upload-error/index.md) | [androidJvm]<br>sealed class [UploadError](-upload-error/index.md)<br>Class describing video upload failures |
