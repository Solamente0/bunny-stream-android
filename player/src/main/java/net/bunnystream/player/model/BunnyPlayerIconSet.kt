package net.bunnystream.player.model

import androidx.annotation.DrawableRes

data class BunnyPlayerIconSet(
    @DrawableRes val playIcon: Int,
    @DrawableRes val pauseIcon: Int,
    @DrawableRes val rewindIcon: Int,
    @DrawableRes val forwardIcon: Int,
    @DrawableRes val settingsIcon: Int,
    @DrawableRes val volumeOnIcon: Int,
    @DrawableRes val volumeOffIcon: Int,
    @DrawableRes val streamingIcon: Int,
    @DrawableRes val fullscreenOnIcon: Int,
    @DrawableRes val fullscreenOffIcon: Int,
)
