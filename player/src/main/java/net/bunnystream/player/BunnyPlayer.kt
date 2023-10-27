package net.bunnystream.player

import android.content.Context
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import net.bunnystream.player.common.Constants
import net.bunnystream.player.common.Player
import net.bunnystream.player.common.getHexFromResource
import net.bunnystream.player.model.BunnyPlayerIconSet

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
interface BunnyPlayer: Player {

    fun loadVideo(url: String)

//    fun loadVideo(libraryId: String, videoId: String, cdnHost: String)

//    configure(apiKey)

}

// TODO Jan: Extend FrameLayout here
class BunnyPlayerBuilder(private val context: Context) {
    private var iconSet: BunnyPlayerIconSet = Constants.DEFAULT_ICON_SET
    private var colorTheme: String = Constants.DEFAULT_THEME_COLOR
    private var fontRes: Int = Constants.DEFAULT_FONT

    fun setIconSet(iconSet: BunnyPlayerIconSet): BunnyPlayerBuilder {
        this.iconSet = iconSet
        return this
    }

    fun setThemeColor(@ColorRes colorRes: Int): BunnyPlayerBuilder {
        this.colorTheme = context.getHexFromResource(colorRes)
        return this
    }

    fun setThemeColor(colorHex: String): BunnyPlayerBuilder {
        this.colorTheme = colorHex
        return this
    }

    fun setFont(@FontRes font: Int): BunnyPlayerBuilder {
        this.fontRes = font
        return this
    }

    fun build(parentView: ViewGroup): BunnyPlayer {
        return DefaultBunnyPlayer(
            context,
            parentView,
            iconSet = iconSet,
            colorTheme = colorTheme,
            font = fontRes,
        )
    }
}
