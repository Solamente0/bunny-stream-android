package net.bunnystream.android.library.model

sealed class LibraryUiState {

    object LibraryUiEmpty : LibraryUiState()

    object LibraryUiLoading : LibraryUiState()

    data class LibraryUiLoaded(
        val videos: List<Video>
    ) : LibraryUiState()
}
