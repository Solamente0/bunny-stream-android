package net.bunnystream.player

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.media3.cast.CastPlayer
import androidx.media3.cast.SessionAvailabilityListener
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.HttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.ima.ImaAdsLoader
import androidx.media3.exoplayer.ima.ImaServerSideAdInsertionMediaSource
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView
import com.google.android.gms.cast.framework.CastState
import net.bunnystream.androidsdk.BunnyStreamSdk
import net.bunnystream.player.common.BunnyPlayer
import net.bunnystream.player.context.AppCastContext
import org.openapitools.client.models.VideoModel

@SuppressLint("UnsafeOptInUsageError")
class DefaultBunnyPlayer private constructor(private val context: Context) : BunnyPlayer {

    companion object {
        private const val TAG = "DefaultBunnyPlayer"

        private const val TEST_AD = "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct%3Dlinear&correlator="

        private const val SEEK_SKIP_MILLIS = 10 * 1000

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

    override var playerStateListener: PlayerStateListener? = null
        set(value) {
            field = value
            playerStateListener?.onPlayingChanged(isPlaying())
            playerStateListener?.onMutedChanged(isMuted())
        }

    private var mediaItem: MediaItem? = null

    private val httpDataSourceFactory: HttpDataSource.Factory =
        DefaultHttpDataSource.Factory().setAllowCrossProtocolRedirects(true)

    private val dataSourceFactory: DataSource.Factory = DataSource.Factory {
        val dataSource: HttpDataSource = httpDataSourceFactory.createDataSource()
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
    }

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

    override fun playVideo(playerView: PlayerView, libraryId: Long, video: VideoModel) {
        Log.d(TAG, "loadVideo libraryId=$libraryId video=$video")

        val imaLoader = ImaAdsLoader.Builder(context).build()

        val mediaSourceFactory = DefaultMediaSourceFactory(context)
            .setDataSourceFactory(dataSourceFactory)
            .setLocalAdInsertionComponents({ imaLoader }, playerView)

        localPlayer = ExoPlayer.Builder(context)
            .setMediaSourceFactory(mediaSourceFactory)
            .build().also {
                it.addListener(playerListener)
            }

        currentPlayer = localPlayer
        imaLoader.setPlayer(currentPlayer)
        serverSideAdLoader?.setPlayer(currentPlayer!!)

        val url = "${BunnyStreamSdk.cdnHostname}/${video.guid}/playlist.m3u8"
        val drmLicenseUri = "${BunnyStreamSdk.baseApi}/WidevineLicense/$libraryId/${video.guid}?contentId=${video.guid}"

        mediaItem = MediaItem.Builder()
            .setUri(url)
            .setMimeType(MimeTypes.APPLICATION_M3U8)
            // TODO(Esed): suggest API changes to have DRM settings returned in VideoModel
            .setDrmConfiguration(drmConfig.setLicenseUri(drmLicenseUri).build())

            // TODO(Esed): suggest API changes to have VAST tag returned in VideoModel
            .setAdsConfiguration(MediaItem.AdsConfiguration.Builder(Uri.parse(TEST_AD)).build())
            .build().also {
                currentPlayer?.setMediaItem(it)
            }

        currentPlayer?.playWhenReady = true
        currentPlayer?.prepare()
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

    override fun release() {
        currentPlayer?.stop()

        localPlayer?.release()
        localPlayer = null

        castPlayer?.release()
        castPlayer = null

        instance = null
    }

    override fun play() {
        currentPlayer?.play()
    }

    override fun pause() {
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
        Log.d(TAG, "switchCurrentPlayer $newPlayer")

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
}
