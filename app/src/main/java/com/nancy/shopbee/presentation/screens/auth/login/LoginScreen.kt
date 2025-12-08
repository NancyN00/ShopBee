package com.nancy.shopbee.presentation.screens.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()

    var mail by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }


        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(text = "ShopBee",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 30.sp
                )

            Spacer(modifier = Modifier.height(20.dp))

            TextInput(
                onVal = { mail = it },
                value = mail,
                placeholder = "Email"
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextInput(
                onVal = { pass = it },
                value = pass,
                placeholder = "Password"
            )
            Spacer(modifier = Modifier.height(15.dp))

            PrimaryButton(
                text = "Login",
                onClick = {
                    scope.launch {
                        // On success:
                        // Save that user is signed in
                        viewModel.setUserSignedIn(true)
                        navController.navigate(Screens.HomeScreen.name)
                    }
                },
            )

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
                SocialMediaBtn(name = "Google", onClick = {})
                SocialMediaBtn(name = "Facebook", onClick = {})
                SocialMediaBtn(name = "Tel", onClick = {})
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


