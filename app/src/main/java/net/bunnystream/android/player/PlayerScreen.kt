package net.bunnystream.android.player

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import net.bunnystream.android.R
import net.bunnystream.android.common.LifecycleAware
import net.bunnystream.android.ui.AppState
import net.bunnystream.android.ui.theme.BunnyStreamTheme
import net.bunnystream.player.BunnyPlayer
import net.bunnystream.player.BunnyPlayerBuilder
import net.bunnystream.player.model.BunnyPlayerIconSet

@Composable
fun PlayerRoute(
    appState: AppState,
    modifier: Modifier = Modifier,
) {
    PlayerScreen(
        modifier = modifier,
        onBackClicked = { appState.navController.popBackStack() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlayerScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
) {
    var bunnyPlayer: BunnyPlayer? by remember { mutableStateOf(null) }
    val bunnyPlayerBuilder = BunnyPlayerBuilder(LocalContext.current)
    LifecycleAware(
        onPause = { bunnyPlayer?.pause() },
        onResume = { bunnyPlayer?.play() }
    )
    Scaffold(
        topBar = {
            Surface(shadowElevation = 3.dp) {
                TopAppBar(
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(stringResource(id = R.string.screen_player))
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = onBackClicked
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    },
                )
            }
        },
    ) { innerPadding ->

        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            BunnyPlayerComposable(
                url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                bunnyPlayerBuilder = bunnyPlayerBuilder,
                onBunnyPlayerCreated = { bunnyPlayer = it },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun BunnyPlayerComposable(
    url: String,
    bunnyPlayerBuilder: BunnyPlayerBuilder,
    onBunnyPlayerCreated: (BunnyPlayer) -> Unit,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            val bunnyVideoPlayer = FrameLayout(context)
            bunnyVideoPlayer
        },
        update = { view ->
            val bunnyPlayer = bunnyPlayerBuilder
                .setIconSet(
                    BunnyPlayerIconSet(
                    net.bunnystream.player.R.drawable.ic_play,
                    net.bunnystream.player.R.drawable.ic_pause,
                    net.bunnystream.player.R.drawable.ic_rewind,
                    net.bunnystream.player.R.drawable.ic_forward,
                    net.bunnystream.player.R.drawable.ic_settings,
                    net.bunnystream.player.R.drawable.ic_sound_on,
                    net.bunnystream.player.R.drawable.ic_sound_off,
                    net.bunnystream.player.R.drawable.ic_cast,
                    net.bunnystream.player.R.drawable.ic_fullscreen_on,
                    net.bunnystream.player.R.drawable.ic_fullscreen_off,
                    )
                ).build(view as ViewGroup)

            onBunnyPlayerCreated(bunnyPlayer)
            bunnyPlayer.loadVideo(url)
            bunnyPlayer.play()
        },
        modifier = modifier
    )
}

@Preview
@Composable
private fun PlayerScreenPreview() {
    val bunnyPlayerBuilder = BunnyPlayerBuilder(LocalContext.current)
    BunnyStreamTheme {
        BunnyPlayerComposable(
            url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            bunnyPlayerBuilder = bunnyPlayerBuilder,
            onBunnyPlayerCreated = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
