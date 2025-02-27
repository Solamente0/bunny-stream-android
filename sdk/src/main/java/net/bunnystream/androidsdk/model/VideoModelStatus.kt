package net.bunnystream.androidsdk.model

enum class VideoModelStatus(val value: Int) {
    CREATED(0),
    UPLOADED(1),
    PROCESSING(2),
    TRANSCODING(3),
    FINISHED(4),
    ERROR(5),
    UPLOAD_FAILED(6),
    JIT_SEGMENTING(7),
    JIT_PLAYLISTS_CREATED(8);

    companion object {
        @JvmStatic
        fun fromValue(value: Int): VideoModelStatus = values().find { it.value == value }
            ?: throw IllegalArgumentException("Unknown VideoModelStatus value: $value")
    }
}