package net.bunnystream.android.settings

fun String.toLongOrDefault(defaultValue: Long): Long {
    return try {
        this.toLong()
    } catch (e: Exception) {
        return defaultValue
    }
}