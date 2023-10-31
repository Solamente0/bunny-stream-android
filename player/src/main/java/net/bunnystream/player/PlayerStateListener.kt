package net.bunnystream.player

import androidx.media3.common.Player

interface PlayerStateListener {
    fun onPlayerTypeChanged(player: Player, playerType: PlayerType)
    fun onPlayingChanged(isPlaying: Boolean)
    fun onMutedChanged(isMuted: Boolean)
    fun onPlaybackSpeedChanged(speed: Float)
    fun onLoadingChanged(isLoading: Boolean)
}