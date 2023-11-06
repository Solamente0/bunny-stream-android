package net.bunnystream.player.ui.widget

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.ColorUtils
import androidx.core.view.isVisible
import androidx.media3.common.Player
import androidx.media3.ui.DefaultTimeBar
import androidx.media3.ui.PlayerControlView
import androidx.media3.ui.PlayerView
import androidx.mediarouter.app.MediaRouteButton
import com.google.android.gms.cast.framework.CastButtonFactory
import net.bunnystream.player.PlayerStateListener
import net.bunnystream.player.PlayerType
import net.bunnystream.player.R
import net.bunnystream.player.common.BunnyPlayer
import net.bunnystream.player.model.PlayerIconSet

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
class BunnyPlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : PlayerView(context, attrs, defStyleAttr) {

    companion object {
        private const val TAG = "BunnyPlayerView"
    }

    interface FullscreenListener {
        fun onFullscreenToggleClicked()
    }

    var isFullscreen: Boolean = false
        set(value) {
            field = value
            applyStyle()
        }

    private val playStateListener = object : PlayerStateListener {
        override fun onPlayingChanged(isPlaying: Boolean) {
            playPauseButton.state = if(isPlaying) {
                ToggleableImageButton.State.STATE_TOGGLED
            } else {
                ToggleableImageButton.State.STATE_DEFAULT
            }
        }

        override fun onMutedChanged(isMuted: Boolean) {
            muteButton.state = if(isMuted) {
                ToggleableImageButton.State.STATE_TOGGLED
            } else {
                ToggleableImageButton.State.STATE_DEFAULT
            }
        }

        override fun onPlaybackSpeedChanged(speed: Float) {

        }

        override fun onLoadingChanged(isLoading: Boolean) {

        }

        override fun onPlayerTypeChanged(player: Player, playerType: PlayerType) {
            updatePlayer(player, playerType)
        }
    }

    var fullscreenListener: FullscreenListener? = null

    var bunnyPlayer: BunnyPlayer? = null
        set(value) {
            field = value
            field?.playerStateListener = playStateListener
            player = bunnyPlayer?.currentPlayer
            setPlayerControls()
        }

    var iconSet: PlayerIconSet = PlayerIconSet()
        set(value) {
            field = value
            applyStyle()
        }

    private val playPauseButton by lazy {
        findViewById<ToggleableImageButton>(R.id.bunny_play_pause)
    }

    private val replyButton by lazy {
        findViewById<ImageButton>(R.id.bunny_replay)
    }

    private val forwardButton by lazy {
        findViewById<ImageButton>(R.id.bunny_forward)
    }

    private val settingsButton by lazy {
        findViewById<ImageButton>(R.id.exo_settings)
    }

    private val muteButton by lazy {
        findViewById<ToggleableImageButton>(R.id.bunny_mute)
    }

    private val castButton by lazy {
        findViewById<MediaRouteButton>(R.id.bunny_cast)
    }

    private val fullScreenButton by lazy {
        findViewById<ImageButton>(R.id.bunny_fullscreen)
    }

    private val progressTextView by lazy {
        findViewById<TextView>(R.id.exo_position)
    }

    private val timeBar by lazy {
        findViewById<DefaultTimeBar>(R.id.exo_progress)
    }

    private fun setPlayerControls(){
        playPauseButton.setOnClickListener {
            if (bunnyPlayer?.isPlaying() == true) {
                bunnyPlayer?.pause()
            } else {
                bunnyPlayer?.play()
            }
        }

        muteButton.setOnClickListener {
            if (bunnyPlayer?.isMuted() == true) {
                bunnyPlayer?.unmute()
            } else {
                bunnyPlayer?.mute()
            }
        }

        replyButton.setOnClickListener {
            bunnyPlayer?.replay()
        }

        forwardButton.setOnClickListener {
            bunnyPlayer?.skipForward()
        }

        fullScreenButton.setOnClickListener {
            fullscreenListener?.onFullscreenToggleClicked()
        }

        CastButtonFactory.setUpMediaRouteButton(context, castButton)
    }

    private fun updatePlayer(player: Player, playerType: PlayerType){
        Log.d(TAG, "updatePlayer player=$player playerTpe=$playerType")
        this.player = player

        when(playerType) {
            PlayerType.DEFAULT_PLAYER -> {
                controllerShowTimeoutMs = PlayerControlView.DEFAULT_SHOW_TIMEOUT_MS
                defaultArtwork = null
                controllerHideOnTouch = true
                muteButton.isVisible = true
            }
            PlayerType.CAST_PLAYER -> {
                controllerShowTimeoutMs = 0
                showController()
                defaultArtwork = ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.ic_cast_connected_400,
                    null
                )
                controllerHideOnTouch = false
                muteButton.isVisible = false
            }
        }
    }

    private fun applyStyle() {
        playPauseButton.setStateIcons(iconSet.playIcon, iconSet.pauseIcon)
        playPauseButton.tintColor = iconSet.tintColor

        replyButton.setImageResource(iconSet.rewindIcon)
        replyButton.imageTintList = ColorStateList.valueOf(iconSet.tintColor)

        forwardButton.setImageResource(iconSet.forwardIcon)
        forwardButton.imageTintList = ColorStateList.valueOf(iconSet.tintColor)

        settingsButton.setImageResource(iconSet.settingsIcon)
        settingsButton.imageTintList = ColorStateList.valueOf(iconSet.tintColor)

        muteButton.setStateIcons(iconSet.volumeOnIcon, iconSet.volumeOffIcon)
        muteButton.tintColor = iconSet.tintColor

        val fullScreenIcon = if(isFullscreen) {
            iconSet.fullscreenOffIcon
        } else {
            iconSet.fullscreenOnIcon
        }

        fullScreenButton.setImageResource(fullScreenIcon)
        fullScreenButton.imageTintList = ColorStateList.valueOf(iconSet.tintColor)

        progressTextView.setTextColor(iconSet.tintColor)

        timeBar.setPlayedColor(iconSet.tintColor)
        timeBar.setScrubberColor(iconSet.tintColor)
        timeBar.setAdMarkerColor(iconSet.tintColor)

        // 0 - fully transparent
        timeBar.setUnplayedColor(ColorUtils.setAlphaComponent(iconSet.tintColor, 80))
        timeBar.setBufferedColor(ColorUtils.setAlphaComponent(iconSet.tintColor, 150))
    }
}