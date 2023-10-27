package net.bunnystream.player.common

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.getHexFromResource(@ColorRes colorRes: Int): String {
    return "#" + Integer.toHexString(
        ContextCompat.getColor(
            this,
            colorRes
        ) and 0x00ffffff
    )

}