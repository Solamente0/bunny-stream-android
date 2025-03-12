package net.bunnystream.player

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.media3.cast.CastPlayer
import androidx.media3.cast.SessionAvailabilityListener
import androidx.media3.common.C
import androidx.media3.common.Format
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.PlaybackException
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.HttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.ima.ImaAdsLoader
import androidx.media3.exoplayer.ima.ImaServerSideAdInsertionMediaSource
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.trackselection.AdaptiveTrackSelection
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.ui.PlayerView
import com.google.android.gms.cast.framework.CastState
import net.bunnystream.androidsdk.BunnyStreamSdk
import net.bunnystream.androidsdk.settings.domain.model.PlayerSettings
import net.bunnystream.androidsdk.settings.toUri
import net.bunnystream.player.common.BunnyPlayer
import net.bunnystream.player.context.AppCastContext
import net.bunnystream.player.model.Chapter
import net.bunnystream.player.model.Moment
import net.bunnystream.player.model.RetentionGraphEntry
import net.bunnystream.player.model.SeekThumbnail
import net.bunnystream.player.model.SubtitleInfo
import net.bunnystream.player.model.Subtitles
import net.bunnystream.player.model.VideoQuality
import net.bunnystream.player.model.VideoQualityOptions
import org.openapitools.client.models.VideoModel
import kotlin.math.ceil
import kotlin.math.round
import kotlin.time.Duration.Companion.seconds

@SuppressLint("UnsafeOptInUsageError")
class DefaultBunnyPlayer private constructor(private val context: Context) : BunnyPlayer {

