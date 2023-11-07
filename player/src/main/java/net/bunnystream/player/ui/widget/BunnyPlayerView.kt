package net.bunnystream.player.ui.widget

import android.content.Context
import android.content.res.ColorStateList
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
import androidx.core.graphics.ColorUtils
import androidx.core.view.isVisible
import androidx.media3.common.Player
import androidx.media3.ui.CaptionStyleCompat
import androidx.media3.ui.CaptionStyleCompat.EDGE_TYPE_NONE
import androidx.media3.ui.DefaultTimeBar
import androidx.media3.ui.PlayerControlView
import androidx.media3.ui.PlayerView
import androidx.media3.ui.SubtitleView
import androidx.mediarouter.app.MediaRouteButton
import com.github.rubensousa.previewseekbar.PreviewBar
import com.github.rubensousa.previewseekbar.animator.PreviewFadeAnimator
import com.github.rubensousa.previewseekbar.media3.PreviewTimeBar
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
            timeBarPreview()
        }

    var iconSet: PlayerIconSet = PlayerIconSet()
        set(value) {
            field = value
            applyStyle()
        }

    private var currentFramePositionGlobal = 0L

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

    val timeBar by lazy {
        findViewById<PreviewTimeBar>(R.id.exo_progress)
    }

    val previewImageView by lazy {
        findViewById<ImageView>(R.id.imageView)
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
                val subtitleOption = subtitleMenuIds[item.itemId]

                if(subtitleOption != null) {
                    bunnyPlayer?.selectSubtitle(subtitleOption)
                    subtitle.state = ToggleableImageButton.State.STATE_TOGGLED
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

    private fun timeBarPreview() {
        val seekThumbnail = bunnyPlayer?.seekThumbnail ?: return
        Log.d(TAG, "timeBarPreview seekThumbnail=$seekThumbnail")

        timeBar.isPreviewEnabled = true
        timeBar.setAutoHidePreview(true)
        timeBar.setPreviewAnimationEnabled(true)
        timeBar.setPreviewAnimator(PreviewFadeAnimator())

        timeBar.addOnScrubListener(object : PreviewBar.OnScrubListener {
            override fun onScrubStart(previewBar: PreviewBar) {
                player?.playWhenReady = false
            }

            override fun onScrubStop(previewBar: PreviewBar) {
                player?.playWhenReady = true
            }

            override fun onScrubMove(previewBar: PreviewBar, progress: Int, fromUser: Boolean) = Unit
        })

        val previewLoader = PreviewLoader(context, previewImageView, seekThumbnail)
        timeBar.setPreviewLoader { currentPosition: Long, max: Long ->
            if (currentFramePositionGlobal != currentPosition) {
                currentFramePositionGlobal = currentPosition
                previewLoader.loadPreview(currentPosition)
            }
        }
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

        val style = CaptionStyleCompat(
            Color.RED, Color.WHITE, Color.GREEN, EDGE_TYPE_NONE, Color.CYAN, null
        )

        // subtitles.setStyle(style)
    }
}