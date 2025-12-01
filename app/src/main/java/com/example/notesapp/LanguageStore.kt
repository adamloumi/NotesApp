package com.example.notesapp

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.languageDataStore by preferencesDataStore(name = "language_pref")

object LanguageStore {
    private val LANGUAGE_KEY = stringPreferencesKey("app_language")

    suspend fun saveLanguage(context: Context, lang: String) {
        context.languageDataStore.edit { settings ->
            settings[LANGUAGE_KEY] = lang
        }
    }

    fun getLanguage(context: Context) =
        context.languageDataStore.data.map { prefs ->
            prefs[LANGUAGE_KEY] ?: "en"   // default English
        }
}
