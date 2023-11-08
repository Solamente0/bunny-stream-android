package net.bunnystream.player.model

data class MomentsContainer(
    val totalDuration: Long,
    val momentsList: List<Moment>
)
/**
 *
 *
 * @param label The text description label for the moment
 * @param timestamp The timestamp of the moment in milliseconds
 */
data class Moment(
    val label: String,
    val timestamp: Long
)
