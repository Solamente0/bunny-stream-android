package net.bunnystream.android.library

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import net.bunnystream.android.App
import net.bunnystream.android.library.model.Video
import net.bunnystream.android.ui.AppState

const val LIBRARY_ROUTE = "library"

fun NavController.navigateToLibrary(navOptions: NavOptions? = null) {
    this.navigate(LIBRARY_ROUTE, navOptions)
}

fun NavGraphBuilder.libraryScreen(
    appState: AppState,
    navigateToSettings: () -> Unit,
    navigateToPlayer: (Long, Video) -> Unit,
    navigateToStreaming: () -> Unit,
) {
    composable(
        route = LIBRARY_ROUTE,
    ) {
        LibraryRoute(
            appState = appState,
            navigateToSettings = navigateToSettings,
            navigateToStreaming = navigateToStreaming,
            navigateToPlayer = { libId, videoId -> navigateToPlayer(libId, videoId) },
            localPrefs = App.di.localPrefs
        )
    }
}
