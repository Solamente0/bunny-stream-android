package net.bunnystream.player.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import net.bunnystream.player.R
import net.bunnystream.player.databinding.ViewBunnyVideoPlayerBinding

class BunnyVideoPlayer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr),
    DefaultLifecycleObserver {

    private val binding = ViewBunnyVideoPlayerBinding.inflate(LayoutInflater.from(context), this, true)

    private var exoPlayer: ExoPlayer? = null
    private var currentVolume: Float = 0f

    val playButton by lazy {
        binding.playerView.findViewById<ImageButton>(R.id.bunny_play_pause)
    }
    val playPauseButton by lazy {
        binding.playerView.findViewById<ImageButton>(androidx.media3.ui.R.id.exo_play_pause)
    }
    val rewindButton by lazy {
        binding.playerView.findViewById<ImageButton>(androidx.media3.ui.R.id.exo_rew_with_amount)
    }
    val forwardButton by lazy {
        binding.playerView.findViewById<ImageButton>(androidx.media3.ui.R.id.exo_ffwd_with_amount)
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
        binding.playerView.findViewById<ImageButton>(androidx.media3.ui.R.id.exo_fullscreen)
    }

    init {
        initializePlayer()
    }

    fun loadVideo(url: String) {
        val item =
            MediaItem.Builder()
                .setUri(url)
                .build()
        exoPlayer?.setMediaItem(item)
        exoPlayer?.prepare()
    }

    private fun initializePlayer() {
        if (exoPlayer?.isCommandAvailable(Player.COMMAND_PREPARE) == true) {
            exoPlayer?.prepare()
            return
        }
        exoPlayer = ExoPlayer.Builder(context).build()
        binding.playerView.player = exoPlayer
        playerListener()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        initializePlayer()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        stopPlayer()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        initializePlayer()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopPlayer()
    }

    private fun stopPlayer() {
        exoPlayer?.stop()
        exoPlayer?.release()
        exoPlayer = null
    }

    fun play() {
        exoPlayer?.play()
    }

    fun pause() {
        exoPlayer?.pause()
    }

    fun stop() {
        exoPlayer?.stop()
    }

    fun release() {
        exoPlayer?.release()
    }

    fun seekTo(positionMs: Long) {
        exoPlayer?.seekTo(positionMs)
    }

    fun setVolume(volumeLevel: Float) {
        exoPlayer?.volume = volumeLevel
    }

    fun getVolume() = exoPlayer?.volume ?: 0f

    fun mute() {
        volumeButton.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_sound_off))
        val volume = getVolume()
        if (volume != 0f) {
            currentVolume = volume
        }
        setVolume(0f)
    }

    fun unmute() {
        volumeButton.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_sound_on))
        setVolume(currentVolume)
    }

    fun isPlaying() = exoPlayer?.isPlaying ?: false

    fun getDuration() = exoPlayer?.duration ?: 0L

    fun getCurrentPosition() = exoPlayer?.currentPosition ?: 0L

    private fun playerListener() {
        playButton.setOnClickListener {
            playPauseButton.performClick()
        }
        volumeButton.setOnClickListener {
            if (getVolume() == 0f) {
                unmute()
            } else {
                mute()
            }
        }
        exoPlayer?.addListener(object: Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)

                playButton.setImageDrawable(AppCompatResources.getDrawable(context,
                    if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
                ))
            }
        })
    }
}
