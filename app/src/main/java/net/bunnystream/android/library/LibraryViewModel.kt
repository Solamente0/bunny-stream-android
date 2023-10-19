package net.bunnystream.android.library

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
import net.bunnystream.android.di.Di
import net.bunnystream.android.library.model.Error
import net.bunnystream.android.library.model.LibraryUiEmpty
import net.bunnystream.android.library.model.LibraryUiLoaded
import net.bunnystream.android.library.model.LibraryUiLoading
import net.bunnystream.android.library.model.LibraryUiState
import net.bunnystream.android.library.model.Video
import java.util.UUID

class LibraryViewModel : ViewModel() {

    companion object {
        private const val TAG = "LibraryViewModel"
    }

    private val scope = CoroutineScope(Dispatchers.IO)

    private val prefs = Di.localPrefs

    private val mutableUiState: MutableStateFlow<LibraryUiState> = MutableStateFlow(LibraryUiEmpty)
    val uiState = mutableUiState.asStateFlow()

    private val mutableErrorState: MutableSharedFlow<Error?> = MutableSharedFlow()
    val errorState = mutableErrorState.asSharedFlow()

    private var loadedVideos: List<Video> = listOf()

    var libraryId by mutableLongStateOf(prefs.libraryId)
        private set

    fun loadLibrary(libraryId: String) {

        mutableUiState.value = LibraryUiLoading

        this.libraryId = libraryId.toLong()
        prefs.libraryId = this.libraryId

        val videosApi = Di.getBunnyStreamSdk(prefs.accessKey).videosApi

        scope.launch {
            try {
                val response = videosApi.videoList(
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

                mutableUiState.value = LibraryUiLoaded(loadedVideos)

            } catch (e: Exception) {
                Log.w(TAG, "Failed to fetch videos")
                e.printStackTrace()

                mutableUiState.value = LibraryUiLoaded(loadedVideos)
                mutableErrorState.emit(Error(e.message ?: e.toString()))
            }
        }
    }

    fun onErrorDismissed() = viewModelScope.launch {
        mutableErrorState.emit(null)
    }
}