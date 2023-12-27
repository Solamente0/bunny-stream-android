package net.bunnystream.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import net.bunnystream.android.ui.App
import net.bunnystream.android.ui.theme.BunnyStreamTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BunnyStreamTheme {
                App()
            }
        }
    }
}