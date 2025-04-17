package net.bunnystream.android.demo.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import net.bunnystream.android.demo.R
import net.bunnystream.android.demo.ui.AppState
import net.bunnystream.android.demo.ui.theme.BunnyStreamTheme
import net.bunnystream.android.demo.library.model.Video
import net.bunnystream.android.demo.library.model.VideoStatus
import net.bunnystream.bunnystreamplayer.ui.BunnyStreamPlayer
import java.util.Locale

@Composable
fun PlayerRoute(
    appState: AppState,
    videoId: String,
    modifier: Modifier = Modifier,
    viewModel: PlayerViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PlayerScreen(
        modifier = modifier,
        videoId = videoId,
        uiState,
        onBackClicked = { appState.navController.popBackStack() },
    )

    LaunchedEffect(key1 = "load", block = { viewModel.loadVideo(videoId) })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    modifier: Modifier = Modifier,
    videoId: String,
    uiState: VideoUiState,
    onBackClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
            Surface(shadowElevation = 3.dp) {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor    = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    title = { Text(stringResource(id = R.string.screen_player)) },
                    navigationIcon = {
                        IconButton(onClick = onBackClicked) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            BunnyPlayerComposable(
                videoId = videoId,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            when (uiState) {
                VideoUiState.VideoUiEmpty ->{}
                is VideoUiState.VideoUiLoaded -> {

                    val props = listOf(
                        VideoProperty("Title", uiState.video.name),
                        VideoProperty("Duration", uiState.video.duration),
                        VideoProperty("Views", uiState.video.viewCount),
                        VideoProperty("Size", String.format(Locale.US, "%.2f MB", uiState.video.size))
                    )
                    VideoPropertiesCard(properties = props)
                }
                VideoUiState.VideoUiLoading -> {}
            }
        }
    }
}

data class VideoProperty(val label: String, val value: String)

@Composable
fun VideoPropertiesCard(properties: List<VideoProperty>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape     = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors    = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            properties.forEachIndexed { index, prop ->
                VideoPropertyItem(prop)
                if (index < properties.lastIndex) {
                    Divider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.18f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun VideoPropertyItem(prop: VideoProperty) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = prop.label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = prop.value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun BunnyPlayerComposable(
    videoId: String,
    modifier: Modifier = Modifier
) {
    if (LocalInspectionMode.current) {
        // Preview placeholder
        Box(modifier
            .background(Color.DarkGray)
            .then(modifier)) {
            Text(
                "Player preview",
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
            )
        }
    } else {
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
}

@Preview
@Composable
private fun PlayerScreenPreview() {
    BunnyStreamTheme {
        PlayerScreen(
            videoId = "12345",
            uiState = VideoUiState.VideoUiLoaded(
                Video(
                    id = "12345",
                    name = "Sample Video",
                    duration = "00:10:00",
                    status = VideoStatus.FINISHED,
                    viewCount = "1000",
                    size = 50.0
                )
            ),
            onBackClicked = {},
        )
    }
}