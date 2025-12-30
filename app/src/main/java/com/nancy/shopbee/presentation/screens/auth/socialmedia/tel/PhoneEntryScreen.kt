package com.nancy.shopbee.presentation.screens.auth.socialmedia.tel

import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nancy.shopbee.navigation.Screens
import com.nancy.shopbee.presentation.components.PrimaryButton
import com.nancy.shopbee.presentation.components.TextInput
import com.nancy.shopbee.presentation.screens.auth.viewmodel.AuthViewModel

@SuppressLint("ContextCastToActivity")
@Composable
fun PhoneEntryScreen(
    navController : NavController,
    authViewModel : AuthViewModel = hiltViewModel()
) {

    var phoneNumber by remember { mutableStateOf("") }
    val context = LocalContext.current as Activity // Needed for Firebase Phone Auth


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Log in with your phone number",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "We will send a 6-digit code to verify your account.",
            textAlign = TextAlign.Center,
         //   color = Color.Gray
        )
        TextInput(
            onVal = { if (it.length <= 15) phoneNumber = it },
            value = phoneNumber,
            placeholder = "e.g. +254700000000"
        )

        Spacer(modifier = Modifier.height(24.dp))

        PrimaryButton(
            text = "Send Verification Code",
            onClick = {
                val formattedNumber = if (phoneNumber.startsWith("+")) {
                    phoneNumber
                } else if (phoneNumber.startsWith("0")) {
                    "+254${phoneNumber.substring(1)}"
                } else {
                    "+254$phoneNumber"
                }

                if (formattedNumber.length >= 12) { // +254 plus 9 digits
                    authViewModel.sendOtp(formattedNumber, context) {
                        navController.navigate(Screens.OtpVerScreen.route)
                    }
                } else {
                    Toast.makeText(context, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
                }
            }
        )


    }
}

@Preview(showBackground = true)
@Composable
fun PhoneNumberScreenPreview() {
   //PhoneEntryScreen()
}

