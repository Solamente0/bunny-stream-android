package net.bunnystream.android.di

import android.annotation.SuppressLint
import android.content.Context
import net.bunnystream.android.library.DefaultVideoUploadService
import net.bunnystream.android.settings.LocalPrefs
import net.bunnystream.api.BunnyStreamApi
import net.bunnystream.api.StreamApi

@SuppressLint("StaticFieldLeak")
class Di(private val context: Context) {

    private val prefs = context.getSharedPreferences("", Context.MODE_PRIVATE)
    val localPrefs = LocalPrefs(prefs)

    init {
        BunnyStreamApi.initialize(context, localPrefs.accessKey, localPrefs.libraryId)
    }

    var streamSdk: StreamApi = BunnyStreamApi.getInstance()
        private set

    var videoUploadService = DefaultVideoUploadService(streamSdk.videoUploader)

    var tusVideoUploadService = DefaultVideoUploadService(streamSdk.tusVideoUploader)

    fun updateKeys(accessKey: String, libraryId: Long) {
        localPrefs.accessKey = accessKey
        localPrefs.libraryId = libraryId
        BunnyStreamApi.initialize(context, accessKey, libraryId)
        streamSdk = BunnyStreamApi.getInstance()
        videoUploadService = DefaultVideoUploadService(streamSdk.videoUploader)
        tusVideoUploadService = DefaultVideoUploadService(streamSdk.tusVideoUploader)
    }
}