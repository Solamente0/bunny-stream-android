package net.bunnystream.player.common

import android.content.Context
import android.util.Log
import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlMap
import com.charleskorn.kaml.yamlMap

class I18n(private val context: Context) {

    companion object {
        private const val TAG = "I18n"
    }

    private var translationsMap: YamlMap? = null

    fun load(lang: String) {
        try {
            val id = context.resources.getIdentifier(
                lang.lowercase(),
                "raw",
                context.packageName
            )

            val i18n = context.resources.openRawResource(id).bufferedReader().use { it.readText() }
            translationsMap = Yaml.default.parseToYamlNode(i18n).yamlMap["i18n"]
        } catch (e: Exception){
            Log.w(TAG, "Couldn't load translations file for $lang language: ${e.message}")
            e.printStackTrace()
        }
    }

    fun getTranslation(key: String, default: String): String {
        return translationsMap?.getScalar(key)?.content ?: default
    }
}