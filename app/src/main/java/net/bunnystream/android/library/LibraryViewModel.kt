package net.bunnystream.android.library

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
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
import net.bunnystream.androidsdk.upload.service.UploadListener
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

    private val uploadListener = object : UploadListener {
        override fun onUploadError(error: UploadError) {
            Log.d(TAG, "onVideoUploadError: $error")
            mutableUploadUiState.value = VideoUploadUiState.UploadError(error.toString())
            uploadInProgressId = null
        }

        override fun onUploadDone() {
            Log.d(TAG, "onVideoUploadDone")
            loadLibrary(libraryId.toString())
            mutableUploadUiState.value = VideoUploadUiState.NotUploading
            uploadInProgressId = null
        }

        override fun onUploadStarted(uploadId: String) {
            Log.d(TAG, "onVideoUploadStarted: uploadId=$uploadId")
            uploadInProgressId = uploadId
        }

        override fun onProgressUpdated(percentage: Int) {
            Log.d(TAG, "onUploadProgress: percentage=$percentage")
            mutableUploadUiState.value = VideoUploadUiState.Uploading(percentage)
        }

        override fun onUploadCancelled() {
            Log.d(TAG, "onVideoUploadCancelled")
            mutableUploadUiState.value = VideoUploadUiState.NotUploading
            uploadInProgressId = null
        }
    }

    var libraryId by mutableLongStateOf(prefs.libraryId)
        private set

    var useTusUpload by mutableStateOf(false)
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
        Log.d(TAG, "uploadVideo uri=$videoUri useTusUpload=$useTusUpload")
        mutableUploadUiState.value = VideoUploadUiState.Preparing

        if(useTusUpload) {
            App.di.tusVideoUploadService.uploadListener = uploadListener
            App.di.tusVideoUploadService.uploadVideo(libraryId, videoUri)
        } else {
            App.di.videoUploadService.uploadListener = uploadListener
            App.di.videoUploadService.uploadVideo(libraryId, videoUri)
        }
    }

    fun clearUploadError() {
        mutableUploadUiState.value = VideoUploadUiState.NotUploading
    }

    fun cancelUpload(){
        Log.d(TAG, "cancelUpload: uploadInProgressId=$uploadInProgressId")
        uploadInProgressId?.let {
            if(useTusUpload) {
                App.di.tusVideoUploadService.cancelUpload(it)
            } else {
                App.di.videoUploadService.cancelUpload(it)
            }
        }
    }

    fun onTusUploadOptionChanged(enabled: Boolean) {
        Log.d(TAG, "onTusUploadOptionChanged enabled=$enabled")
        useTusUpload = enabled
    }

    override fun onCleared() {
        super.onCleared()
    }
}