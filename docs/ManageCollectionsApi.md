# ManageCollectionsApi

All URIs are relative to *https://video.bunnycdn.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**collectionCreateCollection**](ManageCollectionsApi.md#collectionCreateCollection) | **POST** /library/{libraryId}/collections | Create Collection
[**collectionDeleteCollection**](ManageCollectionsApi.md#collectionDeleteCollection) | **DELETE** /library/{libraryId}/collections/{collectionId} | Delete Collection
[**collectionGetCollection**](ManageCollectionsApi.md#collectionGetCollection) | **GET** /library/{libraryId}/collections/{collectionId} | Get Collection
[**collectionList**](ManageCollectionsApi.md#collectionList) | **GET** /library/{libraryId}/collections | Get Collection List
[**collectionUpdateCollection**](ManageCollectionsApi.md#collectionUpdateCollection) | **POST** /library/{libraryId}/collections/{collectionId} | Update Collection


<a id="collectionCreateCollection"></a>
# **collectionCreateCollection**
> CollectionModel collectionCreateCollection(libraryId, collectionUpdateCollectionRequest)

Create Collection

Creates a new collection in the specified video library using the provided collection details.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageCollectionsApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library where the new collection will be created.
val collectionUpdateCollectionRequest : CollectionUpdateCollectionRequest =  // CollectionUpdateCollectionRequest | Collection model containing the details for the new collection.
try {
    val result : CollectionModel = apiInstance.collectionCreateCollection(libraryId, collectionUpdateCollectionRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageCollectionsApi#collectionCreateCollection")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageCollectionsApi#collectionCreateCollection")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library where the new collection will be created. |
 **collectionUpdateCollectionRequest** | [**CollectionUpdateCollectionRequest**](CollectionUpdateCollectionRequest.md)| Collection model containing the details for the new collection. |

### Return type

[**CollectionModel**](CollectionModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="collectionDeleteCollection"></a>
# **collectionDeleteCollection**
> StatusModel collectionDeleteCollection(libraryId, collectionId)

Delete Collection

Deletes the specified collection permanently from the video library.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageCollectionsApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library containing the collection.
val collectionId : kotlin.String = collectionId_example // kotlin.String | The unique identifier of the collection to be deleted.
try {
    val result : StatusModel = apiInstance.collectionDeleteCollection(libraryId, collectionId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageCollectionsApi#collectionDeleteCollection")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageCollectionsApi#collectionDeleteCollection")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library containing the collection. |
 **collectionId** | **kotlin.String**| The unique identifier of the collection to be deleted. |

### Return type

[**StatusModel**](StatusModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="collectionGetCollection"></a>
# **collectionGetCollection**
> CollectionModel collectionGetCollection(libraryId, collectionId, includeThumbnails)

Get Collection

Retrieves details of a specific collection. Use the query parameter &#39;includeThumbnails&#39; to include preview images if available.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageCollectionsApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library containing the collection.
val collectionId : kotlin.String = collectionId_example // kotlin.String | The unique identifier of the collection.
val includeThumbnails : kotlin.Boolean = true // kotlin.Boolean | If true, the response will include preview image URLs for the collection.
try {
    val result : CollectionModel = apiInstance.collectionGetCollection(libraryId, collectionId, includeThumbnails)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageCollectionsApi#collectionGetCollection")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageCollectionsApi#collectionGetCollection")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library containing the collection. |
 **collectionId** | **kotlin.String**| The unique identifier of the collection. |
 **includeThumbnails** | **kotlin.Boolean**| If true, the response will include preview image URLs for the collection. | [optional] [default to false]

### Return type

[**CollectionModel**](CollectionModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="collectionList"></a>
# **collectionList**
> PaginationListOfCollectionModel collectionList(libraryId, page, itemsPerPage, search, orderBy, includeThumbnails)

Get Collection List

Retrieves a paginated list of collections for the specified video library. Optional query parameters allow for filtering, pagination, and ordering the results.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageCollectionsApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library.
val page : kotlin.Int = 56 // kotlin.Int | The page number to retrieve.
val itemsPerPage : kotlin.Int = 56 // kotlin.Int | The number of items per page.
val search : kotlin.String = search_example // kotlin.String | A search term to filter collections by name or other metadata.
val orderBy : kotlin.String = orderBy_example // kotlin.String | Specifies the field by which to order the results.
val includeThumbnails : kotlin.Boolean = true // kotlin.Boolean | If true, includes thumbnail image URLs in the response.
try {
    val result : PaginationListOfCollectionModel = apiInstance.collectionList(libraryId, page, itemsPerPage, search, orderBy, includeThumbnails)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageCollectionsApi#collectionList")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageCollectionsApi#collectionList")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library. |
 **page** | **kotlin.Int**| The page number to retrieve. | [optional] [default to 1]
 **itemsPerPage** | **kotlin.Int**| The number of items per page. | [optional] [default to 100]
 **search** | **kotlin.String**| A search term to filter collections by name or other metadata. | [optional] [default to &quot;&quot;]
 **orderBy** | **kotlin.String**| Specifies the field by which to order the results. | [optional] [default to &quot;date&quot;]
 **includeThumbnails** | **kotlin.Boolean**| If true, includes thumbnail image URLs in the response. | [optional] [default to false]

### Return type

[**PaginationListOfCollectionModel**](PaginationListOfCollectionModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="collectionUpdateCollection"></a>
# **collectionUpdateCollection**
> StatusModel collectionUpdateCollection(libraryId, collectionId, collectionUpdateCollectionRequest)

Update Collection

Updates an existing collection. Provide the new collection details in the request body using the UpdateCollectionModel.

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = ManageCollectionsApi()
val libraryId : kotlin.Long = 789 // kotlin.Long | The ID of the video library containing the collection.
val collectionId : kotlin.String = collectionId_example // kotlin.String | The unique identifier of the collection to be updated.
val collectionUpdateCollectionRequest : CollectionUpdateCollectionRequest =  // CollectionUpdateCollectionRequest | Collection model containing the fields to be updated.
try {
    val result : StatusModel = apiInstance.collectionUpdateCollection(libraryId, collectionId, collectionUpdateCollectionRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ManageCollectionsApi#collectionUpdateCollection")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ManageCollectionsApi#collectionUpdateCollection")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **libraryId** | **kotlin.Long**| The ID of the video library containing the collection. |
 **collectionId** | **kotlin.String**| The unique identifier of the collection to be updated. |
 **collectionUpdateCollectionRequest** | [**CollectionUpdateCollectionRequest**](CollectionUpdateCollectionRequest.md)| Collection model containing the fields to be updated. |

### Return type

[**StatusModel**](StatusModel.md)

### Authorization


Configure AccessKey:
    ApiClient.apiKey["AccessKey"] = ""
    ApiClient.apiKeyPrefix["AccessKey"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

