package net.bunnystream.player.common

import android.annotation.SuppressLint
import net.bunnystream.player.R
import net.bunnystream.player.model.BunnyPlayerIconSet

class Constants {
    companion object {
        val DEFAULT_ICON_SET = BunnyPlayerIconSet(
            R.drawable.ic_play,
            R.drawable.ic_pause,
            R.drawable.ic_rewind,
            R.drawable.ic_forward,
            R.drawable.ic_settings,
            R.drawable.ic_sound_on,
            R.drawable.ic_sound_off,
            R.drawable.ic_cast,
            R.drawable.ic_fullscreen_on,
            R.drawable.ic_fullscreen_off,
        )
        const val DEFAULT_THEME_COLOR = "#FFFFFF"
        @SuppressLint("PrivateResource")
        val DEFAULT_FONT = androidx.media3.ui.R.font.roboto_medium_numbers
    }
}