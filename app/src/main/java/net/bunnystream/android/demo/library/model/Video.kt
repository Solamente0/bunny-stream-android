package net.bunnystream.android.demo.library.model

data class Video(
    val id: String,
    val name: String,
    val duration: String,
    val status: VideoStatus,
)

enum class VideoStatus(private val value: Int) {
    CREATED(0),
    UPLOADED(1),
    PROCESSING(2),
    TRANSCODING(3),
    FINISHED(4),
    ERROR(5),
    UPLOAD_FAILED(6)
}
