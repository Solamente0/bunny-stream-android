# ManageVideosApi

All URIs are relative to *https://video.bunnycdn.com*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**videoAddCaption**](ManageVideosApi.md#videoAddCaption) | **POST** /library/{libraryId}/videos/{videoId}/captions/{srclang} | Add Caption |
| [**videoCreateVideo**](ManageVideosApi.md#videoCreateVideo) | **POST** /library/{libraryId}/videos | Create Video |
| [**videoDeleteCaption**](ManageVideosApi.md#videoDeleteCaption) | **DELETE** /library/{libraryId}/videos/{videoId}/captions/{srclang} | Delete Caption |
| [**videoDeleteResolutions**](ManageVideosApi.md#videoDeleteResolutions) | **POST** /library/{libraryId}/videos/{videoId}/resolutions/cleanup | Cleanup unconfigured resolutions |
| [**videoDeleteVideo**](ManageVideosApi.md#videoDeleteVideo) | **DELETE** /library/{libraryId}/videos/{videoId} | Delete Video |
| [**videoFetchNewVideo**](ManageVideosApi.md#videoFetchNewVideo) | **POST** /library/{libraryId}/videos/fetch | Fetch Video |
| [**videoGetVideo**](ManageVideosApi.md#videoGetVideo) | **GET** /library/{libraryId}/videos/{videoId} | Get Video |
| [**videoGetVideoHeatmap**](ManageVideosApi.md#videoGetVideoHeatmap) | **GET** /library/{libraryId}/videos/{videoId}/heatmap | Get Video Heatmap |
| [**videoGetVideoPlayData**](ManageVideosApi.md#videoGetVideoPlayData) | **GET** /library/{libraryId}/videos/{videoId}/play | Get Video play data |
| [**videoGetVideoResolutions**](ManageVideosApi.md#videoGetVideoResolutions) | **GET** /library/{libraryId}/videos/{videoId}/resolutions | Video resolutions info |
| [**videoGetVideoStatistics**](ManageVideosApi.md#videoGetVideoStatistics) | **GET** /library/{libraryId}/statistics | Get Video Statistics |
| [**videoList**](ManageVideosApi.md#videoList) | **GET** /library/{libraryId}/videos | List Videos |
| [**videoReencodeUsingCodec**](ManageVideosApi.md#videoReencodeUsingCodec) | **PUT** /library/{libraryId}/videos/{videoId}/outputs/{outputCodecId} | Add output codec to video |
| [**videoReencodeVideo**](ManageVideosApi.md#videoReencodeVideo) | **POST** /library/{libraryId}/videos/{videoId}/reencode | Reencode Video |
| [**videoRepackage**](ManageVideosApi.md#videoRepackage) | **POST** /library/{libraryId}/videos/{videoId}/repackage | Repackage Video |
| [**videoSetThumbnail**](ManageVideosApi.md#videoSetThumbnail) | **POST** /library/{libraryId}/videos/{videoId}/thumbnail | Set Thumbnail |
| [**videoTranscribeVideo**](ManageVideosApi.md#videoTranscribeVideo) | **POST** /library/{libraryId}/videos/{videoId}/transcribe | Transcribe video |
| [**videoUpdateVideo**](ManageVideosApi.md#videoUpdateVideo) | **POST** /library/{libraryId}/videos/{videoId} | Update Video |
| [**videoUploadVideo**](ManageVideosApi.md#videoUploadVideo) | **PUT** /library/{libraryId}/videos/{videoId} | Upload Video |


<a id="videoAddCaption"></a>
# **videoAddCaption**
> StatusModel videoAddCaption(libraryId, videoId, srclang, videoAddCaptionRequest)

Add Caption

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | 
val videoId : kotlin.String = videoId_example // kotlin.String | 
val srclang : kotlin.String = srclang_example // kotlin.String | 
val videoAddCaptionRequest : VideoAddCaptionRequest =  // VideoAddCaptionRequest | 
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
| **libraryId** | **kotlin.Long**|  | |
| **videoId** | **kotlin.String**|  | |
| **srclang** | **kotlin.String**|  | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **videoAddCaptionRequest** | [**VideoAddCaptionRequest**](VideoAddCaptionRequest.md)|  | |

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

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | 
val videoCreateVideoRequest : VideoCreateVideoRequest =  // VideoCreateVideoRequest | 
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
| **libraryId** | **kotlin.Long**|  | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **videoCreateVideoRequest** | [**VideoCreateVideoRequest**](VideoCreateVideoRequest.md)|  | |

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

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | 
val videoId : kotlin.String = videoId_example // kotlin.String | 
val srclang : kotlin.String = srclang_example // kotlin.String | 
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
| **libraryId** | **kotlin.Long**|  | |
| **videoId** | **kotlin.String**|  | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **srclang** | **kotlin.String**|  | |

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

Cleanup unconfigured resolutions

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | 
val videoId : kotlin.String = videoId_example // kotlin.String | 
val resolutionsToDelete : kotlin.String = resolutionsToDelete_example // kotlin.String | 
val deleteNonConfiguredResolutions : kotlin.Boolean = true // kotlin.Boolean | 
val deleteOriginal : kotlin.Boolean = true // kotlin.Boolean | 
val deleteMp4Files : kotlin.Boolean = true // kotlin.Boolean | 
val dryRun : kotlin.Boolean = true // kotlin.Boolean | If set to true, no actual file manipulation will happen, only informational data will be returned
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
| **libraryId** | **kotlin.Long**|  | |
| **videoId** | **kotlin.String**|  | |
| **resolutionsToDelete** | **kotlin.String**|  | [optional] |
| **deleteNonConfiguredResolutions** | **kotlin.Boolean**|  | [optional] [default to false] |
| **deleteOriginal** | **kotlin.Boolean**|  | [optional] [default to false] |
| **deleteMp4Files** | **kotlin.Boolean**|  | [optional] [default to false] |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **dryRun** | **kotlin.Boolean**| If set to true, no actual file manipulation will happen, only informational data will be returned | [optional] [default to false] |

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

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | 
val videoId : kotlin.String = videoId_example // kotlin.String | 
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
| **libraryId** | **kotlin.Long**|  | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **videoId** | **kotlin.String**|  | |

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

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | 
val videoFetchNewVideoRequest : VideoFetchNewVideoRequest =  // VideoFetchNewVideoRequest | 
val collectionId : kotlin.String = collectionId_example // kotlin.String | 
val thumbnailTime : kotlin.Int = 56 // kotlin.Int | (Optional) Video time in ms to extract the main video thumbnail.
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
| **libraryId** | **kotlin.Long**|  | |
| **videoFetchNewVideoRequest** | [**VideoFetchNewVideoRequest**](VideoFetchNewVideoRequest.md)|  | |
| **collectionId** | **kotlin.String**|  | [optional] |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **thumbnailTime** | **kotlin.Int**| (Optional) Video time in ms to extract the main video thumbnail. | [optional] |

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

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | 
val videoId : kotlin.String = videoId_example // kotlin.String | 
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
| **libraryId** | **kotlin.Long**|  | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **videoId** | **kotlin.String**|  | |

### Return type

[**VideoModel**](VideoModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="videoGetVideoHeatmap"></a>
# **videoGetVideoHeatmap**
> VideoHeatmapModel videoGetVideoHeatmap(libraryId, videoId)

Get Video Heatmap

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | 
val videoId : kotlin.String = videoId_example // kotlin.String | 
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
| **libraryId** | **kotlin.Long**|  | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **videoId** | **kotlin.String**|  | |

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

Get Video play data

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | 
val videoId : kotlin.String = videoId_example // kotlin.String | 
val token : kotlin.String = token_example // kotlin.String | 
val expires : kotlin.Long = 789 // kotlin.Long | 
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
| **libraryId** | **kotlin.Long**|  | |
| **videoId** | **kotlin.String**|  | |
| **token** | **kotlin.String**|  | [optional] [default to &quot;&quot;] |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **expires** | **kotlin.Long**|  | [optional] [default to 0L] |

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

Video resolutions info

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | 
val videoId : kotlin.String = videoId_example // kotlin.String | 
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
| **libraryId** | **kotlin.Long**|  | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **videoId** | **kotlin.String**|  | |

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

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | 
val dateFrom : kotlin.String = 2013-10-20T19:20:30+01:00 // kotlin.String | (Optional) The start date of the statistics. If no value is passed, the last 30 days will be returned.
val dateTo : kotlin.String = 2013-10-20T19:20:30+01:00 // kotlin.String | (Optional) The end date of the statistics. If no value is passed, the last 30 days will be returned.
val hourly : kotlin.Boolean = true // kotlin.Boolean | (Optional) If true, the statistics data will be returned in hourly groupping.
val videoGuid : kotlin.String = videoGuid_example // kotlin.String | (Optional) The GUID of the video for which the statistics will be returned
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
| **libraryId** | **kotlin.Long**|  | |
| **dateFrom** | **kotlin.String**| (Optional) The start date of the statistics. If no value is passed, the last 30 days will be returned. | [optional] |
| **dateTo** | **kotlin.String**| (Optional) The end date of the statistics. If no value is passed, the last 30 days will be returned. | [optional] |
| **hourly** | **kotlin.Boolean**| (Optional) If true, the statistics data will be returned in hourly groupping. | [optional] [default to false] |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **videoGuid** | **kotlin.String**| (Optional) The GUID of the video for which the statistics will be returned | [optional] |

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

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | 
val page : kotlin.Int = 56 // kotlin.Int | 
val itemsPerPage : kotlin.Int = 56 // kotlin.Int | 
val search : kotlin.String = search_example // kotlin.String | 
val collection : kotlin.String = collection_example // kotlin.String | 
val orderBy : kotlin.String = orderBy_example // kotlin.String | 
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
| **libraryId** | **kotlin.Long**|  | |
| **page** | **kotlin.Int**|  | [optional] [default to 1] |
| **itemsPerPage** | **kotlin.Int**|  | [optional] [default to 100] |
| **search** | **kotlin.String**|  | [optional] [default to &quot;&quot;] |
| **collection** | **kotlin.String**|  | [optional] [default to &quot;&quot;] |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **orderBy** | **kotlin.String**|  | [optional] [default to &quot;date&quot;] |

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

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | 
val videoId : kotlin.String = videoId_example // kotlin.String | 
val outputCodecId : EncoderOutputCodec =  // EncoderOutputCodec | 
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
| **libraryId** | **kotlin.Long**|  | |
| **videoId** | **kotlin.String**|  | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **outputCodecId** | [**EncoderOutputCodec**](.md)|  | [enum: 0, 1, 2, 3] |

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

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | 
val videoId : kotlin.String = videoId_example // kotlin.String | 
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
| **libraryId** | **kotlin.Long**|  | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **videoId** | **kotlin.String**|  | |

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

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | 
val videoId : kotlin.String = videoId_example // kotlin.String | 
val keepOriginalFiles : kotlin.Boolean = true // kotlin.Boolean | Marks whether previous file versions should be kept in storage, allows for faster repackage later on. Default is true.
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
| **libraryId** | **kotlin.Long**|  | |
| **videoId** | **kotlin.String**|  | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **keepOriginalFiles** | **kotlin.Boolean**| Marks whether previous file versions should be kept in storage, allows for faster repackage later on. Default is true. | [optional] [default to true] |

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

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | 
val videoId : kotlin.String = videoId_example // kotlin.String | 
val thumbnailUrl : kotlin.String = thumbnailUrl_example // kotlin.String | 
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
| **libraryId** | **kotlin.Long**|  | |
| **videoId** | **kotlin.String**|  | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **thumbnailUrl** | **kotlin.String**|  | [optional] |

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

Transcribe video

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | 
val videoId : kotlin.String = videoId_example // kotlin.String | 
val language : kotlin.String = language_example // kotlin.String | Video source language, use ISO 639-1 language code
val force : kotlin.Boolean = true // kotlin.Boolean | 
val videoTranscribeVideoRequest : VideoTranscribeVideoRequest =  // VideoTranscribeVideoRequest | Used to override video library transcription settings, null by default
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
| **libraryId** | **kotlin.Long**|  | |
| **videoId** | **kotlin.String**|  | |
| **language** | **kotlin.String**| Video source language, use ISO 639-1 language code | [optional] |
| **force** | **kotlin.Boolean**|  | [optional] [default to false] |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **videoTranscribeVideoRequest** | [**VideoTranscribeVideoRequest**](VideoTranscribeVideoRequest.md)| Used to override video library transcription settings, null by default | [optional] |

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

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | 
val videoId : kotlin.String = videoId_example // kotlin.String | 
val videoUpdateVideoRequest : VideoUpdateVideoRequest =  // VideoUpdateVideoRequest | 
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
| **libraryId** | **kotlin.Long**|  | |
| **videoId** | **kotlin.String**|  | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **videoUpdateVideoRequest** | [**VideoUpdateVideoRequest**](VideoUpdateVideoRequest.md)|  | |

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

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageVideosApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | 
val videoId : kotlin.String = videoId_example // kotlin.String | 
val jitEnabled : kotlin.Boolean = true // kotlin.Boolean | Marks whether JIT encoding should be enabled for this video (works only when Premium Encoding is enabled), overrides library settings
val enabledResolutions : kotlin.String = enabledResolutions_example // kotlin.String | Comma separated list of resolutions enabled for encoding, available options: 240p, 360p, 480p, 720p, 1080p, 1440p, 2160p
val enabledOutputCodecs : kotlin.String = enabledOutputCodecs_example // kotlin.String | List of codecs that will be used to encode the file (overrides library settings). Available values: x264, vp9
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
| **libraryId** | **kotlin.Long**|  | |
| **videoId** | **kotlin.String**|  | |
| **jitEnabled** | **kotlin.Boolean**| Marks whether JIT encoding should be enabled for this video (works only when Premium Encoding is enabled), overrides library settings | [optional] |
| **enabledResolutions** | **kotlin.String**| Comma separated list of resolutions enabled for encoding, available options: 240p, 360p, 480p, 720p, 1080p, 1440p, 2160p | [optional] [default to &quot;&quot;] |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **enabledOutputCodecs** | **kotlin.String**| List of codecs that will be used to encode the file (overrides library settings). Available values: x264, vp9 | [optional] [default to &quot;&quot;] |

### Return type

[**StatusModel**](StatusModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

