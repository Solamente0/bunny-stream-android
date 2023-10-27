package net.bunnystream.player

import android.content.Context
import android.view.ViewGroup
import net.bunnystream.player.common.Player
import net.bunnystream.player.model.BunnyPlayerIconSet

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
interface BunnyPlayer: Player {

    fun loadVideo(url: String)

//    fun loadVideo(libraryId: String, videoId: String, cdnHost: String)

//    configure(apiKey)

}

// TODO Jan: Extend FrameLayout here
class BunnyPlayerBuilder(private val context: Context) {
    private var iconSet: BunnyPlayerIconSet = DEFAULT_ICON_SET

    fun setIconSet(iconSet: BunnyPlayerIconSet): BunnyPlayerBuilder {
        this.iconSet = iconSet
        return this
    }

    fun build(parentView: ViewGroup): BunnyPlayer {
        return DefaultBunnyPlayer(context, parentView, iconSet = iconSet)
    }

    companion object {
        private val DEFAULT_ICON_SET = BunnyPlayerIconSet(
            R.drawable.ic_play,
            R.drawable.ic_pause,
            R.drawable.ic_rewind,
            R.drawable.ic_forward,
            R.drawable.ic_settings,
            R.drawable.ic_sound_on,
            R.drawable.ic_sound_off,
            R.drawable.ic_cast,
            R.drawable.ic_fullscreen_on,
            R.drawable.ic_fullscreen_off,
        )
    }
}
