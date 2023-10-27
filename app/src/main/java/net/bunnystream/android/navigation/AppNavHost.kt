package net.bunnystream.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import net.bunnystream.android.library.LIBRARY_ROUTE
import net.bunnystream.android.library.libraryScreen
import net.bunnystream.android.player.navigateToPlayer
import net.bunnystream.android.player.playerScreen
import net.bunnystream.android.settings.navigateToSettings
import net.bunnystream.android.settings.settingsScreen
import net.bunnystream.android.ui.AppState

@Composable
fun AppNavHost(
    appState: AppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = LIBRARY_ROUTE,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        libraryScreen(
            appState = appState,
            navigateToSettings = navController::navigateToSettings,
            navigateToPlayer = navController::navigateToPlayer
        )
        settingsScreen(appState = appState)
        playerScreen(appState = appState)
    }
}
