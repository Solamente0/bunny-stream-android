package net.bunnystream.android.library

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import net.bunnystream.android.R
import net.bunnystream.android.library.model.LibraryUiState
import net.bunnystream.android.library.model.Video
import net.bunnystream.android.library.model.VideoUploadUiState
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

    Log.d("LibraryRoute", "viewModel: $viewModel")
    val showAccessKeyNeeded = localPrefs.accessKey.isEmpty()

    val pickVideoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            Log.d("LibraryRoute", "uri=$uri")
            if(uri != null) {
                viewModel.uploadVideo(uri)
            }
        }
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uploadingUiState by viewModel.uploadUiState.collectAsStateWithLifecycle()

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
        libraryId = viewModel.libraryId,
        onUploadVideoClicked = {
            pickVideoLauncher.launch(
                PickVisualMediaRequest(
                    mediaType = ActivityResultContracts.PickVisualMedia.VideoOnly
                )
            )
        },
        uploadingUiState = uploadingUiState,
        onDismissUploadErrorClicked = viewModel::clearUploadError,
        onCancelUploadClicked = viewModel::cancelUpload,
        onTusUploadOptionChanged = {
            viewModel.onTusUploadOptionChanged(it)
        },
        useTusUpload = viewModel.useTusUpload
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
    uiState: LibraryUiState,
    onUploadVideoClicked: () -> Unit,
    uploadingUiState: VideoUploadUiState,
    onDismissUploadErrorClicked: () -> Unit,
    onCancelUploadClicked: () -> Unit,
    onTusUploadOptionChanged: (Boolean) -> Unit,
    useTusUpload: Boolean
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

                    Column(modifier = modifier) {
                        Box(modifier = modifier
                            .fillMaxWidth()
                            .weight(1F)){
                            when (uiState) {
                                LibraryUiState.LibraryUiEmpty -> { }
                                is LibraryUiState.LibraryUiLoaded -> {
                                    LazyColumn(
                                        modifier = Modifier
                                            .fillMaxWidth()
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
                                LibraryUiState.LibraryUiLoading -> {
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
                        VideoUploadControls(
                            uploadingUiState = uploadingUiState,
                            onUploadVideoClicked = onUploadVideoClicked,
                            onDismissUploadErrorClicked = onDismissUploadErrorClicked,
                            onCancelUploadClicked = onCancelUploadClicked,
                            onTusUploadOptionChanged = onTusUploadOptionChanged,
                            useTusUpload = useTusUpload,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun VideoUploadControls(
    uploadingUiState: VideoUploadUiState,
    onUploadVideoClicked: () -> Unit,
    onDismissUploadErrorClicked: () -> Unit,
    onCancelUploadClicked: () -> Unit,
    onTusUploadOptionChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    useTusUpload: Boolean
){
    Box(modifier = modifier.fillMaxWidth()){
        when(uploadingUiState) {
            VideoUploadUiState.NotUploading -> {
                Column(modifier = modifier.fillMaxWidth()) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .toggleable(
                                value = useTusUpload,
                                onValueChange = {
                                    onTusUploadOptionChanged(it)
                                },
                                role = Role.Checkbox
                            )
                            .padding(16.dp),
                        verticalAlignment = CenterVertically
                    ) {
                        Checkbox(
                            checked = useTusUpload,
                            onCheckedChange = null
                        )
                        Text(
                            text = stringResource(id = R.string.checkbox_enable_tus),
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    Button(
                        modifier = modifier.fillMaxWidth(),
                        onClick = onUploadVideoClicked
                    ) {
                        Text(text = stringResource(id = R.string.button_upload_video))
                    }
                }
            }
            is VideoUploadUiState.UploadError -> {
                Row(modifier = modifier) {
                    Text(
                        modifier = modifier
                            .weight(1F)
                            .align(CenterVertically),
                        text = "Error: ${uploadingUiState.message}"
                    )
                    IconButton(onClick = onDismissUploadErrorClicked) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = null
                        )
                    }
                }
            }
            is VideoUploadUiState.Uploading -> {
                Row(modifier = modifier.fillMaxWidth()) {
                    Column(modifier = modifier
                        .weight(1F)
                        .align(CenterVertically)) {
                        Text(
                            modifier = modifier,
                            text = "Progress: ${uploadingUiState.progress}%"
                        )
                        LinearProgressIndicator(
                            modifier = modifier.fillMaxWidth(),
                            progress = uploadingUiState.progress.toFloat() / 100
                        )
                    }

                    IconButton(
                        modifier = modifier.align(CenterVertically),
                        onClick = onCancelUploadClicked) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = null
                        )
                    }
                }
            }

            VideoUploadUiState.Preparing -> {
                Text(
                    modifier = modifier,
                    text = "Preparing upload..."
                )
            }
        }
    }
}

@Preview
@Composable
private fun VideoUploadControlsPreview(){
    BunnyStreamTheme {
        VideoUploadControls(
            uploadingUiState = VideoUploadUiState.Uploading(30),
            onUploadVideoClicked = {},
            onDismissUploadErrorClicked = {},
            onCancelUploadClicked = {},
            onTusUploadOptionChanged = {},
            useTusUpload = true,
        )
    }
}

@Preview
@Composable
private fun VideoUploadControlsDefaultPreview(){
    BunnyStreamTheme {
        VideoUploadControls(
            uploadingUiState = VideoUploadUiState.NotUploading,
            onUploadVideoClicked = {},
            onDismissUploadErrorClicked = {},
            onCancelUploadClicked = {},
            onTusUploadOptionChanged = {},
            useTusUpload = true,
        )
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
            uiState = LibraryUiState.LibraryUiEmpty,
            libraryId = -1,
            onUploadVideoClicked = {},
            uploadingUiState = VideoUploadUiState.Uploading(50),
            onDismissUploadErrorClicked = {},
            onCancelUploadClicked = {},
            onTusUploadOptionChanged = {},
            useTusUpload = true,
        )
    }
}
