package com.nancy.shopbee.presentation.screens.onboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nancy.shopbee.R
import com.nancy.shopbee.presentation.components.PrimaryButton
import com.nancy.shopbee.ui.theme.ShopBeeTheme

@Composable
fun OnboardingStart() {

    Box (
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.onboard_auth),
            contentDescription = "Background Image",
            contentScale = ContentScale.FillBounds, 
            modifier = Modifier.matchParentSize()
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PrimaryButton(
                text = "Register",
                onClick = {}
            )

            Spacer(modifier = Modifier.height(10.dp))

            PrimaryButton(
                text = "Login",
                onClick = {})

        }

    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun OnboardingStartPreview(){
    ShopBeeTheme {
        OnboardingStart()
    }

}