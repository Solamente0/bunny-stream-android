package net.bunnystream.android.demo.player

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.bunnystream.android.demo.library.model.Error
import net.bunnystream.android.demo.library.model.Video
import net.bunnystream.android.demo.library.model.VideoStatus
import net.bunnystream.api.BunnyStreamApi
import org.openapitools.client.models.VideoModel
import java.util.UUID
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class PlayerViewModel : ViewModel() {

    companion object {
        private const val TAG = "v"
    }

    private val scope = CoroutineScope(Dispatchers.IO)

    private val mutableUiState: MutableStateFlow<VideoUiState> =
        MutableStateFlow(VideoUiState.VideoUiEmpty)
    val uiState = mutableUiState.asStateFlow()

    private val mutableErrorState: MutableSharedFlow<Error?> = MutableSharedFlow()
    val errorState = mutableErrorState.asSharedFlow()

    private val libraryId: Long
        get() = BunnyStreamApi.libraryId

    init {
        Log.d(TAG, "<init> $this")
    }

    fun loadVideo(videoId: String) {
        Log.d(TAG, "loadVideo videoId=$videoId")

        if (libraryId == -1L || !BunnyStreamApi.isInitialized()) {
            return
        }

        mutableUiState.value = VideoUiState.VideoUiLoading

        scope.launch {
            try {
                val response =
                    BunnyStreamApi.getInstance().videosApi.videoGetVideo(libraryId, videoId)
                val video = response.toVideo()
                mutableUiState.value = VideoUiState.VideoUiLoaded(video)
            } catch (e: Exception) {
                Log.e(TAG, "Error loading video: ${e.message}")
                e.printStackTrace()
                mutableErrorState.emit(Error("Error loading video: ${e.message}"))
                mutableUiState.value = VideoUiState.VideoUiEmpty
            }
        }

    }

    fun onErrorDismissed() = viewModelScope.launch {
        mutableErrorState.emit(null)
    }

    private fun VideoModel.toVideo(): Video {
        return Video(
            id = guid ?: UUID.randomUUID().toString(),
            name = title ?: "N/A",
            duration = length?.toDuration(DurationUnit.SECONDS).toString(),
            status = when (status?.value) {
                null -> VideoStatus.ERROR
                0 -> VideoStatus.CREATED
                1 -> VideoStatus.UPLOADED
                2 -> VideoStatus.PROCESSING
                3 -> VideoStatus.TRANSCODING
                4 -> VideoStatus.FINISHED
                5 -> VideoStatus.ERROR
                6 -> VideoStatus.UPLOAD_FAILED
                else -> VideoStatus.ERROR
            },
            size = storageSize?.inMb ?: 0.0,
            viewCount = views?.toString() ?: "N/A",
        )
    }

    private val Long?.inMb: Double?
        get() = this?.div(1024.0 * 1024.0)
}