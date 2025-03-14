package net.bunnystream.bunnystreamplayer.model

data class AudioTrackInfoOptions(
    val options: List<AudioTrackInfo>,
    val selectedOption: AudioTrackInfo? = null
)
