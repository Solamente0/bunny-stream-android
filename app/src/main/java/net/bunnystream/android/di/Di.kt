package net.bunnystream.android.di

import android.annotation.SuppressLint
import android.content.Context
import net.bunnystream.android.library.DefaultVideoUploadService
import net.bunnystream.android.settings.LocalPrefs
import net.bunnystream.androidsdk.BunnyStreamSdk
import net.bunnystream.androidsdk.StreamSdk

@SuppressLint("StaticFieldLeak")
class Di(private val context: Context) {

    private val prefs by lazy { context.getSharedPreferences("", Context.MODE_PRIVATE) }

    val localPrefs by lazy { LocalPrefs(prefs) }

    var streamSdk: StreamSdk = BunnyStreamSdk(context, localPrefs.accessKey)
        private set

    var videoUploadService = DefaultVideoUploadService(streamSdk.videoUploader)

    var tusVideoUploadService = DefaultVideoUploadService(streamSdk.tusVideoUploader)

    fun updateAccessKey(accessKey: String) {
        localPrefs.accessKey = accessKey
        streamSdk = BunnyStreamSdk(context, localPrefs.accessKey)
        videoUploadService = DefaultVideoUploadService(streamSdk.videoUploader)
        tusVideoUploadService = DefaultVideoUploadService(streamSdk.tusVideoUploader)
    }
}