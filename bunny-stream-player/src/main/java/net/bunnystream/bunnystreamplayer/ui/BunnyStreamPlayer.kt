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
import net.bunnystream.api.playback.PlaybackPosition
import net.bunnystream.api.playback.ResumeConfig
import net.bunnystream.api.playback.ResumePositionListener
import net.bunnystream.api.settings.domain.model.PlayerSettings
import net.bunnystream.bunnystreamplayer.DefaultBunnyPlayer
import net.bunnystream.bunnystreamplayer.config.PlaybackSpeedConfig
import net.bunnystream.bunnystreamplayer.model.PlayerIconSet
import net.bunnystream.bunnystreamplayer.model.getSanitizedRetentionData
import net.bunnystream.bunnystreamplayer.ui.fullscreen.FullScreenPlayerActivity
import net.bunnystream.bunnystreamplayer.ui.widget.BunnyPlayerView
import net.bunnystream.player.databinding.ViewBunnyVideoPlayerBinding
import org.openapitools.client.models.VideoModel
import org.openapitools.client.models.VideoPlayDataModelVideo

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

    // Resume position functionality
    private var resumePositionCallback: ((PlaybackPosition, (Boolean) -> Unit) -> Unit)? = null

    private val resumePositionListener = object : ResumePositionListener {
        override fun onResumePositionAvailable(videoId: String, position: PlaybackPosition) {
            Log.d(TAG, "Resume position available: $position")
            resumePositionCallback?.invoke(position) { shouldResume ->
                if (shouldResume) {
                    bunnyPlayer.seekTo(position.position)
                }
            }
        }

        override fun onResumePositionSaved(videoId: String, position: PlaybackPosition) {
            Log.d(TAG, "Resume position saved: $position")
        }
    }

    private val lifecycleObserver = object : DefaultLifecycleObserver {
        override fun onResume(owner: LifecycleOwner) {
            if (bunnyPlayer.autoPaused) {
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

        // Set up resume position listener
        bunnyPlayer.setResumePositionListener(resumePositionListener)

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

    fun setPlaybackSpeedConfig(config: PlaybackSpeedConfig) {
        val defaultPlayer = DefaultBunnyPlayer.getInstance(context)
        defaultPlayer.setPlaybackSpeedConfig(config)
    }

    /**
     * Enable resume position functionality
     */
    fun enableResumePosition(
        config: ResumeConfig = ResumeConfig(),
        onResumePositionCallback: ((PlaybackPosition, (Boolean) -> Unit) -> Unit)? = null
    ) {
        bunnyPlayer.enableResumePosition(config)
        this.resumePositionCallback = onResumePositionCallback
    }

    /**
     * Disable resume position functionality
     */
    fun disableResumePosition() {
        bunnyPlayer.disableResumePosition()
        this.resumePositionCallback = null
    }

    /**
     * Clear saved position for specific video
     */
    fun clearSavedPosition(videoId: String) {
        bunnyPlayer.clearSavedPosition(videoId)
    }

    override fun playVideo(videoId: String, libraryId: Long?) {
        Log.d(TAG, "playVideo videoId=$videoId")

        val providedLibraryId = libraryId ?: BunnyStreamApi.libraryId

        if (!BunnyStreamApi.isInitialized()) {
            Log.e(
                TAG,
                "Unable to play video, initialize the player first using BunnyStreamSdk.initialize"
            )
            return
        }

        loadVideoJob?.cancel()

        pendingJob = {
            scope!!.launch {

                val video: VideoModel

                try {
                    video = withContext(Dispatchers.IO) {
                        BunnyStreamApi.getInstance().videosApi.videoGetVideoPlayData(
                            providedLibraryId,
                            videoId
                        ).video?.toVideoModel()!!
                    }
                    Log.d(TAG, "video=$video")
                } catch (e: Exception) {
                    Log.w(TAG, "Error fetching video: $e")
                    return@launch
                }

                val settings = BunnyStreamApi.getInstance()
                    .fetchPlayerSettings(providedLibraryId, videoId)

                settings.fold(
                    ifLeft = {
                        initializeVideo(
                            video, PlayerSettings(
                                thumbnailUrl = "",
                                controls = "",
                                keyColor = 0,
                                captionsFontSize = 0,
                                captionsFontColor = null,
                                captionsBackgroundColor = null,
                                uiLanguage = "",
                                showHeatmap = false,
                                fontFamily = "",
                                playbackSpeeds = listOf(0.25f, 0.5f, 0.75f, 1.0f, 1.25f, 1.5f, 2.0f, 3.0f, 4.0f),
                                drmEnabled = false,
                                vastTagUrl = null,
                                videoUrl = "",
                                seekPath = "",
                                captionsPath = ""
                            )
                        )
                        playerView.showError(it)
                    },
                    ifRight = { initializeVideo(video, it) }
                )
            }
        }

        if (scope == null) {
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

    private suspend fun initializeVideo(video: VideoModel, playerSettings: PlayerSettings) {
        playerView.showPreviewThumbnail(playerSettings.thumbnailUrl)

        var retentionData: Map<Int, Int> = mutableMapOf()

        if (playerSettings.showHeatmap) {
            try {
                val retentionDataResponse = withContext(Dispatchers.IO) {
                    BunnyStreamApi.getInstance().videosApi.videoGetVideoHeatmap(
                        video.videoLibraryId!!,
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

    fun VideoPlayDataModelVideo.toVideoModel(): VideoModel = VideoModel(
        videoLibraryId        = this.videoLibraryId,
        guid                  = this.guid,
        title                 = this.title,
        dateUploaded          = this.dateUploaded,
        views                 = this.views,
        isPublic              = this.isPublic,
        length                = this.length,
        status                = this.status,
        framerate             = this.framerate,
        rotation              = this.rotation,
        width                 = this.width,
        height                = this.height,
        availableResolutions  = this.availableResolutions,
        outputCodecs          = this.outputCodecs,
        thumbnailCount        = this.thumbnailCount,
        encodeProgress        = this.encodeProgress,
        storageSize           = this.storageSize,
        captions               = this.captions,
        hasMP4Fallback        = this.hasMP4Fallback,
        collectionId          = this.collectionId,
        thumbnailFileName     = this.thumbnailFileName,
        averageWatchTime      = this.averageWatchTime,
        totalWatchTime        = this.totalWatchTime,
        category              = this.category,
        chapters              = this.chapters,
        moments               = this.moments,
        metaTags              = this.metaTags,
        transcodingMessages   = this.transcodingMessages
    )
}