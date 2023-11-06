package net.bunnystream.player.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.bunnystream.androidsdk.BunnyStreamSdk
import net.bunnystream.player.DefaultBunnyPlayer
import net.bunnystream.player.databinding.ViewBunnyVideoPlayerBinding
import net.bunnystream.player.model.PlayerIconSet
import net.bunnystream.player.ui.fullscreen.FullScreenPlayerActivity
import net.bunnystream.player.ui.widget.BunnyPlayerView

class BunnyVideoPlayer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val TAG = "BunnyVideoPlayer"
    }

    private var job: Job? = null
    private var scope: CoroutineScope? = null
    private var loadVideoJob: Job? = null
    private var pendingJob: (() -> Job)? = null

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
        playerView.fullscreenListener = object : BunnyPlayerView.FullscreenListener {
            override fun onFullscreenToggleClicked() {
                playerView.bunnyPlayer = null
                FullScreenPlayerActivity.show(context, iconSet) {
                    Log.d(TAG, "onFullscreenExited")
                    playerView.bunnyPlayer = bunnyPlayer
                }
            }
        }

        addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(view: View) {
                Log.d(TAG, "onViewAttachedToWindow")
                job = SupervisorJob()
                scope = CoroutineScope(Dispatchers.Main + job!!)

                pendingJob?.let {
                    Log.d(TAG, "there is pending job, executing...")
                    pendingJob?.invoke()
                    pendingJob = null
                }
            }

            override fun onViewDetachedFromWindow(view: View) {
                Log.d(TAG, "onViewDetachedFromWindow")
                job?.cancel()
            }
        })
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d(TAG, "onAttachedToWindow")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        bunnyPlayer.release()
    }

    fun playVideo(libraryId: Long, videoId: String) {
        Log.d(TAG, "playVideo libraryId=$libraryId videoId=$videoId")

        if(!BunnyStreamSdk.isInitialized()) {
            Log.e(TAG, "Unable to play video, initialize the player first using BunnyStreamSdk.initialize")
            return
        }

        loadVideoJob?.cancel()

        pendingJob = {
            scope!!.launch {
                try {
                    val video = withContext(Dispatchers.IO) {
                        BunnyStreamSdk.getInstance().videosApi.videoGetVideo(libraryId, videoId)
                    }
                    Log.d(TAG, "video=$video")
                    bunnyPlayer.playVideo(binding.playerView, libraryId, video)
                    playerView.bunnyPlayer = bunnyPlayer
                } catch (e: Exception) {
                    Log.e(TAG, "Unable to fetch video: ${e.message}")
                    e.printStackTrace()
                }
            }
        }

        if(scope == null) {
            Log.d(TAG, "scope not created yet")
            return
        }

        loadVideoJob = pendingJob?.invoke()
        pendingJob = null
    }
}
