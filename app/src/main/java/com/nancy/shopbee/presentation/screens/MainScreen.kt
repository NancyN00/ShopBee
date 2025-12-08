package com.nancy.shopbee.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import com.nancy.shopbee.domain.SettingsDataStore
import com.nancy.shopbee.navigation.ShopBeeNavigation
import com.nancy.shopbee.ui.theme.ShopBeeTheme
import com.nancy.shopbee.utils.OnboardingUtils

@Composable
fun MainScreen(
    onboardingUtils: OnboardingUtils,
    settingsDataStore: SettingsDataStore
) {
    // Read DataStore → Apply Theme instantly
    val isDarkTheme by settingsDataStore.isDarkThemeFlow.collectAsState(initial = false)
    val fontSize by settingsDataStore.fontSizeFlow.collectAsState(initial = 16)

    val customDensity = Density(
        density = LocalDensity.current.density,
        fontScale = fontSize / 16f
    )

    CompositionLocalProvider(LocalDensity provides customDensity) {
        ShopBeeTheme(darkTheme = isDarkTheme) {
            // Check if onboarding completed
            var showOnboarding by remember {
                mutableStateOf(!onboardingUtils.isOnboardingCompleted())
            }

            // Check if user is already signed in
            val isSignedIn by settingsDataStore.isUserSignedIn.collectAsState(initial = false)

            ShopBeeNavigation(
                showOnboarding = showOnboarding,
                onboardingUtils = onboardingUtils,
                isSignedIn = isSignedIn
            )
        }
    }
}
