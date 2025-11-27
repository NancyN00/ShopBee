package com.nancy.shopbee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.nancy.shopbee.navigation.ShopBeeNavigation
import com.nancy.shopbee.presentation.screens.onboard.OnboardingScreen
import com.nancy.shopbee.ui.theme.ShopBeeTheme
import com.nancy.shopbee.utils.OnboardingUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val onboardingUtils by lazy { OnboardingUtils(this) }

    //remember to implement preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            ShopBeeTheme {
                var showOnboarding by remember {
                    mutableStateOf(!onboardingUtils.isOnboardingCompleted())
                }

                if (showOnboarding) {
                    OnboardingScreen {
                        onboardingUtils.setOnboardingCompleted()
                        showOnboarding = false
                    }
                } else {
                    // add later during fb auth
                    //    OnboardingStart()

                    ShopBeeNavigation()
                }
            }
        }
    }
}
