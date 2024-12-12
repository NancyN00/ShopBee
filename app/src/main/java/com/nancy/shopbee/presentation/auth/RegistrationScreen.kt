package com.nancy.shopbee.presentation.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nancy.shopbee.navigation.Screens

@Composable
fun RegistrationScreen(
    navController: NavHostController,
    navigateToSignIn: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {


        Text(
            text = "Registration Screen",
            fontWeight = FontWeight.SemiBold,
            fontSize = 25.sp
        )

        Text(text = "Have an acc, Signin",
            modifier =  Modifier.clickable {
                navController.navigate(Screens.LoginScreen.name)
            }
        )

        Button(onClick = { navigateToSignIn() }) {
            Text("Signup")
        }
    }
}