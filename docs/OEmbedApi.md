# OEmbedApi

All URIs are relative to *https://video.bunnycdn.com*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**oEmbedGetOEmbed**](OEmbedApi.md#oEmbedGetOEmbed) | **GET** /OEmbed | Get OEmbed Data |


<a id="oEmbedGetOEmbed"></a>
# **oEmbedGetOEmbed**
> VideoOEmbedModel oEmbedGetOEmbed(url, maxWidth, maxHeight, token, expires)

Get OEmbed Data

Retrieves OEmbed information for a given video URL. This includes embed HTML, thumbnail URL, and metadata such as title and provider details.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = OEmbedApi()
val url : kotlin.String = url_example // kotlin.String | The URL for which to retrieve OEmbed information.
val maxWidth : kotlin.Int = 56 // kotlin.Int | Optional maximum width of the embed.
val maxHeight : kotlin.Int = 56 // kotlin.Int | Optional maximum height of the embed.
val token : kotlin.String = token_example // kotlin.String | Authentication token if required.
val expires : kotlin.Long = 789 // kotlin.Long | Expiration timestamp for the provided token.
try {
    val result : VideoOEmbedModel = apiInstance.oEmbedGetOEmbed(url, maxWidth, maxHeight, token, expires)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OEmbedApi#oEmbedGetOEmbed")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OEmbedApi#oEmbedGetOEmbed")
    e.printStackTrace()
}
```

### Parameters
| **url** | **kotlin.String**| The URL for which to retrieve OEmbed information. | [optional] |
| **maxWidth** | **kotlin.Int**| Optional maximum width of the embed. | [optional] |
| **maxHeight** | **kotlin.Int**| Optional maximum height of the embed. | [optional] |
| **token** | **kotlin.String**| Authentication token if required. | [optional] [default to &quot;&quot;] |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **expires** | **kotlin.Long**| Expiration timestamp for the provided token. | [optional] [default to 0L] |

### Return type

[**VideoOEmbedModel**](VideoOEmbedModel.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

