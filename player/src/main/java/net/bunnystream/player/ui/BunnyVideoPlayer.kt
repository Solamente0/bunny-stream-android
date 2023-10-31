package net.bunnystream.player.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.UnstableApi
import net.bunnystream.player.DefaultBunnyPlayer
import net.bunnystream.player.databinding.ViewBunnyVideoPlayerBinding
import net.bunnystream.player.model.PlayerIconSet
import net.bunnystream.player.ui.fullscreen.FullScreenPlayerActivity
import net.bunnystream.player.ui.widget.BunnyPlayerView

@UnstableApi
class BunnyVideoPlayer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val TAG = "BunnyVideoPlayer"
    }

    private val binding = ViewBunnyVideoPlayerBinding.inflate(LayoutInflater.from(context), this)

    private val playerView by lazy {
        binding.playerView
    }

    var iconSet: PlayerIconSet = PlayerIconSet()
        set(value) {
            field = value
            playerView.iconSet = value
        }

    private val bunnyPlayer = DefaultBunnyPlayer.getInstance(context)

    init {
        playerView.iconSet = iconSet
        playerView.bunnyPlayer = bunnyPlayer
        playerView.fullscreenListener = object : BunnyPlayerView.FullscreenListener {
            override fun onFullscreenToggleClicked() {
                playerView.bunnyPlayer = null
                FullScreenPlayerActivity.show(context, iconSet) {
                    Log.d(TAG, "onFullscreenExited")
                    playerView.bunnyPlayer = bunnyPlayer
                }
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d(TAG, "onAttachedToWindow")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        bunnyPlayer.release()
    }

    fun loadVideo(url: String) {
        bunnyPlayer.loadVideo(url, MimeTypes.VIDEO_MP4)
    }

    fun play() {
        bunnyPlayer.play()
    }
}
