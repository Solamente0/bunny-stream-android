package net.bunnystream.android.player

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import net.bunnystream.android.library.model.Video
import net.bunnystream.android.ui.AppState
import java.net.URLEncoder

const val PLAYER_ROUTE = "player"
const val LIBRARY_ID = "libraryId"
const val VIDEO_ID = "videoId"

fun NavController.navigateToPlayer(libraryId: Long, video: Video, navOptions: NavOptions? = null) {
    val encodedVideoId = URLEncoder.encode(video.id, "UTF-8")
    this.navigate("$PLAYER_ROUTE/$libraryId/$encodedVideoId", navOptions)
}

fun NavGraphBuilder.playerScreen(appState: AppState) {
    composable(
        route = "$PLAYER_ROUTE/{$LIBRARY_ID}/{$VIDEO_ID}",
        arguments = listOf(
            navArgument(LIBRARY_ID) { type = NavType.LongType },
            navArgument(VIDEO_ID) { type = NavType.StringType },
        ),
    ) {
        val libraryId = it.arguments?.getLong(LIBRARY_ID)!!
        val videoId = it.arguments?.getString(VIDEO_ID)!!
        PlayerRoute(
            appState = appState,
            libraryId = libraryId,
            videoId = videoId
        )
    }
}