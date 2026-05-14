package com.nancy.shopbee.presentation.screens.auth.socialmedia

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.nancy.shopbee.BuildConfig
import com.nancy.shopbee.FacebookManager
import com.nancy.shopbee.navigation.Screens
import com.nancy.shopbee.presentation.screens.account.SettingsViewModel
import com.nancy.shopbee.presentation.screens.auth.viewmodel.AuthViewModel

@Composable
fun rememberSocialAuthHandler(
    navController: NavController,
    authViewModel: AuthViewModel,
    settingsViewModel: SettingsViewModel,
): SocialAuthHandler {
    val context = LocalContext.current
    val activity = context as? Activity

    val googleClientId = BuildConfig.GOOGLE_CLIENT_ID
    Log.d("GOOGLE_CLIENT_ID", googleClientId)

    val googleSignInClient =
        remember {
            val gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(googleClientId)
                    .requestEmail()
                    .build()

            GoogleSignIn.getClient(context, gso)
        }

    val googleLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
        ) { result ->

            try {
                if (result.resultCode == Activity.RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    val account = task.getResult(ApiException::class.java)
                    val idToken = account.idToken

                    Log.d("GOOGLE_TOKEN", "ID Token: $idToken")

                    if (idToken == null) {
                        Log.e("GOOGLE_SIGN_IN", "Google ID token is null")
                        return@rememberLauncherForActivityResult
                    }

                    val credential = GoogleAuthProvider.getCredential(idToken, null)

                    authViewModel.onSocialLogin(credential) {
                        settingsViewModel.setUserSignedIn(true)
                        navController.navigate(Screens.HomeScreen.name) {
                            popUpTo(Screens.LoginScreen.name) { inclusive = true }
                        }
                    }
                }
            } catch (e: ApiException) {
                Log.e("GOOGLE_SIGN_IN", "Google sign in failed: ${e.statusCode}", e)
            } catch (e: IllegalStateException) {
                Log.e("GOOGLE_SIGN_IN", "Illegal state during Google sign in", e)
            }
        }

    DisposableEffect(Unit) {
        val fbCallback =
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    Log.d("FB_FLOW", "Facebook login success reached")

                    val credential =
                        FacebookAuthProvider.getCredential(
                            result.accessToken.token,
                        )

                    authViewModel.onSocialLogin(credential) {
                        Log.d("FB_FLOW", "AuthViewModel success callback")

                        settingsViewModel.setUserSignedIn(true)

                        navController.navigate(Screens.HomeScreen.name) {
                            popUpTo(Screens.LoginScreen.name) { inclusive = true }
                        }
                    }
                }

                override fun onCancel() {
                    Log.d("FB_FLOW", "Facebook login cancelled")
                }

                override fun onError(error: FacebookException) {
                    Log.e("FB_FLOW", "Facebook login error", error)
                }
            }

        LoginManager.getInstance()
            .registerCallback(FacebookManager.callbackManager, fbCallback)

        onDispose {
            LoginManager.getInstance()
                .unregisterCallback(FacebookManager.callbackManager)
        }
    }

    return remember {
        SocialAuthHandler(
            onGoogleClick = {
                Log.d("GOOGLE_SIGN_IN", "Launching Google Sign In")
                googleLauncher.launch(googleSignInClient.signInIntent)
            },
            onFacebookClick = {
                if (activity == null) {
                    Log.e("FACEBOOK_LOGIN", "Context is not Activity")
                    return@SocialAuthHandler
                }

                LoginManager.getInstance()
                    .logInWithReadPermissions(
                        activity,
                        listOf("public_profile"),
                    )
            },
        )
    }
}

// ----------------------------------------------------------------
// Data class that holds the two click callbacks
// ----------------------------------------------------------------
data class SocialAuthHandler(
    val onGoogleClick: () -> Unit,
    val onFacebookClick: () -> Unit,
)
