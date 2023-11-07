package net.bunnystream.player.ui.widget

data class Subtitles(
    val subtitles: List<SubtitleInfo>,
    val selectedSubtitle: SubtitleInfo? = null
)
