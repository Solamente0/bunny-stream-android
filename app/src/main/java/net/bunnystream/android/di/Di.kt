package net.bunnystream.android.di

import android.annotation.SuppressLint
import android.content.Context
import net.bunnystream.android.settings.LocalPrefs
import net.bunnystream.androidsdk.BunnyStreamSdk
import net.bunnystream.androidsdk.IBunnyStreamSdk

@SuppressLint("StaticFieldLeak")
object Di {
    private lateinit var context: Context
    fun initialize(context: Context){
        this.context = context
    }

    private val prefs by lazy { context.getSharedPreferences("", Context.MODE_PRIVATE) }

    val localPrefs by lazy { LocalPrefs(prefs) }

    private val sdkInstances: MutableMap<String, IBunnyStreamSdk> = mutableMapOf()

    fun getBunnyStreamSdk(accessKey: String): IBunnyStreamSdk {
        return sdkInstances.getOrPut(accessKey) { BunnyStreamSdk(accessKey) }
    }
}