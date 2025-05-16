# ManageVideosApi

All URIs are relative to *https://video.bunnycdn.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**videoAddCaption**](ManageVideosApi.md#videoAddCaption) | **POST** /library/{libraryId}/videos/{videoId}/captions/{srclang} | Add Caption
[**videoCreateVideo**](ManageVideosApi.md#videoCreateVideo) | **POST** /library/{libraryId}/videos | Create Video
[**videoDeleteCaption**](ManageVideosApi.md#videoDeleteCaption) | **DELETE** /library/{libraryId}/videos/{videoId}/captions/{srclang} | Delete Caption
[**videoDeleteResolutions**](ManageVideosApi.md#videoDeleteResolutions) | **POST** /library/{libraryId}/videos/{videoId}/resolutions/cleanup | Cleanup Unconfigured Resolutions
[**videoDeleteVideo**](ManageVideosApi.md#videoDeleteVideo) | **DELETE** /library/{libraryId}/videos/{videoId} | Delete Video
[**videoFetchNewVideo**](ManageVideosApi.md#videoFetchNewVideo) | **POST** /library/{libraryId}/videos/fetch | Fetch Video
[**videoGetVideo**](ManageVideosApi.md#videoGetVideo) | **GET** /library/{libraryId}/videos/{videoId} | Get Video
[**videoGetVideoHeatmap**](ManageVideosApi.md#videoGetVideoHeatmap) | **GET** /library/{libraryId}/videos/{videoId}/heatmap | Get Video Heatmap
[**videoGetVideoPlayData**](ManageVideosApi.md#videoGetVideoPlayData) | **GET** /library/{libraryId}/videos/{videoId}/play | Get Video Play Data
[**videoGetVideoResolutions**](ManageVideosApi.md#videoGetVideoResolutions) | **GET** /library/{libraryId}/videos/{videoId}/resolutions | Video Resolutions Info
[**videoGetVideoStatistics**](ManageVideosApi.md#videoGetVideoStatistics) | **GET** /library/{libraryId}/statistics | Get Video Statistics
[**videoList**](ManageVideosApi.md#videoList) | **GET** /library/{libraryId}/videos | List Videos
[**videoReencodeUsingCodec**](ManageVideosApi.md#videoReencodeUsingCodec) | **PUT** /library/{libraryId}/videos/{videoId}/outputs/{outputCodecId} | Add output codec to video
[**videoReencodeVideo**](ManageVideosApi.md#videoReencodeVideo) | **POST** /library/{libraryId}/videos/{videoId}/reencode | Reencode Video
[**videoRepackage**](ManageVideosApi.md#videoRepackage) | **POST** /library/{libraryId}/videos/{videoId}/repackage | Repackage Video
[**videoSetThumbnail**](ManageVideosApi.md#videoSetThumbnail) | **POST** /library/{libraryId}/videos/{videoId}/thumbnail | Set Thumbnail
[**videoTranscribeVideo**](ManageVideosApi.md#videoTranscribeVideo) | **POST** /library/{libraryId}/videos/{videoId}/transcribe | Transcribe Video
[**videoUpdateVideo**](ManageVideosApi.md#videoUpdateVideo) | **POST** /library/{libraryId}/videos/{videoId} | Update Video
[**videoUploadVideo**](ManageVideosApi.md#videoUploadVideo) | **PUT** /library/{libraryId}/videos/{videoId} | Upload Video


<a id="videoAddCaption"></a>
# **videoAddCaption**
> StatusModel videoAddCaption(libraryId, videoId, srclang, videoAddCaptionRequest)

Add Caption

Adds caption data to the specified video for the given language. The caption file should be provided as a base64 encoded string.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library.
val videoId : kotlin.String = videoId_example // kotlin.String | The unique identifier of the video.
val srclang : kotlin.String = srclang_example // kotlin.String | The ISO 639-1 language code for the caption (e.g., 'en', 'fr').
val videoAddCaptionRequest : VideoAddCaptionRequest =  // VideoAddCaptionRequest | Caption model including language, label, and base64 encoded captions file.
try {
    val result : StatusModel = apiInstance.videoAddCaption(libraryId, videoId, srclang, videoAddCaptionRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageVideosApi#videoAddCaption")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageVideosApi#videoAddCaption")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library. |
 **videoId** | **kotlin.String**| The unique identifier of the video. |
 **srclang** | **kotlin.String**| The ISO 639-1 language code for the caption (e.g., &#39;en&#39;, &#39;fr&#39;). |
 **videoAddCaptionRequest** | [**VideoAddCaptionRequest**](VideoAddCaptionRequest.md)| Caption model including language, label, and base64 encoded captions file. |

### Return type

[**StatusModel**](StatusModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="videoCreateVideo"></a>
# **videoCreateVideo**
> VideoModel videoCreateVideo(libraryId, videoCreateVideoRequest)

Create Video

Creates a new video entry in the specified library. Provide the video details, such as title and optional thumbnail extraction time, in the request body.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library where the video will be created.
val videoCreateVideoRequest : VideoCreateVideoRequest =  // VideoCreateVideoRequest | Video model containing details for the new video. Title is required.
try {
    val result : VideoModel = apiInstance.videoCreateVideo(libraryId, videoCreateVideoRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageVideosApi#videoCreateVideo")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageVideosApi#videoCreateVideo")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library where the video will be created. |
 **videoCreateVideoRequest** | [**VideoCreateVideoRequest**](VideoCreateVideoRequest.md)| Video model containing details for the new video. Title is required. |

### Return type

[**VideoModel**](VideoModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="videoDeleteCaption"></a>
# **videoDeleteCaption**
> StatusModel videoDeleteCaption(libraryId, videoId, srclang)

Delete Caption

Deletes the caption for the specified language from the video.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library.
val videoId : kotlin.String = videoId_example // kotlin.String | The unique identifier of the video.
val srclang : kotlin.String = srclang_example // kotlin.String | The ISO 639-1 language code of the caption to be deleted.
try {
    val result : StatusModel = apiInstance.videoDeleteCaption(libraryId, videoId, srclang)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageVideosApi#videoDeleteCaption")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageVideosApi#videoDeleteCaption")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library. |
 **videoId** | **kotlin.String**| The unique identifier of the video. |
 **srclang** | **kotlin.String**| The ISO 639-1 language code of the caption to be deleted. |

### Return type

[**StatusModel**](StatusModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="videoDeleteResolutions"></a>
# **videoDeleteResolutions**
> StatusModel videoDeleteResolutions(libraryId, videoId, resolutionsToDelete, deleteNonConfiguredResolutions, deleteOriginal, deleteMp4Files, dryRun)

Cleanup Unconfigured Resolutions

Cleans up unconfigured resolutions for the specified video. Query parameters allow you to specify which resolutions to delete and whether to perform a dry run without actual file deletion.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library.
val videoId : kotlin.String = videoId_example // kotlin.String | The unique identifier of the video.
val resolutionsToDelete : kotlin.String = resolutionsToDelete_example // kotlin.String | Comma separated list of resolutions to delete.
val deleteNonConfiguredResolutions : kotlin.Boolean = true // kotlin.Boolean | If true, deletes resolutions that are not configured.
val deleteOriginal : kotlin.Boolean = true // kotlin.Boolean | If true, deletes the original video file.
val deleteMp4Files : kotlin.Boolean = true // kotlin.Boolean | If true, deletes MP4 fallback files.
val dryRun : kotlin.Boolean = true // kotlin.Boolean | If set to true, no files will be actually deleted; only informational data is returned.
try {
    val result : StatusModel = apiInstance.videoDeleteResolutions(libraryId, videoId, resolutionsToDelete, deleteNonConfiguredResolutions, deleteOriginal, deleteMp4Files, dryRun)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageVideosApi#videoDeleteResolutions")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageVideosApi#videoDeleteResolutions")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library. |
 **videoId** | **kotlin.String**| The unique identifier of the video. |
 **resolutionsToDelete** | **kotlin.String**| Comma separated list of resolutions to delete. | [optional]
 **deleteNonConfiguredResolutions** | **kotlin.Boolean**| If true, deletes resolutions that are not configured. | [optional] [default to false]
 **deleteOriginal** | **kotlin.Boolean**| If true, deletes the original video file. | [optional] [default to false]
 **deleteMp4Files** | **kotlin.Boolean**| If true, deletes MP4 fallback files. | [optional] [default to false]
 **dryRun** | **kotlin.Boolean**| If set to true, no files will be actually deleted; only informational data is returned. | [optional] [default to false]

### Return type

[**StatusModel**](StatusModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="videoDeleteVideo"></a>
# **videoDeleteVideo**
> StatusModel videoDeleteVideo(libraryId, videoId)

Delete Video

Deletes the specified video permanently from the video library.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library.
val videoId : kotlin.String = videoId_example // kotlin.String | The unique identifier of the video to be deleted.
try {
    val result : StatusModel = apiInstance.videoDeleteVideo(libraryId, videoId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageVideosApi#videoDeleteVideo")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageVideosApi#videoDeleteVideo")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library. |
 **videoId** | **kotlin.String**| The unique identifier of the video to be deleted. |

### Return type

[**StatusModel**](StatusModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="videoFetchNewVideo"></a>
# **videoFetchNewVideo**
> StatusModel videoFetchNewVideo(libraryId, videoFetchNewVideoRequest, collectionId, thumbnailTime)

Fetch Video

Fetches a video from a remote URL and adds it to the specified library. Optional parameters allow you to specify a collection and the time (in ms) to extract a thumbnail.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library.
val videoFetchNewVideoRequest : VideoFetchNewVideoRequest =  // VideoFetchNewVideoRequest | Fetch video request model containing the URL and optional headers for the fetch operation.
val collectionId : kotlin.String = collectionId_example // kotlin.String | Optional collection ID to assign the fetched video to.
val thumbnailTime : kotlin.Int = 56 // kotlin.Int | Optional video time in milliseconds to extract the main video thumbnail.
try {
    val result : StatusModel = apiInstance.videoFetchNewVideo(libraryId, videoFetchNewVideoRequest, collectionId, thumbnailTime)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageVideosApi#videoFetchNewVideo")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageVideosApi#videoFetchNewVideo")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library. |
 **videoFetchNewVideoRequest** | [**VideoFetchNewVideoRequest**](VideoFetchNewVideoRequest.md)| Fetch video request model containing the URL and optional headers for the fetch operation. |
 **collectionId** | **kotlin.String**| Optional collection ID to assign the fetched video to. | [optional]
 **thumbnailTime** | **kotlin.Int**| Optional video time in milliseconds to extract the main video thumbnail. | [optional]

### Return type

[**StatusModel**](StatusModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="videoGetVideo"></a>
# **videoGetVideo**
> VideoModel videoGetVideo(libraryId, videoId)

Get Video

Retrieves detailed metadata for the specified video, including status, dimensions, encoding progress, and more.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library.
val videoId : kotlin.String = videoId_example // kotlin.String | The unique identifier of the video.
try {
    val result : VideoModel = apiInstance.videoGetVideo(libraryId, videoId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageVideosApi#videoGetVideo")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageVideosApi#videoGetVideo")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library. |
 **videoId** | **kotlin.String**| The unique identifier of the video. |

### Return type

[**VideoModel**](VideoModel.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="videoGetVideoHeatmap"></a>
# **videoGetVideoHeatmap**
> VideoHeatmapModel videoGetVideoHeatmap(libraryId, videoId)

Get Video Heatmap

Retrieves the heatmap data for the specified video, indicating the percentage of watch time across the video&#39;s duration.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library.
val videoId : kotlin.String = videoId_example // kotlin.String | The unique identifier of the video.
try {
    val result : VideoHeatmapModel = apiInstance.videoGetVideoHeatmap(libraryId, videoId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageVideosApi#videoGetVideoHeatmap")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageVideosApi#videoGetVideoHeatmap")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library. |
 **videoId** | **kotlin.String**| The unique identifier of the video. |

### Return type

[**VideoHeatmapModel**](VideoHeatmapModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="videoGetVideoPlayData"></a>
# **videoGetVideoPlayData**
> VideoPlayDataModel videoGetVideoPlayData(libraryId, videoId, token, expires)

Get Video Play Data

Retrieves playback data for the specified video including video URLs, captions path, authentication tokens, and player settings.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library.
val videoId : kotlin.String = videoId_example // kotlin.String | The unique identifier of the video.
val token : kotlin.String = token_example // kotlin.String | Authentication token for accessing the video playback data.
val expires : kotlin.Long = 789 // kotlin.Long | Expiration timestamp for the provided token.
try {
    val result : VideoPlayDataModel = apiInstance.videoGetVideoPlayData(libraryId, videoId, token, expires)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageVideosApi#videoGetVideoPlayData")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageVideosApi#videoGetVideoPlayData")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library. |
 **videoId** | **kotlin.String**| The unique identifier of the video. |
 **token** | **kotlin.String**| Authentication token for accessing the video playback data. | [optional] [default to &quot;&quot;]
 **expires** | **kotlin.Long**| Expiration timestamp for the provided token. | [optional] [default to 0L]

### Return type

[**VideoPlayDataModel**](VideoPlayDataModel.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="videoGetVideoResolutions"></a>
# **videoGetVideoResolutions**
> StatusModelOfVideoResolutionsInfoModel videoGetVideoResolutions(libraryId, videoId)

Video Resolutions Info

Retrieves information about the available and configured resolutions for the specified video. This includes data on storage resolutions and MP4 fallback files if available.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library.
val videoId : kotlin.String = videoId_example // kotlin.String | The unique identifier of the video.
try {
    val result : StatusModelOfVideoResolutionsInfoModel = apiInstance.videoGetVideoResolutions(libraryId, videoId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageVideosApi#videoGetVideoResolutions")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageVideosApi#videoGetVideoResolutions")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library. |
 **videoId** | **kotlin.String**| The unique identifier of the video. |

### Return type

[**StatusModelOfVideoResolutionsInfoModel**](StatusModelOfVideoResolutionsInfoModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="videoGetVideoStatistics"></a>
# **videoGetVideoStatistics**
> VideoStatisticsModel videoGetVideoStatistics(libraryId, dateFrom, dateTo, hourly, videoGuid)

Get Video Statistics

Retrieves statistical data for videos in the specified library. Supports filtering by date range, hourly grouping, and filtering by video GUID.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library.
val dateFrom : kotlin.String = 2013-10-20T19:20:30+01:00 // kotlin.String | Optional start date (ISO 8601 format) for the statistics. Defaults to the last 30 days if not provided.
val dateTo : kotlin.String = 2013-10-20T19:20:30+01:00 // kotlin.String | Optional end date (ISO 8601 format) for the statistics. Defaults to the last 30 days if not provided.
val hourly : kotlin.Boolean = true // kotlin.Boolean | If true, returns statistics data grouped by hour.
val videoGuid : kotlin.String = videoGuid_example // kotlin.String | Optional GUID of a specific video to retrieve statistics for.
try {
    val result : VideoStatisticsModel = apiInstance.videoGetVideoStatistics(libraryId, dateFrom, dateTo, hourly, videoGuid)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageVideosApi#videoGetVideoStatistics")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageVideosApi#videoGetVideoStatistics")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library. |
 **dateFrom** | **kotlin.String**| Optional start date (ISO 8601 format) for the statistics. Defaults to the last 30 days if not provided. | [optional]
 **dateTo** | **kotlin.String**| Optional end date (ISO 8601 format) for the statistics. Defaults to the last 30 days if not provided. | [optional]
 **hourly** | **kotlin.Boolean**| If true, returns statistics data grouped by hour. | [optional] [default to false]
 **videoGuid** | **kotlin.String**| Optional GUID of a specific video to retrieve statistics for. | [optional]

### Return type

[**VideoStatisticsModel**](VideoStatisticsModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="videoList"></a>
# **videoList**
> PaginationListOfVideoModel videoList(libraryId, page, itemsPerPage, search, collection, orderBy)

List Videos

Retrieves a paginated list of videos from the specified video library. Supports filtering by search term, collection, and ordering by date.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library.
val page : kotlin.Int = 56 // kotlin.Int | The page number to retrieve.
val itemsPerPage : kotlin.Int = 56 // kotlin.Int | The number of videos per page.
val search : kotlin.String = search_example // kotlin.String | A search term to filter videos by title or metadata.
val collection : kotlin.String = collection_example // kotlin.String | The ID of the collection to filter videos by.
val orderBy : kotlin.String = orderBy_example // kotlin.String | Specifies the field by which to order the video list.
try {
    val result : PaginationListOfVideoModel = apiInstance.videoList(libraryId, page, itemsPerPage, search, collection, orderBy)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageVideosApi#videoList")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageVideosApi#videoList")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library. |
 **page** | **kotlin.Int**| The page number to retrieve. | [optional] [default to 1]
 **itemsPerPage** | **kotlin.Int**| The number of videos per page. | [optional] [default to 100]
 **search** | **kotlin.String**| A search term to filter videos by title or metadata. | [optional] [default to &quot;&quot;]
 **collection** | **kotlin.String**| The ID of the collection to filter videos by. | [optional] [default to &quot;&quot;]
 **orderBy** | **kotlin.String**| Specifies the field by which to order the video list. | [optional] [default to &quot;date&quot;]

### Return type

[**PaginationListOfVideoModel**](PaginationListOfVideoModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="videoReencodeUsingCodec"></a>
# **videoReencodeUsingCodec**
> VideoModel videoReencodeUsingCodec(libraryId, videoId, outputCodecId)

Add output codec to video

Adds a specified output codec to the video. This enables additional encoding options for the video file.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library.
val videoId : kotlin.String = videoId_example // kotlin.String | The unique identifier of the video.
val outputCodecId : EncoderOutputCodec =  // EncoderOutputCodec | The output codec to add (e.g., x264, vp9, hevc, av1).
try {
    val result : VideoModel = apiInstance.videoReencodeUsingCodec(libraryId, videoId, outputCodecId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageVideosApi#videoReencodeUsingCodec")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageVideosApi#videoReencodeUsingCodec")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library. |
 **videoId** | **kotlin.String**| The unique identifier of the video. |
 **outputCodecId** | [**EncoderOutputCodec**](.md)| The output codec to add (e.g., x264, vp9, hevc, av1). | [enum: 0, 1, 2, 3]

### Return type

[**VideoModel**](VideoModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="videoReencodeVideo"></a>
# **videoReencodeVideo**
> VideoModel videoReencodeVideo(libraryId, videoId)

Reencode Video

Initiates a re-encoding process for the specified video. This operation can be used to adjust encoding settings or to fix issues with the original encoding.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library.
val videoId : kotlin.String = videoId_example // kotlin.String | The unique identifier of the video to reencode.
try {
    val result : VideoModel = apiInstance.videoReencodeVideo(libraryId, videoId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageVideosApi#videoReencodeVideo")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageVideosApi#videoReencodeVideo")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library. |
 **videoId** | **kotlin.String**| The unique identifier of the video to reencode. |

### Return type

[**VideoModel**](VideoModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="videoRepackage"></a>
# **videoRepackage**
> VideoModel videoRepackage(libraryId, videoId, keepOriginalFiles)

Repackage Video

Repackages the video, with an option to retain original files for faster future operations. This process may adjust resolution outputs and file formats.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library.
val videoId : kotlin.String = videoId_example // kotlin.String | The unique identifier of the video to repackage.
val keepOriginalFiles : kotlin.Boolean = true // kotlin.Boolean | If true, previous file versions are kept in storage, allowing for faster future repackaging. Default is true.
try {
    val result : VideoModel = apiInstance.videoRepackage(libraryId, videoId, keepOriginalFiles)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageVideosApi#videoRepackage")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageVideosApi#videoRepackage")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library. |
 **videoId** | **kotlin.String**| The unique identifier of the video to repackage. |
 **keepOriginalFiles** | **kotlin.Boolean**| If true, previous file versions are kept in storage, allowing for faster future repackaging. Default is true. | [optional] [default to true]

### Return type

[**VideoModel**](VideoModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="videoSetThumbnail"></a>
# **videoSetThumbnail**
> StatusModel videoSetThumbnail(libraryId, videoId, thumbnailUrl)

Set Thumbnail

Sets or updates the thumbnail image for the specified video using the provided thumbnail URL.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library.
val videoId : kotlin.String = videoId_example // kotlin.String | The unique identifier of the video.
val thumbnailUrl : kotlin.String = thumbnailUrl_example // kotlin.String | The URL of the thumbnail image to set for the video.
try {
    val result : StatusModel = apiInstance.videoSetThumbnail(libraryId, videoId, thumbnailUrl)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageVideosApi#videoSetThumbnail")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageVideosApi#videoSetThumbnail")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library. |
 **videoId** | **kotlin.String**| The unique identifier of the video. |
 **thumbnailUrl** | **kotlin.String**| The URL of the thumbnail image to set for the video. | [optional]

### Return type

[**StatusModel**](StatusModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="videoTranscribeVideo"></a>
# **videoTranscribeVideo**
> StatusModel videoTranscribeVideo(libraryId, videoId, language, force, videoTranscribeVideoRequest)

Transcribe Video

Initiates the transcription process for the specified video. You can optionally override the video library transcription settings using the provided request body.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library.
val videoId : kotlin.String = videoId_example // kotlin.String | The unique identifier of the video to transcribe.
val language : kotlin.String = language_example // kotlin.String | The ISO 639-1 language code of the video source. This parameter is used only if not overridden by the request body.
val force : kotlin.Boolean = true // kotlin.Boolean | If true, forces the transcription process even if the video has been transcribed before.
val videoTranscribeVideoRequest : VideoTranscribeVideoRequest =  // VideoTranscribeVideoRequest | Optional transcription settings that override the default video library settings.
try {
    val result : StatusModel = apiInstance.videoTranscribeVideo(libraryId, videoId, language, force, videoTranscribeVideoRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageVideosApi#videoTranscribeVideo")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageVideosApi#videoTranscribeVideo")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library. |
 **videoId** | **kotlin.String**| The unique identifier of the video to transcribe. |
 **language** | **kotlin.String**| The ISO 639-1 language code of the video source. This parameter is used only if not overridden by the request body. | [optional]
 **force** | **kotlin.Boolean**| If true, forces the transcription process even if the video has been transcribed before. | [optional] [default to false]
 **videoTranscribeVideoRequest** | [**VideoTranscribeVideoRequest**](VideoTranscribeVideoRequest.md)| Optional transcription settings that override the default video library settings. | [optional]

### Return type

[**StatusModel**](StatusModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="videoUpdateVideo"></a>
# **videoUpdateVideo**
> StatusModel videoUpdateVideo(libraryId, videoId, videoUpdateVideoRequest)

Update Video

Updates metadata and other details for the specified video. Provide updated fields in the request body using the UpdateVideoModel.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library.
val videoId : kotlin.String = videoId_example // kotlin.String | The unique identifier of the video to update.
val videoUpdateVideoRequest : VideoUpdateVideoRequest =  // VideoUpdateVideoRequest | Video model containing the fields to update.
try {
    val result : StatusModel = apiInstance.videoUpdateVideo(libraryId, videoId, videoUpdateVideoRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageVideosApi#videoUpdateVideo")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageVideosApi#videoUpdateVideo")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library. |
 **videoId** | **kotlin.String**| The unique identifier of the video to update. |
 **videoUpdateVideoRequest** | [**VideoUpdateVideoRequest**](VideoUpdateVideoRequest.md)| Video model containing the fields to update. |

### Return type

[**StatusModel**](StatusModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="videoUploadVideo"></a>
# **videoUploadVideo**
> StatusModel videoUploadVideo(libraryId, videoId, jitEnabled, enabledResolutions, enabledOutputCodecs)

Upload Video

Uploads a new video file to the specified video library. Additional query parameters allow customization of encoding options such as JIT encoding, enabled resolutions, and output codecs.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library.
val videoId : kotlin.String = videoId_example // kotlin.String | The unique identifier to assign to the new video.
val jitEnabled : kotlin.Boolean = true // kotlin.Boolean | Determines whether JIT encoding should be enabled for this video. Works only when Premium Encoding is enabled; overrides library settings.
val enabledResolutions : kotlin.String = enabledResolutions_example // kotlin.String | A comma-separated list of resolutions to enable for encoding. Options include: 240p, 360p, 480p, 720p, 1080p, 1440p, 2160p.
val enabledOutputCodecs : kotlin.String = enabledOutputCodecs_example // kotlin.String | Specifies the codecs that will be used to encode the video. Overrides library settings. Available values: x264, vp9.
try {
    val result : StatusModel = apiInstance.videoUploadVideo(libraryId, videoId, jitEnabled, enabledResolutions, enabledOutputCodecs)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageVideosApi#videoUploadVideo")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageVideosApi#videoUploadVideo")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library. |
 **videoId** | **kotlin.String**| The unique identifier to assign to the new video. |
 **jitEnabled** | **kotlin.Boolean**| Determines whether JIT encoding should be enabled for this video. Works only when Premium Encoding is enabled; overrides library settings. | [optional]
 **enabledResolutions** | **kotlin.String**| A comma-separated list of resolutions to enable for encoding. Options include: 240p, 360p, 480p, 720p, 1080p, 1440p, 2160p. | [optional] [default to &quot;&quot;]
 **enabledOutputCodecs** | **kotlin.String**| Specifies the codecs that will be used to encode the video. Overrides library settings. Available values: x264, vp9. | [optional] [default to &quot;&quot;]

### Return type

[**StatusModel**](StatusModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

