package net.bunnystream.player.model

data class Subtitles(
    val subtitles: List<SubtitleInfo>,
    val selectedSubtitle: SubtitleInfo? = null
)
