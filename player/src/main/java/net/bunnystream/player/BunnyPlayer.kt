package net.bunnystream.player

import android.content.Context
import androidx.media3.common.util.Assertions
import net.bunnystream.player.common.Player

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
interface BunnyPlayer: Player, Player.PlaybackControl, Player.VolumeControl, Player.VideoInformation, Player.VideoControl {

    class Builder {

        var context: Context? = null
        var buildCalled: Boolean = false

        private operator fun invoke(context: Context?) {
            this.context = Assertions.checkNotNull(context)
        }

        fun build(): BunnyPlayer {
            Assertions.checkState(!buildCalled)
            buildCalled = true
            return BunnyPlayerImpl(builder = this)
        }
    }
}
