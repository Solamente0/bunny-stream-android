package net.bunnystream.player.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.media3.ui.DefaultTimeBar
import net.bunnystream.player.DefaultBunnyPlayer
import net.bunnystream.player.R
import net.bunnystream.player.databinding.ViewBunnyVideoPlayerBinding
import net.bunnystream.player.model.BunnyPlayerIconSet

@SuppressLint("ViewConstructor")
class BunnyVideoPlayer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val playerManager: DefaultBunnyPlayer,
    private val iconSet: BunnyPlayerIconSet,
    private val colorTheme: String,
    private val font: Int,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewBunnyVideoPlayerBinding.inflate(LayoutInflater.from(context), this, true)

    val playerView by lazy {
        binding.playerView
    }
    private val buffer by lazy {
        binding.playerView.findViewById<ProgressBar>(androidx.media3.ui.R.id.exo_buffering)
    }
    val timePosition by lazy {
        binding.playerView.findViewById<TextView>(androidx.media3.ui.R.id.exo_position)
    }
    val timeDurationContainer by lazy {
        binding.playerView.findViewById<LinearLayout>(R.id.bunny_duration_container)
    }
    val timeDivider by lazy {
        binding.playerView.findViewById<TextView>(R.id.bunny_duration_divider)
    }
    val timeDuration by lazy {
        binding.playerView.findViewById<TextView>(androidx.media3.ui.R.id.exo_duration)
    }
    val progress by lazy {
        binding.playerView.findViewById<DefaultTimeBar>(androidx.media3.ui.R.id.exo_progress)
    }
    val playButton by lazy {
        binding.playerView.findViewById<ImageButton>(R.id.bunny_play_pause)
    }
    val rewindButton by lazy {
        binding.playerView.findViewById<ImageButton>(androidx.media3.ui.R.id.exo_rew)
    }
    val forwardButton by lazy {
        binding.playerView.findViewById<ImageButton>(androidx.media3.ui.R.id.exo_ffwd)
    }
    val settingsButton by lazy {
        binding.playerView.findViewById<ImageButton>(androidx.media3.ui.R.id.exo_settings)
    }
    val volumeButton by lazy {
        binding.playerView.findViewById<ImageButton>(R.id.bunny_volume)
    }
    val streamingButton by lazy {
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
        setColorTheme()
        setIconResources()
        setFontResources()
        playerListener()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (newConfig?.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fullScreenButton.performClick()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        playerManager.release()
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
        rewindButton.setImageResource(iconSet.rewindIcon)
        forwardButton.setImageResource(iconSet.forwardIcon)
        settingsButton.setImageResource(iconSet.settingsIcon)
        volumeButton.setImageResource(if (playerManager.getVolume() == 0f) iconSet.volumeOffIcon else iconSet.volumeOnIcon)
        streamingButton.setImageResource(iconSet.streamingIcon)
        fullScreenButton.setImageResource(iconSet.fullscreenOnIcon)
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun setColorTheme() {
        val color = Color.parseColor(colorTheme)
        buffer.apply {
            val colorStateList = ColorStateList.valueOf(color)
            progressTintList = colorStateList
            secondaryProgressTintList = colorStateList
            indeterminateTintList = colorStateList
        }
        timePosition.setTextColor(color)
        timeDivider.setTextColor(color)
        timeDuration.setTextColor(color)
        progress.apply {
            setBufferedColor(color)
            setScrubberColor(color)
            setPlayedColor(color)
        }
        playButton.setColorFilter(color)
        rewindButton.setColorFilter(color)
        forwardButton.setColorFilter(color)
        settingsButton.setColorFilter(color)
        volumeButton.setColorFilter(color)
        streamingButton.setColorFilter(color)
        fullScreenButton.setColorFilter(color)
    }

    private fun setFontResources() {
        val typeface = ResourcesCompat.getFont(context, font)
        timePosition.typeface = typeface
        timeDivider.typeface = typeface
        timeDuration.typeface = typeface
    }
}
