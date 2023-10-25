package net.bunnystream.android.settings

import android.content.SharedPreferences

class LocalPrefs(private val prefs: SharedPreferences) {

    companion object {
        private const val ACCESS_KEY = "accessKey"
        private const val LIBRARY_ID = "libraryId"
        private const val CDN_HOSTNAME = "cdnHostname"
    }

    var accessKey: String
        set(value) {
            prefs.edit().putString(ACCESS_KEY, value).apply()
        }
        get() = prefs.getString(ACCESS_KEY, "") ?: ""

    var libraryId: Long
        set(value) {
            prefs.edit().putLong(LIBRARY_ID, value).apply()
        }
        get() = prefs.getLong(LIBRARY_ID, -1)

    var cdnHostname: String
        set(value) {
            prefs.edit().putString(CDN_HOSTNAME, value).apply()
        }
        get() = prefs.getString(CDN_HOSTNAME, "") ?: ""
}