package net.bunnystream.bunnystreamplayer.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.bunnystream.api.BunnyStreamApi
import net.bunnystream.api.settings.domain.model.PlayerSettings
import net.bunnystream.bunnystreamplayer.DefaultBunnyPlayer
import net.bunnystream.bunnystreamplayer.model.PlayerIconSet
import net.bunnystream.bunnystreamplayer.model.getSanitizedRetentionData
import net.bunnystream.bunnystreamplayer.ui.fullscreen.FullScreenPlayerActivity
import net.bunnystream.bunnystreamplayer.ui.widget.BunnyPlayerView
import net.bunnystream.player.R
import net.bunnystream.player.databinding.ViewBunnyVideoPlayerBinding
import org.openapitools.client.models.VideoModel

class BunnyStreamPlayer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr), BunnyPlayer {

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

    override var iconSet: PlayerIconSet = PlayerIconSet()
        set(value) {
            field = value
            playerView.iconSet = value
        }

    private val bunnyPlayer = DefaultBunnyPlayer.getInstance(context)

    private val lifecycleObserver = object : DefaultLifecycleObserver {
        override fun onResume(owner: LifecycleOwner) {
            if(bunnyPlayer.autoPaused) {
                bunnyPlayer.play()
            }
        }

        override fun onPause(owner: LifecycleOwner) {
            val autoPaused = bunnyPlayer.isPlaying()
            bunnyPlayer.pause(autoPaused)
        }
    }

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

                findViewTreeLifecycleOwner()?.lifecycle?.addObserver(lifecycleObserver)
            }

            override fun onViewDetachedFromWindow(view: View) {
                Log.d(TAG, "onViewDetachedFromWindow")
                job?.cancel()
                findViewTreeLifecycleOwner()?.lifecycle?.removeObserver(lifecycleObserver)
            }
        })
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d(TAG, "onAttachedToWindow")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.d(TAG, "onDetachedFromWindow")
        bunnyPlayer.stop()
    }

    override fun playVideo(videoId: String) {
        Log.d(TAG, "playVideo videoId=$videoId")

        if(!BunnyStreamApi.isInitialized()) {
            Log.e(TAG, "Unable to play video, initialize the player first using BunnyStreamSdk.initialize")
            return
        }

        loadVideoJob?.cancel()

        pendingJob = {
            scope!!.launch {

                val video: VideoModel

                try {
                    video = withContext(Dispatchers.IO) {
                        BunnyStreamApi.getInstance().videosApi.videoGetVideo(
                            BunnyStreamApi.libraryId,
                            videoId
                        )
                    }
                    Log.d(TAG, "video=$video")
                } catch (e: Exception) {
                    Log.w(TAG, "Error fetching video: $e")
                    return@launch
                }

                val settings = BunnyStreamApi.getInstance().fetchPlayerSettings(BunnyStreamApi.libraryId, videoId)

                settings.fold(
                    ifLeft = {
                        initializeVideo(video, PlayerSettings(
                            thumbnailUrl = "",
                            controls = "",
                            keyColor = 0,
                            captionsFontSize = 0,
                            captionsFontColor = null,
                            captionsBackgroundColor = null,
                            uiLanguage = "",
                            showHeatmap = false,
                            fontFamily = "",
                            playbackSpeeds = emptyList(),
                            drmEnabled = false,
                            vastTagUrl = null,
                            videoUrl = "",
                            seekPath = "",
                            captionsPath = ""
                        ))
                        playerView.showError(it)
                             },
                    ifRight = { initializeVideo(video, it) }
                )
            }
        }

        if(scope == null) {
            Log.d(TAG, "scope not created yet")
            return
        }

        loadVideoJob = pendingJob?.invoke()
        pendingJob = null
    }

    override fun pause() {
        bunnyPlayer.pause()
    }

    override fun play() {
        bunnyPlayer.play()
    }

    private suspend fun initializeVideo(video: VideoModel, playerSettings: PlayerSettings){
        if(playerSettings.drmEnabled){
            Log.e(TAG, "DRM is enabled for this video, " +
                    "BunnyStreamPlayer does not support DRM protected content.")
            playerView.showError(context.getString(R.string.label_drm_not_supported_exception))
            return
        }
        playerView.showPreviewThumbnail(playerSettings.thumbnailUrl)

        var retentionData: Map<Int, Int> = mutableMapOf()

        if(playerSettings.showHeatmap) {
            try {
                val retentionDataResponse = withContext(Dispatchers.IO) {
                    BunnyStreamApi.getInstance().videosApi.videoGetVideoHeatmap(
                        BunnyStreamApi.libraryId,
                        video.guid!!
                    )
                }
                retentionData = retentionDataResponse.getSanitizedRetentionData()
            } catch (e: Exception) {
                Log.w(TAG, "Error fetching video heatmap")
            }
        }

        bunnyPlayer.playVideo(binding.playerView, video, retentionData, playerSettings)
        playerView.bunnyPlayer = bunnyPlayer
    }
}
