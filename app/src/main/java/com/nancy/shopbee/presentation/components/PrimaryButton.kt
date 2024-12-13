package com.nancy.shopbee.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrimaryButton(
    modifier: Modifier,
    name: String,
    onClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.primary // Default to theme primary color
) {

    Button(
        onClick = { onClick() },
        modifier = modifier.fillMaxWidth().height(45.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)

    ) {
        Text(
            text = name,
            modifier = modifier,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
            )

    }

}
