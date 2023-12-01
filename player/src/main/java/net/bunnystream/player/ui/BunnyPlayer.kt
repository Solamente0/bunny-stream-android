package net.bunnystream.player.ui

import net.bunnystream.player.model.PlayerIconSet

interface BunnyPlayer {

    /**
     * Apply custom icons to video player interface
     */
    var iconSet: PlayerIconSet

    /**
     * Plays a video and fetches additional info, e.g. chapters, moments and subtitles
     *
     * @param libraryId Video Library ID
     * @param videoId Video ID
     */
    fun playVideo(libraryId: Long, videoId: String)

    /**
     * Pauses video
     */
    fun pause()

    /**
     * Resumes playing video
     */
    fun play()
}