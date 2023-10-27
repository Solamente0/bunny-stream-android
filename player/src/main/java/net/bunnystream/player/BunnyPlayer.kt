package net.bunnystream.player

import android.content.Context
import android.view.ViewGroup
import net.bunnystream.player.common.Player

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
interface BunnyPlayer: Player {

    fun loadVideo(url: String)
}

class BunnyPlayerBuilder(private val context: Context) {

    fun build(parentView: ViewGroup): BunnyPlayer {
        return DefaultBunnyPlayer()
    }
}
