package net.bunnystream.android

import android.app.Application
import net.bunnystream.android.di.Di

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Di.initialize(this)
    }
}