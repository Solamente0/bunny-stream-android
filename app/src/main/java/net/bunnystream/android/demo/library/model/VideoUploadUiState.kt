package net.bunnystream.android.demo.library.model

sealed class VideoUploadUiState {

    object NotUploading : VideoUploadUiState()

    object Preparing : VideoUploadUiState()

    data class Uploading(val progress: Int) : VideoUploadUiState()

    data class UploadError(val message: String) : VideoUploadUiState()
}
