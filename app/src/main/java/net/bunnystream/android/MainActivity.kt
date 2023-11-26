package net.bunnystream.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import net.bunnystream.android.ui.App
import net.bunnystream.android.ui.theme.BunnyStreamTheme
import net.bunnystream.stream.StreamActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BunnyStreamTheme {
                App()
            }
        }

        startActivity(Intent(this, StreamActivity::class.java))
    }
}