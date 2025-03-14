
# VideoResolutionsInfoModel

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**videoId** | **kotlin.String** | The unique identifier of the video. |  [optional]
**videoLibraryId** | **kotlin.Long** | The ID of the video library. |  [optional]
**availableResolutions** | **kotlin.collections.List&lt;kotlin.String&gt;** | A list of available resolutions for the video. |  [optional]
**configuredResolutions** | **kotlin.collections.List&lt;kotlin.String&gt;** | A list of resolutions configured for encoding. |  [optional]
**playlistResolutions** | [**kotlin.collections.List&lt;ResolutionReference&gt;**](ResolutionReference.md) | A list of resolutions used in playlists. |  [optional]
**storageResolutions** | [**kotlin.collections.List&lt;ResolutionReference&gt;**](ResolutionReference.md) | A list of resolutions stored on the server. |  [optional]
**mp4Resolutions** | [**kotlin.collections.List&lt;ResolutionReference&gt;**](ResolutionReference.md) | A list of MP4 fallback resolutions. |  [optional]
**storageObjects** | [**kotlin.collections.List&lt;StorageObjectModel&gt;**](StorageObjectModel.md) | A list of storage objects related to the video resolutions. |  [optional]
**oldResolutions** | [**kotlin.collections.List&lt;StorageObjectModel&gt;**](StorageObjectModel.md) | A list of previous resolution objects that may be removed. |  [optional]
**hasBothOldAndNewResolutionFormat** | **kotlin.Boolean** | Indicates if both old and new resolution formats are present. |  [optional]
**hasOriginal** | **kotlin.Boolean** | Indicates if the original video file is available. |  [optional]



