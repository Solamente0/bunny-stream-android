package net.bunnystream.player.model

data class AudioTrackInfoOptions(
    val options: List<AudioTrackInfo>,
    val selectedOption: AudioTrackInfo? = null
)
