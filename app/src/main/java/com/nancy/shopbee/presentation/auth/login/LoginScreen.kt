package com.nancy.shopbee.presentation.auth.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nancy.shopbee.presentation.auth.AuthViewModel
import com.nancy.shopbee.presentation.auth.Resource
import com.nancy.shopbee.presentation.components.PasswordInput
import com.nancy.shopbee.presentation.components.PrimaryButton
import com.nancy.shopbee.presentation.components.RoundCheckBox
import com.nancy.shopbee.presentation.components.RowText
import com.nancy.shopbee.presentation.components.TextInput
import com.nancy.shopbee.ui.theme.Purple40
import com.nancy.shopbee.ui.theme.Purple80

@Composable
fun LoginScreen(
    navigateToHome: () -> Unit,
    navigateToSignUp: () -> Unit,
    viewModel: AuthViewModel?,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val loginFlow = viewModel?.loginFlow?.collectAsState()

    Column(
        modifier =
            Modifier.fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "ShopBee",
            fontSize = 50.sp,
            color = Color.Red,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 100.dp),
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Login to continue",
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 125.dp),
        )

        Spacer(modifier = Modifier.height(25.dp))

        TextInput(
            placeholder = "Email",
            value = email,
            onValueChange = { email = it },
        )

        Spacer(modifier = Modifier.height(15.dp))

        PasswordInput(
            value = password,
            onValueChange = { password = it },
            placeholder = "Password",
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RoundCheckBox(
                title = "Remember me",
                isChecked = isChecked,
                onCheckChange = { isChecked = it },
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Forgot password?",
                color = Purple40,
                modifier =
                    Modifier.clickable {
                        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show()
                    },
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        PrimaryButton(
            modifier = Modifier,
            name = "LOGIN",
            onClick = {
                viewModel?.login(email, password)
            },
            backgroundColor = Purple80,
        )

        Spacer(modifier = Modifier.height(20.dp))

        RowText(
            modifier = Modifier,
            text1 = "Not a registered user?",
            text2 = "Signup now",
            onClickText2 = { navigateToSignUp() },
        )

        loginFlow?.value?.let {
            when (it) {
                is Resource.Failure -> {
                    val context = LocalContext.current
                    Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
                }

                Resource.Loading -> {
                    CircularProgressIndicator()
                }

                is Resource.Success -> {
                    // navigate to diff screen time using flow, it might do again and again, so, use of launched effect.

                    LaunchedEffect(Unit) {
                        navigateToHome()
                    }
                }
            }
        }
    }
}
