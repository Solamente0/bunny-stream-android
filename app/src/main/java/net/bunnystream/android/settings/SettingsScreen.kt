package net.bunnystream.android.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
    SettingsScreen(
        modifier = modifier,
        onBackClicked = { appState.navController.popBackStack() },
        accessKey = viewModel.accessKey,
        onAccessKeyUpdated = { viewModel.updateKeys(it, viewModel.cdnHostname) },
        cdnHostname = viewModel.cdnHostname,
        onCdnHostnameUpdated = { viewModel.updateKeys(viewModel.accessKey, it) }
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
    onCdnHostnameUpdated: (String) -> Unit
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
                value = cdnHostname,
                onValueChange = onCdnHostnameUpdated,
                label = { Text(stringResource(id = R.string.hint_cdn_hostname)) }
            )
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
            onCdnHostnameUpdated = {}
        )
    }
}
