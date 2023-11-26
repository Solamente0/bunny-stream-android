package net.bunnystream.player

import androidx.media3.common.Player
import net.bunnystream.player.model.Chapter
import net.bunnystream.player.model.Moment
import net.bunnystream.player.model.RetentionGraphEntry

interface PlayerStateListener {
    fun onPlayerTypeChanged(player: Player, playerType: PlayerType)
    fun onPlayingChanged(isPlaying: Boolean)
    fun onMutedChanged(isMuted: Boolean)
    fun onPlaybackSpeedChanged(speed: Float)
    fun onLoadingChanged(isLoading: Boolean)
    fun onChaptersUpdated(chapters: List<Chapter>)
    fun onMomentsUpdated(moments: List<Moment>)
    fun onRetentionGraphUpdated(points: List<RetentionGraphEntry>)
}