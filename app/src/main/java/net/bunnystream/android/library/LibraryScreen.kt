package net.bunnystream.android.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import net.bunnystream.android.R
import net.bunnystream.android.library.model.LibraryUiEmpty
import net.bunnystream.android.library.model.LibraryUiLoaded
import net.bunnystream.android.library.model.LibraryUiLoading
import net.bunnystream.android.library.model.LibraryUiState
import net.bunnystream.android.library.model.Video
import net.bunnystream.android.settings.LocalPrefs
import net.bunnystream.android.ui.AppState
import net.bunnystream.android.ui.theme.BunnyStreamTheme

@Composable
fun LibraryRoute(
    appState: AppState,
    modifier: Modifier = Modifier,
    navigateToSettings: () -> Unit,
    localPrefs: LocalPrefs,
    viewModel: LibraryViewModel = viewModel(),
) {
    val showAccessKeyNeeded = localPrefs.accessKey.isEmpty()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errorState by viewModel.errorState.collectAsStateWithLifecycle(
        null,
        LocalLifecycleOwner.current
    )

    if(errorState != null) {
        AlertDialog(
            icon = {
                Icon(Icons.Filled.Warning, contentDescription = null)
            },
            title = {
                Text(text = stringResource(id = R.string.dialog_title_error))
            },
            text = {
                Text(text = errorState?.message ?: "")
            },
            onDismissRequest = viewModel::onErrorDismissed,
            confirmButton = {
                TextButton(
                    onClick = viewModel::onErrorDismissed
                ) {
                    Text(text = stringResource(id = R.string.dialog_button_ok))
                }
            },
        )
    }

    LibraryScreen(
        modifier = modifier,
        navigateToSettings = navigateToSettings,
        showAccessKeyNeeded = showAccessKeyNeeded,
        onLoadLibraryClicked = viewModel::loadLibrary,
        uiState = uiState,
        libraryId = viewModel.libraryId
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LibraryScreen(
    modifier: Modifier = Modifier,
    navigateToSettings: () -> Unit,
    showAccessKeyNeeded: Boolean,
    onLoadLibraryClicked: (String) -> Unit,
    libraryId: Long,
    uiState: LibraryUiState
) {

    var libId by remember { mutableStateOf(libraryId.toString()) }

    Scaffold(
        topBar = {
            Surface(shadowElevation = 3.dp) {
                TopAppBar(
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(stringResource(id = R.string.screen_library))
                    },
                    actions = {
                        IconButton(onClick = navigateToSettings) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = null
                            )
                        }
                    },
                )
            }
        },
    ) { innerPadding ->

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if(showAccessKeyNeeded) {
                AccessKey(
                    navigateToSettings = navigateToSettings,
                    modifier = modifier.align(Alignment.Center)
                )
            } else {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {

                    Row(modifier = modifier) {
                        OutlinedTextField(
                            modifier = modifier.weight(1F),
                            value = libId,
                            onValueChange = { libId = it },
                            label = { Text(stringResource(id = R.string.hint_library_id)) }
                        )

                        Button(
                            onClick = { onLoadLibraryClicked(libId) },
                            modifier = modifier
                                .align(CenterVertically)
                                .padding(start = 10.dp)
                        ) {
                            Text(text = stringResource(id = R.string.button_load))
                        }
                    }

                    when (uiState) {
                        LibraryUiEmpty -> {}
                        is LibraryUiLoaded -> {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 10.dp)
                            ){
                                items(
                                    items = uiState.videos,
                                    key = { video -> video.id }
                                ){
                                    Text(
                                        modifier = Modifier.padding(10.dp),
                                        text = it.name
                                    )
                                }
                            }
                        }
                        LibraryUiLoading -> {
                            Box(modifier = modifier.fillMaxSize()){
                                CircularProgressIndicator(
                                    modifier = modifier
                                        .width(64.dp)
                                        .align(Center),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AccessKey(
    navigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(id = R.string.label_set_access_key))
        Button(onClick = navigateToSettings) {
            Text(text = stringResource(id = R.string.button_settings))
        }
    }
}

@Preview
@Composable
private fun LibraryScreenPreview() {

    val videos = listOf(
        Video("1", "Video 1", "1m"),
        Video("2", "Video 2", "1m"),
        Video("3", "Video 3", "1m")
    )

    BunnyStreamTheme {
        LibraryScreen(
            navigateToSettings = {},
            showAccessKeyNeeded = false,
            onLoadLibraryClicked = {},
            uiState = LibraryUiLoading,
            libraryId = -1
        )
    }
}
