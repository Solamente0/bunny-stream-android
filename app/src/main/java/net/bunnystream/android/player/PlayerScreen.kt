package net.bunnystream.android.player

import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import net.bunnystream.android.R
import net.bunnystream.android.ui.AppState
import net.bunnystream.android.ui.theme.BunnyStreamTheme
import net.bunnystream.player.ui.BunnyVideoPlayer

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
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun BunnyPlayerComposable(
    url: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            BunnyVideoPlayer(context)
        },
        update = {
            it.loadVideo(url)
            it.play()
        },
        modifier = modifier.background(Color.Gray)
    )
}

@Preview
@Composable
private fun PlayerScreenPreview() {
    BunnyStreamTheme {
        BunnyPlayerComposable(
            url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            modifier = Modifier.fillMaxSize()
        )
    }
}