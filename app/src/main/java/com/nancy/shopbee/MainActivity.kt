package com.nancy.shopbee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nancy.shopbee.domain.SettingsDataStore
import com.nancy.shopbee.navigation.ShopBeeNavigation
import com.nancy.shopbee.presentation.screens.MainScreen
import com.nancy.shopbee.presentation.screens.auth.login.LoginScreen
import com.nancy.shopbee.presentation.screens.onboard.OnboardingScreen
import com.nancy.shopbee.ui.theme.ShopBeeTheme
import com.nancy.shopbee.utils.OnboardingUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// add work manager for caching with room
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val onboardingUtils by lazy { OnboardingUtils(this) }
    @Inject lateinit var settingsDataStore: SettingsDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        setContent {
            MainScreen(
                onboardingUtils = onboardingUtils,
                settingsDataStore = settingsDataStore
            )
        }
    }
}
