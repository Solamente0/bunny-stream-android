package net.bunnystream.player

import android.content.Context
import android.view.ViewGroup
import net.bunnystream.player.common.Player
import net.bunnystream.player.ui.BunnyVideoPlayer

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
interface BunnyPlayer: Player {

    fun loadVideo(url: String)
}

class BunnyPlayerBuilder(private val context: Context) {

    fun build(parentView: ViewGroup): BunnyPlayer {
        val bunnyVideoPlayer = BunnyVideoPlayer(context)
        parentView.addView(bunnyVideoPlayer)
        return DefaultBunnyPlayer(bunnyVideoPlayer)
    }
}
