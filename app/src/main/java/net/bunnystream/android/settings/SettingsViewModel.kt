package net.bunnystream.android.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import net.bunnystream.android.di.Di
import androidx.compose.runtime.setValue

class SettingsViewModel : ViewModel() {

    private val prefs = Di.localPrefs

    var accessKey by mutableStateOf(prefs.accessKey)
        private set

    fun updateAccessKey(key: String){
        accessKey = key
        prefs.accessKey = key
    }
}