//[sdk](../../index.md)/[net.bunnystream.androidsdk.upload.service.tus](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [TusUploaderService](-tus-uploader-service/index.md) | [androidJvm]<br>class [TusUploaderService](-tus-uploader-service/index.md)(preferences: [SharedPreferences](https://developer.android.com/reference/kotlin/android/content/SharedPreferences.html), chunkSize: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), accessKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), dispatcher: CoroutineDispatcher) : [UploadService](../net.bunnystream.androidsdk.upload.service/-upload-service/index.md) |
| [TusUploadRequest](-tus-upload-request/index.md) | [androidJvm]<br>class [TusUploadRequest](-tus-upload-request/index.md)(val libraryId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val videoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), uploader: TusUploader, listener: [UploadListener](../net.bunnystream.androidsdk.upload.service/-upload-listener/index.md)) : [UploadRequest](../net.bunnystream.androidsdk.upload.service/-upload-request/index.md) |
