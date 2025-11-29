package com.nancy.shopbee.domain

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// where is the module for this?
@Singleton
class SettingsDataStore
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting_pref")
        private val setDataStore = context.dataStore

        companion object {
            val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
            val FONT_SIZE = intPreferencesKey("font_size")
        }

        val isDarkThemeFlow: Flow<Boolean> =
            setDataStore.data
                .map { preferences ->
                    preferences[IS_DARK_THEME] ?: false
                }

        val fontSizeFlow: Flow<Int> = setDataStore.data.map { it[FONT_SIZE] ?: 16 }

        suspend fun setDarkTheme(isDark: Boolean) {
            setDataStore.edit { preferences ->
                preferences[IS_DARK_THEME] = isDark
            }
        }

        suspend fun setFontSize(size: Int) {
            setDataStore.edit { it[FONT_SIZE] = size }
        }
    }
