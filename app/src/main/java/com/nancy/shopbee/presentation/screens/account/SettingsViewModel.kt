package com.nancy.shopbee.presentation.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nancy.shopbee.domain.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
    @Inject
    constructor(
        private val settingsDataStore: SettingsDataStore,
    ) : ViewModel() {
        // Converts flow to stateflow for compose (updates ui)
        val isDarkTheme =
            settingsDataStore.isDarkThemeFlow
                .stateIn(viewModelScope, SharingStarted.Lazily, false)

        val fontSize =
            settingsDataStore.fontSizeFlow
                .stateIn(viewModelScope, SharingStarted.Lazily, 16)

        fun setDarkTheme(enabled: Boolean) {
            viewModelScope.launch {
                settingsDataStore.setDarkTheme(enabled)
            }
        }

        fun setFontSize(size: Int) {
            viewModelScope.launch { settingsDataStore.setFontSize(size) }
        }
    }
