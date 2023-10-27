package net.bunnystream.android.player

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import net.bunnystream.android.ui.AppState

const val PLAYER_ROUTE = "player"

fun NavController.navigateToPlayer(navOptions: NavOptions? = null) {
    this.navigate(PLAYER_ROUTE, navOptions)
}

fun NavGraphBuilder.playerScreen(appState: AppState) {
    composable(
        route = PLAYER_ROUTE,
    ) {
        PlayerRoute(appState = appState)
    }
}