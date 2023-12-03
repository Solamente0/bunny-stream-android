package net.bunnystream.android.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import net.bunnystream.android.library.LIBRARY_ROUTE
import net.bunnystream.android.library.libraryScreen
import net.bunnystream.android.player.navigateToPlayer
import net.bunnystream.android.player.playerScreen
import net.bunnystream.android.settings.navigateToSettings
import net.bunnystream.android.settings.settingsScreen
import net.bunnystream.android.ui.AppState
import net.bunnystream.android.ui.streaming.StreamActivity

@Composable
fun AppNavHost(
    appState: AppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = LIBRARY_ROUTE,
) {
    val navController = appState.navController
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        libraryScreen(
            appState = appState,
            navigateToSettings = navController::navigateToSettings,
            navigateToPlayer = navController::navigateToPlayer,
            navigateToStreaming = {
                context.startActivity(Intent(context, StreamActivity::class.java))
            }
        )
        settingsScreen(appState = appState)
        playerScreen(appState = appState)
    }
}
