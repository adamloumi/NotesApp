package com.example.notesapp

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.themeDataStore by preferencesDataStore("theme_prefs")

object ThemeStore {

    private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme_enabled")

    fun getTheme(context: Context): Flow<Boolean> {
        return context.themeDataStore.data.map { prefs ->
            prefs[DARK_THEME_KEY] ?: false
        }
    }

    suspend fun saveTheme(context: Context, darkMode: Boolean) {
        context.themeDataStore.edit { prefs ->
            prefs[DARK_THEME_KEY] = darkMode
        }
    }
}
