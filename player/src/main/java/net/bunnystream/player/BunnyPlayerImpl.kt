package net.bunnystream.player

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import net.bunnystream.player.common.FullscreenMode
import net.bunnystream.player.common.PlaybackStateListener
import net.bunnystream.player.common.VideoQuality
import net.bunnystream.player.model.BunnyPlayerIconSet
import net.bunnystream.player.ui.BunnyVideoPlayer
import net.bunnystream.player.ui.FullScreenBunnyVideoPlayer

class BunnyPlayerImpl(
    private val context: Context,
    private val parentView: ViewGroup,
    private val iconSet: BunnyPlayerIconSet,
): BunnyPlayer {

    private var exoPlayer: ExoPlayer? = null
    private lateinit var normalPlayer: BunnyVideoPlayer
    private lateinit var fullScreenPlayer: FullScreenBunnyVideoPlayer

    private var currentVolume: Float = 0f

    private var fullscreenStateListener: ((FullscreenMode) -> Unit)? = null

    init {
        initExoPlayer()
        initializeBunnyPlayerView()
        listeners()
    }

    private fun initExoPlayer() {
        if (exoPlayer != null) {
            exoPlayer?.prepare()
        }
        exoPlayer = ExoPlayer.Builder(context).build()
    }

    private fun initializeBunnyPlayerView() {
        normalPlayer = BunnyVideoPlayer(
            context,
            playerManager = this@BunnyPlayerImpl,
            iconSet = iconSet
        ).apply {
            exoPlayer?.let {
                it.prepareFullScreenPlayer(
                    playerView,
                    this@BunnyPlayerImpl,
                    iconSet = iconSet,
                )
                playerView.player = it
            }
        }
        parentView.addView(normalPlayer)
    }

    private fun setVolumeIcon(isMute: Boolean) {
        normalPlayer.isMute = isMute
        fullScreenPlayer.isMute = isMute
    }

    private fun setPlayIcon(isPlaying: Boolean) {
        normalPlayer.isPlaying = isPlaying
        fullScreenPlayer.isPlaying = isPlaying
    }

    private fun listeners() {
        exoPlayer?.addListener(object: Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                setPlayIcon(isPlaying = isPlaying)
            }
        })
    }

    override fun loadVideo(url: String) {
        val item =
            MediaItem.Builder()
                .setUri(url)
                .build()
        exoPlayer?.setMediaItem(item)
        exoPlayer?.prepare()
    }

    override fun release() {
        exoPlayer?.stop()
        exoPlayer?.release()
        exoPlayer = null
    }

    override fun play() {
        exoPlayer?.play()
    }

    override fun pause() {
        exoPlayer?.pause()
    }

    override fun stop() {
        exoPlayer?.stop()
    }

    override fun seekTo(positionMs: Long) {
        exoPlayer?.seekTo(positionMs)
    }

    override fun setVolume(volume: Float) {
        exoPlayer?.volume = volume
    }

    override fun getVolume(): Float = exoPlayer?.volume ?: 0f

    override fun mute() {
        val volume = getVolume()
        if (volume != 0f) {
            currentVolume = volume
        }
        setVolume(0f)
        setVolumeIcon(isMute = true)
    }

    override fun unmute() {
        setVolume(currentVolume)
        setVolumeIcon(isMute = false)
    }

    override fun isPlaying(): Boolean = exoPlayer?.isPlaying ?: false

    override fun getDuration(): Long = exoPlayer?.duration ?: 0L

    override fun getCurrentPosition(): Long = exoPlayer?.currentPosition ?: 0L

    override fun getBufferedPercentage(): Int {
        TODO("Not yet implemented")
    }

    override fun setPlaybackStateListener(listener: PlaybackStateListener) {
        TODO("Not yet implemented")
    }

    override fun getPlaybackState() {
        TODO("Not yet implemented")
    }

    override fun setVideoQuality(videoQuality: VideoQuality) {
        TODO("Not yet implemented")
    }

    override fun getAvailableVideoQualities(): VideoQuality {
        TODO("Not yet implemented")
    }

    override fun setFullscreen(fullscreen: FullscreenMode) {
        when (fullscreen) {
            FullscreenMode.FULLSCREEN -> normalPlayer.fullScreenButton.performClick()
            FullscreenMode.NORMAL -> fullScreenPlayer.fullScreenButton.performClick()
        }
    }

    override fun setAspectRatio(aspectRatio: Float) {
        TODO("Not yet implemented")
    }

    override fun isInFullscreen(listener: (FullscreenMode) -> Unit) {
        fullscreenStateListener = listener
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    @SuppressLint("SourceLockedOrientationActivity")
    fun ExoPlayer.prepareFullScreenPlayer(
        normalPlayerPlayerView: PlayerView,
        playerManager: BunnyPlayer,
        iconSet: BunnyPlayerIconSet,
        forceLandscape: Boolean = false,
    ) {
        (normalPlayerPlayerView.context as Activity).apply {
            fullScreenPlayer = FullScreenBunnyVideoPlayer(this, playerManager = playerManager, iconSet = iconSet)
            val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            fullScreenPlayer.layoutParams = layoutParams
            fullScreenPlayer.visibility = View.GONE
            fullScreenPlayer.setBackgroundColor(Color.BLACK)
            (normalPlayerPlayerView.rootView as ViewGroup).apply { addView(fullScreenPlayer, childCount) }
            val fullScreenButton: ImageView = normalPlayerPlayerView.findViewById(R.id.bunny_fullscreen)
            val normalScreenButton: ImageView = fullScreenPlayer.findViewById(R.id.bunny_fullscreen)
            fullScreenButton.setOnClickListener {
                if (forceLandscape)
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                normalPlayerPlayerView.visibility = View.GONE
                fullScreenPlayer.visibility = View.VISIBLE
                PlayerView.switchTargetView(
                    this@prepareFullScreenPlayer,
                    normalPlayerPlayerView,
                    fullScreenPlayer.playerView,
                )
                fullscreenStateListener?.invoke(FullscreenMode.FULLSCREEN)
            }
            normalScreenButton.setOnClickListener {
                if (forceLandscape || requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                normalPlayerPlayerView.visibility = View.VISIBLE
                fullScreenPlayer.visibility = View.GONE
                PlayerView.switchTargetView(
                    this@prepareFullScreenPlayer,
                    fullScreenPlayer.playerView,
                    normalPlayerPlayerView,
                )
                fullscreenStateListener?.invoke(FullscreenMode.NORMAL)
            }
            normalPlayerPlayerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT
            normalPlayerPlayerView.player = this@prepareFullScreenPlayer
        }
    }

}
