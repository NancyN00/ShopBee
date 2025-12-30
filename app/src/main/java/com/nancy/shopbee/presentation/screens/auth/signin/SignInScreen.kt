package com.nancy.shopbee.presentation.screens.auth.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nancy.shopbee.navigation.Screens
import com.nancy.shopbee.presentation.components.PrimaryButton
import com.nancy.shopbee.presentation.components.RowText
import com.nancy.shopbee.presentation.components.SocialMediaBtn
import com.nancy.shopbee.presentation.components.TextInput
import com.nancy.shopbee.presentation.screens.account.SettingsViewModel
import com.nancy.shopbee.presentation.screens.auth.socialmedia.rememberSocialAuthHandler
import com.nancy.shopbee.presentation.screens.auth.viewmodel.AuthViewModel

@Composable
fun SignInScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    authViewModel : AuthViewModel = hiltViewModel()
) {

    val socialAuthHandler = rememberSocialAuthHandler(navController, authViewModel, settingsViewModel)

    val email by authViewModel.email
    val password by authViewModel.password
    val isLoading by authViewModel.isLoading
    val errorMessage by authViewModel.errorMessage

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "ShopBee", color = MaterialTheme.colorScheme.primary, fontSize = 30.sp)

        Spacer(modifier = Modifier.height(20.dp))

        TextInput(
            onVal = { authViewModel.email.value = it },
            value = email,
            placeholder = "Email"
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextInput(
            onVal = { authViewModel.password.value = it },
            value = password,
            placeholder = "Password"
        )

        if (errorMessage != null) {
            Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(15.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            PrimaryButton(
                text = "Login",
                onClick = {
                    authViewModel.onSignIn {
                        settingsViewModel.setUserSignedIn(true)
                        navController.navigate(Screens.HomeScreen.name) {
                            popUpTo(Screens.LoginScreen.name) { inclusive = true }
                        }
                    }
                },
            )
        }

            Spacer(modifier = Modifier.height(15.dp))

            HorizontalDivider(
                modifier = Modifier
                    .width(300.dp)
                    .align(Alignment.CenterHorizontally),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
            )

            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SocialMediaBtn(name = "Google", onClick = {socialAuthHandler.onGoogleClick()})
           //     SocialMediaBtn(name = "Facebook", onClick = {})
                SocialMediaBtn(name = "Tel", onClick = {  navController.navigate(Screens.PhoneEntrScreen.route)})
            }
            Spacer(modifier = Modifier.height(10.dp))

            RowText(
                text1 = "Forgot your ",
                text2 = "Password?",
                onClickText2 = {}
            )
            Spacer(modifier = Modifier.height(10.dp))

            RowText(
                text1 = "New to ShopBee ",
                text2 = "Register",
                onClickText2 = { navController.navigate(Screens.RegScreen.name)}
            )

        }
    }


