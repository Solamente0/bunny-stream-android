package net.bunnystream.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import net.bunnystream.android.ui.App
import net.bunnystream.android.ui.theme.BunnyStreamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BunnyStreamTheme {
                App()
            }
        }
    }
}