package net.bunnystream.android.demo.di

import android.annotation.SuppressLint
import android.content.Context
import net.bunnystream.android.demo.library.DefaultVideoUploadService
import net.bunnystream.android.demo.settings.LocalPrefs
import net.bunnystream.api.BunnyStreamApi
import net.bunnystream.api.StreamApi

@SuppressLint("StaticFieldLeak")
class Di(private val context: Context) {

    private val prefs = context.getSharedPreferences("", Context.MODE_PRIVATE)
    val localPrefs = LocalPrefs(prefs)

    init {
        BunnyStreamApi.initialize(context, localPrefs.accessKey, localPrefs.libraryId)
    }

    var streamApi: StreamApi = BunnyStreamApi.getInstance()
        private set

    var videoUploadService = DefaultVideoUploadService(streamApi.videoUploader)

    var tusVideoUploadService = DefaultVideoUploadService(streamApi.tusVideoUploader)

    fun updateKeys(accessKey: String, libraryId: Long) {
        localPrefs.accessKey = accessKey
        localPrefs.libraryId = libraryId
        BunnyStreamApi.initialize(context, accessKey, libraryId)
        streamApi = BunnyStreamApi.getInstance()
        videoUploadService = DefaultVideoUploadService(streamApi.videoUploader)
        tusVideoUploadService = DefaultVideoUploadService(streamApi.tusVideoUploader)
    }
}