    companion object {
        private const val TAG = "DefaultBunnyPlayer"

        private const val SEEK_SKIP_MILLIS = 10 * 1000
        private const val THUMBNAILS_PER_IMAGE = 36

        @Volatile
        private var instance: BunnyPlayer? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: DefaultBunnyPlayer(context.applicationContext).also { instance = it }
            }
    }

    private var localPlayer: Player? = null
    private val castContext = AppCastContext.get()
    private var castPlayer: Player? = null
    override var currentPlayer: Player? = null

    private var currentVideo: VideoModel? = null
    private var selectedSubtitle: SubtitleInfo? = null
    private var subtitlesEnabled = false

    override var autoPaused = false

    private var chapters = listOf<Chapter>()
        set(value) {
            field = value
            playerStateListener?.onChaptersUpdated(chapters)
        }

    private var moments = listOf<Moment>()
        set(value) {
            field = value
            playerStateListener?.onMomentsUpdated(moments)
        }

    private var retentionData = listOf<RetentionGraphEntry>()
        set(value) {
            field = value
            playerStateListener?.onRetentionGraphUpdated(retentionData)
        }

    override var playerStateListener: PlayerStateListener? = null
        set(value) {
            field = value
            playerStateListener?.onPlayingChanged(isPlaying())
            playerStateListener?.onMutedChanged(isMuted())
            playerStateListener?.onChaptersUpdated(chapters)
            playerStateListener?.onMomentsUpdated(moments)
            playerStateListener?.onRetentionGraphUpdated(retentionData)
        }

    private var mediaItem: MediaItem? = null
    private var mediaItemBuilder: MediaItem.Builder? = null

    private var trackSelector: DefaultTrackSelector? = null

    private val httpDataSourceFactory: HttpDataSource.Factory =
        DefaultHttpDataSource.Factory().setAllowCrossProtocolRedirects(true)

    private val dataSourceFactory: DataSource.Factory = DataSource.Factory {
        val dataSource: HttpDataSource = httpDataSourceFactory.createDataSource()
        // Needed if "Block Direct Url File Access" is enabled on Dashboard
        dataSource.setRequestProperty("Referer", "https://iframe.mediadelivery.net/")
        dataSource
    }

    private val drmConfig = MediaItem.DrmConfiguration.Builder(C.WIDEVINE_UUID)

    private var serverSideAdLoader: ImaServerSideAdInsertionMediaSource.AdsLoader? = null

    private val playerListener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            Log.d(TAG, "onIsPlayingChanged: $isPlaying")
            playerStateListener?.onPlayingChanged(isPlaying)
        }

        override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
            Log.d(TAG, "onPlaybackParametersChanged speed: ${playbackParameters.speed}")
            playerStateListener?.onPlaybackSpeedChanged(playbackParameters.speed)
        }

        override fun onIsLoadingChanged(isLoading: Boolean) {
            Log.d(TAG, "onIsLoadingChanged isLoading: $isLoading")
            playerStateListener?.onLoadingChanged(isLoading)
        }

        override fun onTracksChanged(tracks: Tracks) {
            super.onTracksChanged(tracks)
            Log.d(TAG, "onTracksChanged tracks: $tracks")
        }

        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)
            Log.d(TAG, "onPlayerError error=$error")
            playerStateListener?.onPlayerError("${error.errorCodeName}: ${error.message}")
        }
    }

    override var seekThumbnail: SeekThumbnail? = null

    override var playerSettings: PlayerSettings? = null

    init {
        castPlayer = CastPlayer(castContext).also {
            it.addListener(playerListener)
            it.setSessionAvailabilityListener(object : SessionAvailabilityListener {
                override fun onCastSessionAvailable() {
                    Log.d(TAG, "onCastSessionAvailable")
                    switchCurrentPlayer(it)
                }

                override fun onCastSessionUnavailable() {
                    Log.d(TAG, "onCastSessionUnavailable")
                    switchCurrentPlayer(localPlayer!!)
                }
            })
        }

        castContext.addCastStateListener {
            Log.d(TAG, "onCastStateChanged: $it")
            when(it) {
                CastState.CONNECTED -> {}
                CastState.CONNECTING -> {}
                CastState.NOT_CONNECTED -> {}
                CastState.NO_DEVICES_AVAILABLE -> {}
            }
        }
    }

    override fun playVideo(
        playerView: PlayerView,
        video: VideoModel,
        retentionData: Map<Int, Int>,
        playerSettings: PlayerSettings
    ) {
        Log.d(TAG, "loadVideo video=$video retentionData=$retentionData playerSettings=$playerSettings")

        this.playerSettings = playerSettings
        currentVideo = video

        val imaLoader = ImaAdsLoader.Builder(context).build()

        val mediaSourceFactory = DefaultMediaSourceFactory(context)
            .setDataSourceFactory(dataSourceFactory)
            .setLocalAdInsertionComponents({ imaLoader }, playerView)

        trackSelector = DefaultTrackSelector(context, AdaptiveTrackSelection.Factory())

        localPlayer = ExoPlayer.Builder(context)
            .setTrackSelector(trackSelector!!)
            .setMediaSourceFactory(mediaSourceFactory)
            .build().also {
                it.addListener(playerListener)
            }

        currentPlayer = localPlayer
        imaLoader.setPlayer(currentPlayer)
        serverSideAdLoader?.setPlayer(currentPlayer!!)

        val drmLicenseUri = "${BunnyStreamSdk.baseApi}/WidevineLicense/${BunnyStreamSdk.libraryId}/${video.guid}?contentId=${video.guid}"

        mediaItemBuilder = MediaItem.Builder()
            .setUri(playerSettings.videoUrl)
            .setMimeType(MimeTypes.APPLICATION_M3U8)

        if(playerSettings.drmEnabled) {
            mediaItemBuilder!!.setDrmConfiguration(drmConfig.setLicenseUri(drmLicenseUri).build())
        }

        val vastTagUri = playerSettings.vastTagUrl.toUri()

        if(vastTagUri != null) {
            mediaItemBuilder!!.setAdsConfiguration(MediaItem.AdsConfiguration.Builder(vastTagUri).build())
        }

        val subtitles = video.captions?.map {
            val subUri = Uri.parse("${playerSettings.captionsPath}${it.srclang}.vtt?ver=1")

            MediaItem.SubtitleConfiguration.Builder(subUri)
                .setMimeType(MimeTypes.TEXT_VTT)
                .setLanguage(it.srclang)
                .setSelectionFlags(C.SELECTION_FLAG_DEFAULT)
                .build()
        } ?: listOf()

        mediaItemBuilder!!.setSubtitleConfigurations(subtitles)

        mediaItem = mediaItemBuilder!!.build()
        currentPlayer?.setMediaItem(mediaItem!!)

        selectSubtitleTrack(null)

        currentPlayer?.playWhenReady = true
        currentPlayer?.prepare()

        initSeekThumbnailPreview(video, playerSettings.seekPath)

        moments = video.moments?.map {
            Moment(it.label, it.timestamp?.seconds?.inWholeMilliseconds ?: 0)
        } ?: listOf()

        chapters = video.chapters?.map {
            Chapter(
                it.start?.seconds?.inWholeMilliseconds ?: 0,
                it.end?.seconds?.inWholeMilliseconds ?: 0,
                it.title
            )
        } ?: listOf()

        if(playerSettings.showHeatmap) {
            this.retentionData = retentionData.map {
                RetentionGraphEntry(it.key, it.value)
            }
        }
    }

    override fun skipForward() {
        currentPlayer?.let {
            it.seekTo(it.currentPosition + SEEK_SKIP_MILLIS)
        }
    }

    override fun replay() {
        currentPlayer?.let {
            val current = it.currentPosition
            val target = if(current > SEEK_SKIP_MILLIS) {
                current - SEEK_SKIP_MILLIS
            } else {
                0
            }
            it.seekTo(target)
        }
    }

    private fun initSeekThumbnailPreview(video: VideoModel, seekPath: String) {
        val thumbnailPreviewsList: MutableList<String> = mutableListOf()
        val numberOfPreviews = round((video.thumbnailCount?.toFloat() ?: 0.0F) / THUMBNAILS_PER_IMAGE).toInt()
        var i = 0
        do {
            thumbnailPreviewsList.add("$seekPath/_${i}.jpg")
            i++
        } while (i < numberOfPreviews)

        seekThumbnail = SeekThumbnail(
            seekThumbnailUrls = thumbnailPreviewsList,
            frameDurationPerThumbnail = ceil((((video.length?.toFloat()) ?: 0.0F) * 1000) / (video.thumbnailCount ?: 1)).toInt(),
            totalThumbnailCount = video.thumbnailCount ?: 0,
            thumbnailsPerImage = THUMBNAILS_PER_IMAGE,
        )
    }

    override fun setSpeed(speed: Float) {
        currentPlayer?.setPlaybackSpeed(speed)
    }

    override fun getSpeed(): Float {
        return currentPlayer?.playbackParameters?.speed ?: 1F
    }

    override fun getSubtitles(): Subtitles {
        return Subtitles(
            currentVideo?.captions?.map {
                SubtitleInfo(it.label!!, it.srclang!!)
            } ?: listOf(),
            if(subtitlesEnabled) {
                selectedSubtitle
            } else {
                null
            }
        )
    }

    override fun selectSubtitle(subtitleInfo: SubtitleInfo) {
        Log.d(TAG, "selectSubtitle: $subtitleInfo")
        subtitlesEnabled = subtitleInfo.language != ""

        val lang: String?
        if(subtitlesEnabled){
            selectedSubtitle = subtitleInfo
            lang = subtitleInfo.language
        } else {
            selectedSubtitle = null
            lang = null
        }

        selectSubtitleTrack(lang)
    }

    override fun setSubtitlesEnabled(enabled: Boolean) {
        subtitlesEnabled = enabled

        if(enabled) {
            if(selectedSubtitle != null) {
                selectSubtitle(selectedSubtitle!!)
            } else {
                val caption = currentVideo?.captions?.getOrNull(0)
                if (caption != null) {
                    selectedSubtitle = SubtitleInfo(caption.label!!, caption.srclang!!)
                    selectSubtitle(selectedSubtitle!!)
                }
            }
        } else {
            selectSubtitleTrack(null)
        }
    }

    override fun areSubtitlesEnabled(): Boolean {
        return subtitlesEnabled
    }

    override fun getVideoQualityOptions(): VideoQualityOptions? {
        return getAvailableVideoQualityOptions()
    }

    override fun selectQuality(quality: VideoQuality) {
        Log.d(TAG, "selectQuality: $quality")
        trackSelector?.let {
            val params = it.buildUponParameters().setMaxVideoSize(quality.width, quality.height)
            it.setParameters(params)
        }
    }

    override fun getPlaybackSpeeds(): List<Float> {
        return playerSettings?.playbackSpeeds ?: listOf()
    }

    override fun release() {
        currentPlayer?.stop()

        localPlayer?.release()
        localPlayer = null

        castPlayer?.release()
        castPlayer = null

        instance = null
    }

    override fun play() {
        val current = currentPlayer?.currentPosition ?: 0
        val duration = currentPlayer?.duration ?: 0
        // There can be few ms difference
        if(current >= duration) {
            currentPlayer?.seekTo(0)
        }
        currentPlayer?.play()
    }

    override fun pause(autoPaused: Boolean) {
        this.autoPaused = autoPaused
        currentPlayer?.pause()
    }

    override fun stop() {
        currentPlayer?.stop()
    }

    override fun seekTo(positionMs: Long) {
        currentPlayer?.seekTo(positionMs)
    }

    override fun setVolume(volume: Float) {
        currentPlayer?.volume = volume
    }

    override fun getVolume(): Float = currentPlayer?.volume ?: 0f

    override fun isMuted(): Boolean {
        return currentPlayer?.volume == 0F
    }

    override fun mute() {
        currentPlayer?.volume = 0F
        playerStateListener?.onMutedChanged(true)
    }

    override fun unmute() {
        currentPlayer?.volume = 1F
        playerStateListener?.onMutedChanged(false)
    }

    override fun isPlaying(): Boolean = currentPlayer?.isPlaying ?: false

    override fun getDuration(): Long = currentPlayer?.duration ?: 0L

    override fun getCurrentPosition(): Long = currentPlayer?.currentPosition ?: 0L

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun switchCurrentPlayer(newPlayer: Player) {
        if (this.currentPlayer === newPlayer) {
            return
        }

        if(newPlayer === castPlayer) {
            playerStateListener?.onPlayerTypeChanged(newPlayer, PlayerType.CAST_PLAYER)
        } else {
            playerStateListener?.onPlayerTypeChanged(newPlayer, PlayerType.DEFAULT_PLAYER)
        }

        currentPlayer?.removeListener(playerListener)

        var newPlaybackPositionMs = C.TIME_UNSET
        var newPlayWhenReady = false
        val previousPlayer: Player? = currentPlayer

        if (previousPlayer != null) {
            val playbackState = previousPlayer.playbackState

            if (playbackState != Player.STATE_ENDED) {
                newPlaybackPositionMs = previousPlayer.currentPosition
                newPlayWhenReady = previousPlayer.playWhenReady
            }

            previousPlayer.removeListener(playerListener)
            previousPlayer.stop()
            previousPlayer.clearMediaItems()
        }

        currentPlayer = newPlayer
        currentPlayer?.addListener(playerListener)

        mediaItem?.let {
            newPlayer.setMediaItem(it, newPlaybackPositionMs)
        }

        newPlayer.playWhenReady = newPlayWhenReady
        newPlayer.prepare()
    }

    private fun getAvailableVideoQualityOptions(): VideoQualityOptions? {
        val trackGroups = currentPlayer?.currentTracks?.groups ?: return null

        val options = mutableListOf<VideoQuality>()

        trackGroups.forEach {
            for (trackIndex in 0 until it.length) {
                if (it.isTrackSupported(trackIndex)) {
                    val format = it.getTrackFormat(trackIndex)
                    if (format.width != Format.NO_VALUE || format.height != Format.NO_VALUE) {
                        options.add(VideoQuality(format.width, format.height))
                    }
                }
            }
        }

        // Default option (resolution selected automatically by player)
        var selectedOption = VideoQuality(Int.MAX_VALUE, Int.MAX_VALUE)

        options.sortByDescending { it.width + it.height }
        options.add(0, selectedOption)

        trackSelector?.parameters?.let {
            if (it.maxVideoWidth != Int.MAX_VALUE && it.maxVideoHeight != Int.MAX_VALUE) {
                selectedOption = VideoQuality(it.maxVideoWidth, it.maxVideoHeight)
            }
        }

        return VideoQualityOptions(options, selectedOption)
    }

    private fun selectSubtitleTrack(lang: String?) {
        val trackSelectionParameters = currentPlayer?.trackSelectionParameters ?: return
        currentPlayer?.trackSelectionParameters = trackSelectionParameters
            .buildUpon()
            .clearOverridesOfType(C.TRACK_TYPE_TEXT)
            .setIgnoredTextSelectionFlags(C.SELECTION_FLAG_FORCED.inv())
            .setPreferredTextLanguage(lang)
            .build()
    }
}
