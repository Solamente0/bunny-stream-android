package net.bunnystream.player.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.TextView
import androidx.media3.ui.PlayerView
import net.bunnystream.player.BunnyPlayer
import net.bunnystream.player.R
import net.bunnystream.player.databinding.ViewFullScreenBunnyVideoPlayerBinding
import net.bunnystream.player.model.BunnyPlayerIconSet

@SuppressLint("ViewConstructor")
class FullScreenBunnyVideoPlayer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val playerManager: BunnyPlayer,
    private val iconSet: BunnyPlayerIconSet,
) : PlayerView(context, attrs, defStyleAttr) {

    private var binding = ViewFullScreenBunnyVideoPlayerBinding.inflate(LayoutInflater.from(context), this, true)

    val playerView by lazy {
        binding.playerView
    }
    private val playButton by lazy {
        binding.playerView.findViewById<ImageButton>(R.id.bunny_play_pause)
    }
    private val rewindButton by lazy {
        binding.playerView.findViewById<TextView>(androidx.media3.ui.R.id.exo_rew_with_amount)
    }
    private val forwardButton by lazy {
        binding.playerView.findViewById<TextView>(androidx.media3.ui.R.id.exo_ffwd_with_amount)
    }
    private val settingsButton by lazy {
        binding.playerView.findViewById<ImageButton>(androidx.media3.ui.R.id.exo_settings)
    }
    private val volumeButton by lazy {
        binding.playerView.findViewById<ImageButton>(R.id.bunny_volume)
    }
    private val streamingButton by lazy {
        binding.playerView.findViewById<ImageButton>(R.id.bunny_streaming)
    }
    val fullScreenButton by lazy {
        binding.playerView.findViewById<ImageButton>(R.id.bunny_fullscreen)
    }

    var isPlaying: Boolean = false
        set(value) {
            field = value
            playButton.setImageResource(if (value) iconSet.pauseIcon else iconSet.playIcon)
        }
    var isMute: Boolean = false
        set(value) {
            field = value
            volumeButton.setImageResource(if (value) iconSet.volumeOffIcon else iconSet.volumeOnIcon)
        }

    init {
        setIconResources()
        playerListener()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (newConfig?.orientation == Configuration.ORIENTATION_PORTRAIT) {
            fullScreenButton.performClick()
        }
    }

    private fun playerListener() {
        playButton.setOnClickListener {
            if (playerManager.isPlaying()) {
                playerManager.pause()
            } else {
                playerManager.play()
            }
        }
        volumeButton.setOnClickListener {
            if (playerManager.getVolume() == 0f) {
                playerManager.unmute()
            } else {
                playerManager.mute()
            }
        }
    }

    private fun setIconResources() {
        playButton.setImageResource(if (playerManager.isPlaying()) iconSet.pauseIcon else iconSet.playIcon)
        rewindButton.setBackgroundResource(iconSet.rewindIcon)
        forwardButton.setBackgroundResource(iconSet.forwardIcon)
        settingsButton.setImageResource(iconSet.settingsIcon)
        volumeButton.setImageResource(if (playerManager.getVolume() == 0f) iconSet.volumeOffIcon else iconSet.volumeOnIcon)
        streamingButton.setImageResource(iconSet.streamingIcon)
        fullScreenButton.setImageResource(iconSet.fullscreenOffIcon)
    }
}
