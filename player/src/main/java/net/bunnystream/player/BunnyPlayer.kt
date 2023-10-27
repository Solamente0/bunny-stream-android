package net.bunnystream.player

import android.content.Context
import android.view.ViewGroup
import net.bunnystream.player.common.Player
import net.bunnystream.player.ui.BunnyVideoPlayer
import net.bunnystream.player.model.BunnyPlayerIconSet

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
interface BunnyPlayer: Player {

    fun loadVideo(url: String)
}

class BunnyPlayerBuilder(private val context: Context) {
    private var iconSet: BunnyPlayerIconSet? = null

    fun setIconSet(iconSet: BunnyPlayerIconSet): BunnyPlayerBuilder {
        this.iconSet = iconSet
        return this
    }

    fun build(parentView: ViewGroup): BunnyPlayer {
        val bunnyVideoPlayer = BunnyVideoPlayer(context, iconSet = iconSet)
        parentView.addView(bunnyVideoPlayer)
        return DefaultBunnyPlayer(bunnyVideoPlayer)
    }
}
