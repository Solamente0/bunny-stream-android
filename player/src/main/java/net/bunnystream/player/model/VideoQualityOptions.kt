package net.bunnystream.player.model

data class VideoQualityOptions(
    val options: List<VideoQuality>,
    val selectedOption: VideoQuality? = null
)
