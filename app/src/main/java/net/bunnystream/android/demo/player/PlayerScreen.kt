package net.bunnystream.android.demo.player

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
import net.bunnystream.android.demo.R
import net.bunnystream.android.demo.ui.AppState
import net.bunnystream.android.demo.ui.theme.BunnyStreamTheme
import net.bunnystream.bunnystreamplayer.ui.BunnyStreamPlayer

@Composable
fun PlayerRoute(
    appState: AppState,
    videoId: String,
    modifier: Modifier = Modifier,
) {
    PlayerScreen(
        modifier = modifier,
        videoId = videoId,
        onBackClicked = { appState.navController.popBackStack() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlayerScreen(
    modifier: Modifier = Modifier,
    videoId: String,
    onBackClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
            Surface(shadowElevation = 3.dp) {
                TopAppBar(
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
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
                videoId = videoId,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun BunnyPlayerComposable(
    videoId: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            BunnyStreamPlayer(context)
        },
        update = {
            it.playVideo(videoId)
        },
        modifier = modifier.background(Color.Gray)
    )
}

@Preview
@Composable
private fun PlayerScreenPreview() {
    BunnyStreamTheme {
        BunnyPlayerComposable(
            videoId = "",
            modifier = Modifier.fillMaxSize()
        )
    }
}