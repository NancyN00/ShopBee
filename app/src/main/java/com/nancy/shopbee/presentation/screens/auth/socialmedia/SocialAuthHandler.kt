package com.nancy.shopbee.presentation.screens.auth.socialmedia
import com.nancy.shopbee.BuildConfig
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.nancy.shopbee.navigation.Screens
import com.nancy.shopbee.presentation.screens.account.SettingsViewModel
import com.nancy.shopbee.presentation.screens.auth.viewmodel.AuthViewModel

@Composable
fun rememberSocialAuthHandler(
    navController: NavController,
    authViewModel: AuthViewModel,
    settingsViewModel : SettingsViewModel
): SocialAuthHandlers {
    val context = LocalContext.current
    val token = BuildConfig.GOOGLE_CLIENT_ID

    /** Google Setup  */
    val googleSignInClient = remember {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(token)
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }

    val googleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)

            authViewModel.onSocialLogin(credential) {
                // Update settings so the app knows user is in!
                settingsViewModel.setUserSignedIn(true)
                navController.navigate(Screens.HomeScreen.name) {
                    popUpTo(Screens.LoginScreen.name) { inclusive = true }
                }
            }
        }
    }

    // return a simple object that triggers these actions
    return remember {
        SocialAuthHandlers(
            onGoogleClick = { googleLauncher.launch(googleSignInClient.signInIntent) },
          //  onFacebookClick = { /* Add facebook LoginManager.logIn logic here */ }
        )
    }
}

data class SocialAuthHandlers(
    val onGoogleClick: () -> Unit,
   // val onFacebookClick: () -> Unit
)
