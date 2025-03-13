package net.bunnystream.android.demo.library.model

sealed class LibraryUiState {

    object LibraryUiEmpty : LibraryUiState()

    object LibraryUiLoading : LibraryUiState()

    data class LibraryUiLoaded(
        val videos: List<Video>
    ) : LibraryUiState()
}
