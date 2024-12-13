package com.nancy.shopbee.presentation.onboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nancy.shopbee.R
import com.nancy.shopbee.presentation.components.PrimaryButton
import com.nancy.shopbee.ui.theme.Purple40
import com.nancy.shopbee.ui.theme.Purple80
import com.nancy.shopbee.ui.theme.ShopBeeTheme

@Composable
fun OnboardScreen(
    navigateToLogin: () -> Unit,
    navigateToSignUp: () -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .background(color = Color.White)
    ) {

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(R.drawable.shopbee),
                contentDescription = null,
                modifier = Modifier.height(350.dp)
                    .width(150.dp)
                    .background(color = Color.White)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Swipe through categories, and discover the hottest items in no time",
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "Welcome to ShopBee",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier,
                color = Purple40
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Choose an option and get started",
                textAlign = TextAlign.Center,
                color = Color.DarkGray,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(30.dp))

            PrimaryButton(
                modifier = Modifier,
                name = "LOGIN",
                onClick = navigateToLogin,
                backgroundColor = Purple80
            )

            Spacer(modifier = Modifier.height(20.dp))

            PrimaryButton(
                modifier = Modifier,
                name = "SIGNUP",
                onClick = navigateToSignUp,
                backgroundColor = Color(0xFFCC0000)
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardScreenPreview() {

    ShopBeeTheme {
        /** theme wrapper **/
        OnboardScreen(
            navigateToLogin = {  },
            navigateToSignUp = {  }
        )
    }
}