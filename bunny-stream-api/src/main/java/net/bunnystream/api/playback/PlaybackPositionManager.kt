package net.bunnystream.api.playback

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface PlaybackPositionManager {
    suspend fun savePosition(videoId: String, position: Long, duration: Long)
    suspend fun getPosition(videoId: String): PlaybackPosition?
    suspend fun clearPosition(videoId: String)
    suspend fun cleanupExpiredPositions()
}

data class PlaybackPosition(
    val videoId: String,
    val position: Long,
    val duration: Long,
    val timestamp: Long,
    val watchPercentage: Float
)

data class ResumeConfig(
    val retentionDays: Int = 7,
    val minimumWatchTime: Long = 30_000L, // 30 seconds
    val resumeThreshold: Float = 0.05f, // Don't resume if < 5% watched
    val nearEndThreshold: Float = 0.95f // Don't resume if > 95% watched
)

class DefaultPlaybackPositionManager(
    private val context: Context,
    private val config: ResumeConfig
) : PlaybackPositionManager {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        "bunny_playback_positions", 
        Context.MODE_PRIVATE
    )
    private val gson = Gson()

    override suspend fun savePosition(videoId: String, position: Long, duration: Long) = withContext(Dispatchers.IO) {
        if (duration <= 0) return@withContext
        
        val watchPercentage = position.toFloat() / duration.toFloat()
        
        // Only save if meaningful progress
        if (position > config.minimumWatchTime &&
            watchPercentage >= config.resumeThreshold &&
            watchPercentage <= config.nearEndThreshold) {
            
            val playbackPosition = PlaybackPosition(
                videoId = videoId,
                position = position,
                duration = duration,
                timestamp = System.currentTimeMillis(),
                watchPercentage = watchPercentage
            )
            
            prefs.edit()
                .putString("position_$videoId", gson.toJson(playbackPosition))
                .apply()
        }
    }

    override suspend fun getPosition(videoId: String): PlaybackPosition? = withContext(Dispatchers.IO) {
        val json = prefs.getString("position_$videoId", null) ?: return@withContext null
        
        try {
            val position = gson.fromJson(json, PlaybackPosition::class.java)
            
            // Check if expired
            val daysSinceWatched = (System.currentTimeMillis() - position.timestamp) / (24 * 60 * 60 * 1000)
            
            if (daysSinceWatched <= config.retentionDays) {
                position
            } else {
                // Remove expired position
                prefs.edit().remove("position_$videoId").apply()
                null
            }
        } catch (e: Exception) {
            // Remove corrupted data
            prefs.edit().remove("position_$videoId").apply()
            null
        }
    }

    override suspend fun clearPosition(videoId: String) = withContext(Dispatchers.IO) {
        prefs.edit().remove("position_$videoId").apply()
    }

    override suspend fun cleanupExpiredPositions() = withContext(Dispatchers.IO) {
        val all = prefs.all
        val currentTime = System.currentTimeMillis()
        val editor = prefs.edit()
        
        for ((key, value) in all) {
            if (key.startsWith("position_") && value is String) {
                try {
                    val position = gson.fromJson(value, PlaybackPosition::class.java)
                    val daysSinceWatched = (currentTime - position.timestamp) / (24 * 60 * 60 * 1000)
                    
                    if (daysSinceWatched > config.retentionDays) {
                        editor.remove(key)
                    }
                } catch (e: Exception) {
                    // Remove corrupted data
                    editor.remove(key)
                }
            }
        }
        
        editor.apply()
    }
}
