package net.bunnystream.player

import net.bunnystream.player.common.PlaybackState
import net.bunnystream.player.common.VideoQuality
import net.bunnystream.player.ui.BunnyVideoPlayer

class DefaultBunnyPlayer(
    private val bunnyVideoPlayer: BunnyVideoPlayer,
): BunnyPlayer {

    override fun loadVideo(url: String) {
        bunnyVideoPlayer.loadVideo(url)
    }

    override fun release() {
        bunnyVideoPlayer.release()
    }

    override fun play() {
        bunnyVideoPlayer.play()
    }

    override fun pause() {
        bunnyVideoPlayer.pause()
    }

    override fun stop() {
        bunnyVideoPlayer.stop()
    }

    override fun seekTo(positionMs: Long) {
        bunnyVideoPlayer.seekTo(positionMs)
    }

    override fun setVolume(volume: Float) {
        bunnyVideoPlayer.setVolume(volume)
    }

    override fun getVolume(): Float = bunnyVideoPlayer.getVolume()

    override fun mute() {
        bunnyVideoPlayer.mute()
    }

    override fun unmute() {
        bunnyVideoPlayer.unmute()
    }

    override fun isPlaying(): Boolean = bunnyVideoPlayer.isPlaying()

    override fun getDuration(): Long = bunnyVideoPlayer.getDuration()

    override fun getCurrentPosition(): Long = bunnyVideoPlayer.getCurrentPosition()

    override fun getBufferedPercentage(): Int {
        TODO("Not yet implemented")
    }

    override fun setPlaybackStateListener(listener: PlaybackState) {
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

    override fun setFullscreen(fullscreen: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setAspectRatio(aspectRatio: Float) {
        TODO("Not yet implemented")
    }

}
