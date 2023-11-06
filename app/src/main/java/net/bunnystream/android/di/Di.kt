package net.bunnystream.android.di

import android.annotation.SuppressLint
import android.content.Context
import net.bunnystream.android.library.DefaultVideoUploadService
import net.bunnystream.android.settings.LocalPrefs
import net.bunnystream.androidsdk.BunnyStreamSdk
import net.bunnystream.androidsdk.StreamSdk

@SuppressLint("StaticFieldLeak")
class Di(private val context: Context) {

    private val prefs = context.getSharedPreferences("", Context.MODE_PRIVATE)
    val localPrefs = LocalPrefs(prefs)

    init {
        BunnyStreamSdk.initialize(context, localPrefs.accessKey, localPrefs.cdnHostname)
    }

    var streamSdk: StreamSdk = BunnyStreamSdk.getInstance()
        private set

    var videoUploadService = DefaultVideoUploadService(streamSdk.videoUploader)

    var tusVideoUploadService = DefaultVideoUploadService(streamSdk.tusVideoUploader)

    fun updateKeys(accessKey: String, cdnHostname: String) {
        localPrefs.accessKey = accessKey
        localPrefs.cdnHostname = cdnHostname
        BunnyStreamSdk.initialize(context, accessKey, cdnHostname)
        streamSdk = BunnyStreamSdk.getInstance()
        videoUploadService = DefaultVideoUploadService(streamSdk.videoUploader)
        tusVideoUploadService = DefaultVideoUploadService(streamSdk.tusVideoUploader)
    }
}