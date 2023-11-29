package net.bunnystream.player.ui.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.media3.common.Player
import androidx.media3.ui.CaptionStyleCompat
import androidx.media3.ui.CaptionStyleCompat.EDGE_TYPE_NONE
import androidx.media3.ui.PlayerControlView
import androidx.media3.ui.PlayerView
import androidx.media3.ui.PlayerView.ControllerVisibilityListener
import androidx.media3.ui.SubtitleView
import androidx.media3.ui.TimeBar
import androidx.mediarouter.app.MediaRouteButton
import com.google.android.gms.cast.framework.CastButtonFactory
import net.bunnystream.androidsdk.settings.domain.model.PlayerSettings
import net.bunnystream.player.PlayerStateListener
import net.bunnystream.player.PlayerType
import net.bunnystream.player.R
import net.bunnystream.player.common.BunnyPlayer
import net.bunnystream.player.model.Chapter
import net.bunnystream.player.model.Moment
import net.bunnystream.player.model.PlayerIconSet
import net.bunnystream.player.model.RetentionGraphEntry
import net.bunnystream.player.model.SubtitleInfo
import net.bunnystream.player.model.VideoQuality
import kotlin.time.Duration.Companion.seconds

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

        override fun onChaptersUpdated(chapters: List<Chapter>) {
            Log.d(TAG, "onChaptersUpdated: $chapters")
            timeBar?.chapters = chapters
        }

        override fun onMomentsUpdated(moments: List<Moment>) {
            Log.d(TAG, "onMomentsUpdated: $moments")
            timeBar?.moments = moments
        }

        override fun onRetentionGraphUpdated(points: List<RetentionGraphEntry>) {
            timeBar?.retentionGraphData = points
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
            initTimeBar()

            bunnyPlayer?.seekThumbnail?.let {
                previewLoader = PreviewLoader(context, it)
            }
        }

    var iconSet: PlayerIconSet = PlayerIconSet()
        set(value) {
            field = value
            applyStyle()
        }

    var playerSettings: PlayerSettings? = null
        set(value) {
            field = value
            applyStyle()
        }

    private var previewLoader: PreviewLoader? = null

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
        findViewById<ImageButton>(R.id.bunny_settings)
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
        findViewById<BunnyTimeBar>(R.id.exo_progress)
    }

    private val thumbnailPreview by lazy {
        findViewById<BunnyTimeBarPreview>(R.id.youtubeTimeBarPreview)
    }

    private val subtitles by lazy {
        findViewById<SubtitleView>(R.id.exo_subtitles)
    }

    private val subtitle by lazy {
        findViewById<ToggleableImageButton>(R.id.bunny_subtitle)
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

        subtitle.setOnClickListener { view ->
            bunnyPlayer?.let {
                it.setSubtitlesEnabled(!it.areSubtitlesEnabled())
            }
            subtitle.state = if(bunnyPlayer?.areSubtitlesEnabled() == true) {
                ToggleableImageButton.State.STATE_TOGGLED
            } else {
                ToggleableImageButton.State.STATE_DEFAULT
            }
        }

        settingsButton.setOnClickListener {
            val popupMenu = PopupMenu(context, it)
            popupMenu.inflate(R.menu.video_settings)

            val subtitlesConfig = bunnyPlayer?.getSubtitles()

            val subtitleMenuId = View.generateViewId()
            val subtitleMenuIds = mutableMapOf<Int, SubtitleInfo>()

            val speed = bunnyPlayer?.getSpeed()

            if(subtitlesConfig != null && subtitlesConfig.subtitles.isNotEmpty()) {
                val subtitlesMenu = popupMenu.menu.addSubMenu(
                    Menu.NONE,
                    subtitleMenuId,
                    Menu.NONE,
                    context.getString(R.string.label_video_settings_captions)
                )

                subtitlesConfig.subtitles.forEach { info ->
                    val id = View.generateViewId()
                    subtitleMenuIds[id] = info
                    val item = subtitlesMenu.add(
                        Menu.NONE,
                        id,
                        Menu.NONE,
                        "${info.title} (${info.language})"
                    )
                    item.isCheckable = true
                    item.isChecked = subtitlesConfig.selectedSubtitle == info
                }
            }

            val videoQualityOptions = bunnyPlayer?.getVideoQualityOptions()

            val qualityMenuId = View.generateViewId()
            val qualityMenuIds = mutableMapOf<Int, VideoQuality>()

            if(videoQualityOptions != null) {
                val qualityOptionsMenu = popupMenu.menu.addSubMenu(
                    Menu.NONE,
                    qualityMenuId,
                    Menu.NONE,
                    context.getString(R.string.label_video_settings_quality)
                )

                videoQualityOptions.options.forEach { option ->
                    val id = generateViewId()
                    qualityMenuIds[id] = option

                    val title = if(option.width == Int.MAX_VALUE) {
                        context.getString(R.string.label_video_quality_auto)
                    } else {
                        "${option.width} x ${option.height}"
                    }

                    val item = qualityOptionsMenu.add(
                        Menu.NONE,
                        id,
                        Menu.NONE,
                        title
                    )
                    item.isCheckable = true
                    item.isChecked = videoQualityOptions.selectedOption == option
                }
            }

            val speedMenuItemId = when (speed) {
                0.5F -> R.id.video_speed_0_5
                0.75F -> R.id.video_speed_0_75
                1F -> R.id.video_speed_normal
                1.25F -> R.id.video_speed_1_25
                1.5F -> R.id.video_speed_1_5
                2F -> R.id.video_speed_2
                4F -> R.id.video_speed_4
                else -> R.id.video_speed_normal
            }

            popupMenu.menu.findItem(speedMenuItemId)?.isChecked = true

            popupMenu.setOnMenuItemClickListener { item ->
                Log.d(TAG, "setOnMenuItemClickListener")

                val subtitleOption = subtitleMenuIds[item.itemId]

                if(subtitleOption != null) {
                    bunnyPlayer?.selectSubtitle(subtitleOption)
                    subtitle.state = ToggleableImageButton.State.STATE_TOGGLED
                    controllerShowTimeoutMs = 2.seconds.inWholeMilliseconds.toInt()
                    return@setOnMenuItemClickListener true
                }

                val qualityOption = qualityMenuIds[item.itemId]

                if(qualityOption != null) {
                    bunnyPlayer?.selectQuality(qualityOption)
                    controllerShowTimeoutMs = 2.seconds.inWholeMilliseconds.toInt()
                    return@setOnMenuItemClickListener true
                }

                when(item.itemId) {
                    R.id.video_speed_0_5 -> bunnyPlayer?.setSpeed(0.5F)
                    R.id.video_speed_0_75 -> bunnyPlayer?.setSpeed(0.75F)
                    R.id.video_speed_normal -> bunnyPlayer?.setSpeed(1F)
                    R.id.video_speed_1_25 -> bunnyPlayer?.setSpeed(1.25F)
                    R.id.video_speed_1_5 -> bunnyPlayer?.setSpeed(1.5F)
                    R.id.video_speed_2 -> bunnyPlayer?.setSpeed(2F)
                    R.id.video_speed_4 -> bunnyPlayer?.setSpeed(4F)
                }

                true
            }

            controllerHideOnTouch = true
            controllerShowTimeoutMs = -1

            popupMenu.show()
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

        subtitle.isVisible = bunnyPlayer?.getSubtitles()?.subtitles?.isNotEmpty() == true

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
        replyButton.setImageResource(iconSet.rewindIcon)
        forwardButton.setImageResource(iconSet.forwardIcon)
        settingsButton.setImageResource(iconSet.settingsIcon)
        muteButton.setStateIcons(iconSet.volumeOnIcon, iconSet.volumeOffIcon)

        val fullScreenIcon = if(isFullscreen) {
            iconSet.fullscreenOffIcon
        } else {
            iconSet.fullscreenOnIcon
        }

        fullScreenButton.setImageResource(fullScreenIcon)

        playerSettings?.let {
            timeBar.tintColor = it.keyColor

            val style = CaptionStyleCompat(
                /* foregroundColor = */ it.captionsFontColor ?: Color.WHITE,
                /* backgroundColor = */ it.captionsBackgroundColor ?: Color.BLACK,
                /* windowColor = */ Color.TRANSPARENT,
                /* edgeType = */ EDGE_TYPE_NONE,
                /* edgeColor = */ Color.WHITE,
                /* typeface = */ null
            )

            subtitles.setStyle(style)

            // "play-large,play,progress,current-time,mute,volume,captions,settings,airplay,pip,fullscreen"
        } ?: kotlin.run {
            timeBar.tintColor = Color.WHITE
            subtitles.setStyle(CaptionStyleCompat.DEFAULT)
        }

        invalidate()
    }

    private fun initTimeBar() {
        timeBar.timeBarPreview(thumbnailPreview)

        thumbnailPreview.previewListener(object : BunnyTimeBarPreview.PreviewListener {
            override fun loadThumbnail(imageView: ImageView, position: Long) {
                previewLoader?.loadPreview(position, imageView)
            }
        })

        timeBar.addListener(object : TimeBar.OnScrubListener {
            override fun onScrubStart(timeBar: TimeBar, position: Long) {
                showController()
                controllerShowTimeoutMs = 120.seconds.inWholeMilliseconds.toInt()
            }

            override fun onScrubMove(timeBar: TimeBar, position: Long) {
                /* no-op, seek will be done in onScrubStop */
            }

            override fun onScrubStop(timeBar: TimeBar, position: Long, canceled: Boolean) {
                player?.seekTo(position)
                timeBar.setPosition(position)
                controllerShowTimeoutMs = 2.seconds.inWholeMilliseconds.toInt()
            }
        })

        setControllerVisibilityListener(ControllerVisibilityListener {
            if (it == View.VISIBLE) {
                timeBar.showScrubber()
            } else {
                timeBar.hideScrubber()
            }
        })
    }
}