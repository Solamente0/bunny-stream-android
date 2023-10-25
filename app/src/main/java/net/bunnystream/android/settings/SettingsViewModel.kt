package net.bunnystream.android.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.setValue
import net.bunnystream.android.App

class SettingsViewModel : ViewModel() {

    private val prefs = App.di.localPrefs

    var accessKey by mutableStateOf(prefs.accessKey)
        private set

    fun updateAccessKey(key: String){
        accessKey = key
        App.di.updateAccessKey(accessKey)
    }
}