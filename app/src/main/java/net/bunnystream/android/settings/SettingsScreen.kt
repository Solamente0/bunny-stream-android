package net.bunnystream.android.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.bunnystream.android.R
import net.bunnystream.android.ui.AppState
import net.bunnystream.android.ui.theme.BunnyStreamTheme

@Composable
fun SettingsRoute(
    appState: AppState,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel(),
) {

    var accessKey by remember { mutableStateOf(viewModel.accessKey) }
    var cdnHostname by remember { mutableStateOf(viewModel.cdnHostname) }
    var libraryId by remember { mutableStateOf(viewModel.libraryId.toString()) }

    val modified = accessKey != viewModel.accessKey
            || cdnHostname != viewModel.cdnHostname
            || libraryId != viewModel.libraryId.toString()

    SettingsScreen(
        modifier = modifier,
        onBackClicked = { appState.navController.popBackStack() },
        accessKey = accessKey,
        onAccessKeyUpdated = { accessKey = it },
        cdnHostname = cdnHostname,
        onCdnHostnameUpdated = { cdnHostname = it },
        libraryId = libraryId,
        onLibraryIdUpdated = { libraryId = it },
        onSaveClicked = {
            viewModel.updateKeys(accessKey, cdnHostname, libraryId.toLongOrDefault(-1))
            appState.navController.popBackStack()
        },
        settingsModified = modified
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    accessKey: String,
    onAccessKeyUpdated: (String) -> Unit,
    cdnHostname: String,
    onCdnHostnameUpdated: (String) -> Unit,
    libraryId: String,
    onLibraryIdUpdated: (String) -> Unit,
    onSaveClicked: () -> Unit,
    settingsModified: Boolean
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
                        Text(stringResource(id = R.string.screen_settings))
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

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            OutlinedTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = accessKey,
                onValueChange = onAccessKeyUpdated,
                label = { Text(stringResource(id = R.string.hint_access_key)) }
            )

            OutlinedTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = libraryId,
                onValueChange = onLibraryIdUpdated,
                label = { Text(stringResource(id = R.string.hint_library_id)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )

            OutlinedTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = cdnHostname,
                onValueChange = onCdnHostnameUpdated,
                label = { Text(stringResource(id = R.string.hint_cdn_hostname)) }
            )

            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = onSaveClicked,
            ) {
                Text(text = stringResource(id = R.string.button_save_settings))
            }
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    BunnyStreamTheme {
        SettingsScreen(
            onBackClicked = {},
            accessKey = "",
            onAccessKeyUpdated = {},
            cdnHostname = "",
            onCdnHostnameUpdated = {},
            libraryId = "",
            onLibraryIdUpdated = {},
            onSaveClicked = {},
            settingsModified = false
        )
    }
}
