package com.nancy.shopbee.presentation.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun RegistrationScreen() {

    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "This is Registration Screen",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold

        )

    }

}