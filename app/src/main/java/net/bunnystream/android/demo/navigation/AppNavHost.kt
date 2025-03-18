package net.bunnystream.android.demo.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import net.bunnystream.android.demo.library.LIBRARY_ROUTE
import net.bunnystream.android.demo.library.libraryScreen
import net.bunnystream.android.demo.player.navigateToPlayer
import net.bunnystream.android.demo.player.playerScreen
import net.bunnystream.android.demo.settings.navigateToSettings
import net.bunnystream.android.demo.settings.settingsScreen
import net.bunnystream.android.demo.ui.AppState
import net.bunnystream.android.demo.ui.recording.RecordingActivity

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
                context.startActivity(Intent(context, RecordingActivity::class.java))
            }
        )
        settingsScreen(appState = appState)
        playerScreen(appState = appState)
    }
}
