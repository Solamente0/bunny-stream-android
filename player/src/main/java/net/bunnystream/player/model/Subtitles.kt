package net.bunnystream.player.model

import net.bunnystream.player.ui.widget.SubtitleInfo

data class Subtitles(
    val subtitles: List<SubtitleInfo>,
    val selectedSubtitle: SubtitleInfo? = null
)
