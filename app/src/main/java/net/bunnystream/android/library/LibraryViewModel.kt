package net.bunnystream.android.library

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.bunnystream.android.App
import net.bunnystream.android.library.model.Error
import net.bunnystream.android.library.model.LibraryUiState
import net.bunnystream.android.library.model.Video
import net.bunnystream.android.library.model.VideoUploadUiState
import net.bunnystream.androidsdk.upload.VideoUploadListener
import net.bunnystream.androidsdk.upload.model.UploadError
import java.util.UUID

class LibraryViewModel : ViewModel() {

    companion object {
        private const val TAG = "LibraryViewModel"
    }

    private val scope = CoroutineScope(Dispatchers.IO)

    private val prefs = App.di.localPrefs

    private val mutableUiState: MutableStateFlow<LibraryUiState> = MutableStateFlow(LibraryUiState.LibraryUiEmpty)
    val uiState = mutableUiState.asStateFlow()

    private val mutableUploadUiState: MutableStateFlow<VideoUploadUiState> = MutableStateFlow(VideoUploadUiState.NotUploading)
    val uploadUiState = mutableUploadUiState.asStateFlow()

    private val mutableErrorState: MutableSharedFlow<Error?> = MutableSharedFlow()
    val errorState = mutableErrorState.asSharedFlow()

    private var loadedVideos: List<Video> = listOf()
    private var uploadInProgressId: String? = null

    private val uploadListener = object : VideoUploadListener{
        override fun onVideoUploadError(error: UploadError) {
            Log.d(TAG, "onVideoUploadError: $error")
            mutableUploadUiState.value = VideoUploadUiState.UploadError(error.toString())
            uploadInProgressId = null
        }

        override fun onVideoUploadDone() {
            Log.d(TAG, "onVideoUploadDone")
            loadLibrary(libraryId.toString())
            mutableUploadUiState.value = VideoUploadUiState.NotUploading
            uploadInProgressId = null
        }

        override fun onVideoUploadStarted(uploadId: String) {
            Log.d(TAG, "onVideoUploadStarted: uploadId=$uploadId")
            uploadInProgressId = uploadId
        }

        override fun onUploadProgress(percentage: Int) {
            Log.d(TAG, "onUploadProgress: percentage=$percentage")
            mutableUploadUiState.value = VideoUploadUiState.Uploading(percentage)
        }
    }

    var libraryId by mutableLongStateOf(prefs.libraryId)
        private set

    init {
        Log.d(TAG, "<init> $this")
        App.di.videoUploadService.uploadListener = uploadListener
    }

    fun loadLibrary(libraryId: String) {
        mutableUiState.value = LibraryUiState.LibraryUiLoading

        this.libraryId = libraryId.toLong()
        prefs.libraryId = this.libraryId

        scope.launch {
            try {
                val response = App.di.streamSdk.videosApi.videoList(
                    libraryId = libraryId.toLong(),
                    page = null,
                    itemsPerPage = null,
                    search = null,
                    collection = null,
                    orderBy = null
                )

                loadedVideos = response.items?.map {
                    Video(
                        id = it.guid ?: UUID.randomUUID().toString(),
                        name = it.title ?: "N/A",
                        duration = it.length.toString(),
                    )
                } ?: listOf()

                mutableUiState.value = LibraryUiState.LibraryUiLoaded(loadedVideos)

            } catch (e: Exception) {
                Log.w(TAG, "Failed to fetch videos")
                e.printStackTrace()
                mutableUiState.value = LibraryUiState.LibraryUiLoaded(loadedVideos)
                mutableErrorState.emit(Error(e.message ?: e.toString()))
            }
        }
    }

    fun onErrorDismissed() = viewModelScope.launch {
        mutableErrorState.emit(null)
    }

    fun uploadVideo(videoUri: Uri) {
        mutableUploadUiState.value = VideoUploadUiState.Preparing
        App.di.videoUploadService.uploadListener = uploadListener
        App.di.videoUploadService.uploadVideo(libraryId, videoUri)
    }

    fun clearUploadError() {
        mutableUploadUiState.value = VideoUploadUiState.NotUploading
    }

    fun cancelUpload(){
        uploadInProgressId?.let {
            App.di.streamSdk.videoUploader.cancelUpload(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}