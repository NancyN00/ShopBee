package com.nancy.shopbee.presentation.onboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nancy.shopbee.navigation.Screens

@Composable
fun OnboardScreen(
    navigateToLogin: () -> Unit,
    navigateToSignUp: () -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {

        Text(text = "Welcome to ShopBee!", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navigateToLogin() }) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navigateToSignUp() }) {
            Text("Sign Up")
        }
    }
}