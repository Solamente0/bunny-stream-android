package net.bunnystream.bunnystreamplayer.model

data class Subtitles(
    val subtitles: List<SubtitleInfo>,
    val selectedSubtitle: SubtitleInfo? = null
)
