package net.bunnystream.android.demo.player

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import net.bunnystream.android.demo.library.model.Video
import net.bunnystream.android.demo.ui.AppState
import java.net.URLEncoder

const val PLAYER_ROUTE = "player"
const val VIDEO_ID = "videoId"

fun NavController.navigateToPlayer(video: Video, navOptions: NavOptions? = null) {
    val encodedVideoId = URLEncoder.encode(video.id, "UTF-8")
    this.navigate("$PLAYER_ROUTE/$encodedVideoId", navOptions)
}

fun NavGraphBuilder.playerScreen(appState: AppState) {
    composable(
        route = "$PLAYER_ROUTE/{$VIDEO_ID}",
        arguments = listOf(
            navArgument(VIDEO_ID) { type = NavType.StringType },
        ),
    ) {
        val videoId = it.arguments?.getString(VIDEO_ID)!!
        PlayerRoute(
            appState = appState,
            videoId = videoId
        )
    }
}