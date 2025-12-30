package com.nancy.shopbee.presentation.screens.auth.socialmedia.tel

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nancy.shopbee.navigation.Screens
import com.nancy.shopbee.presentation.components.PrimaryButton
import com.nancy.shopbee.presentation.components.TextInput
import com.nancy.shopbee.presentation.screens.auth.viewmodel.AuthViewModel

@Composable
fun OtpVerifyScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var otpCode by remember { mutableStateOf("") }
    val context = LocalContext.current

    // This listens for messages from the ViewModel
    LaunchedEffect(Unit) {
        authViewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Verify your number",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Enter the 6-digit code sent to your phone.",
            textAlign = TextAlign.Center,
            //color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        TextInput(
            onVal = { if (it.length <= 6) otpCode = it },
            value = otpCode,
            placeholder = "Enter 6-digit code"
        )

        Spacer(modifier = Modifier.height(24.dp))

        PrimaryButton(
            text = "Login",
            onClick = {
                if (otpCode.length == 6) {
                    authViewModel.verifyOtp(otpCode) {
                        navController.navigate(Screens.HomeScreen.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                } else {
                    Toast.makeText(context, "Please enter all 6 digits", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}
