package com.nancy.shopbee

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.nancy.shopbee.domain.SettingsDataStore
import com.nancy.shopbee.presentation.screens.MainScreen
import com.nancy.shopbee.utils.OnboardingUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val onboardingUtils by lazy { OnboardingUtils(this) }

    @Inject
    lateinit var settingsDataStore: SettingsDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        enableEdgeToEdge()

        LoginManager.getInstance().registerCallback(
            FacebookManager.callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    Log.d("FB_LOGIN", "SUCCESS: ${result.accessToken.token}")

                    FacebookAuthState.onTokenReceived?.invoke(result.accessToken.token)
                }

                override fun onCancel() {
                    Log.d("FB_LOGIN", "CANCELLED")
                }

                override fun onError(error: FacebookException) {
                    Log.e("FB_LOGIN", "ERROR", error)
                }
            },
        )

        setContent {
            MainScreen(
                onboardingUtils = onboardingUtils,
                settingsDataStore = settingsDataStore,
            )
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        FacebookManager.callbackManager.onActivityResult(
            requestCode,
            resultCode,
            data,
        )
    }
}

/**
 * Shared CallbackManager
 */
object FacebookManager {
    val callbackManager: CallbackManager = CallbackManager.Factory.create()
}

/**
 * Bridge between SDK → ViewModel layer
 */
object FacebookAuthState {
    var onTokenReceived: ((String) -> Unit)? = null
}
