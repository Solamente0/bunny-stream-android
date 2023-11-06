package net.bunnystream.android.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import net.bunnystream.android.App

class SettingsViewModel : ViewModel() {

    private val prefs = App.di.localPrefs

    var accessKey by mutableStateOf(prefs.accessKey)
        private set

    var cdnHostname by mutableStateOf(prefs.cdnHostname)
        private set

    fun updateKeys(accessKey: String, cdnHostname: String){
        this.accessKey = accessKey
        this.cdnHostname = cdnHostname
        App.di.updateKeys(accessKey, cdnHostname)
    }
}