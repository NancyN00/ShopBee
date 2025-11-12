package com.nancy.shopbee.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class OnboardingButtonUi(
    val backgroundColor: Color,
    val textColor: Color,
    val textStyle: TextStyle,
    val fontSize: Int,
)

@Composable
fun defaultButtonStyle() =
    OnboardingButtonUi(
        backgroundColor = MaterialTheme.colorScheme.primary,
        textColor = MaterialTheme.colorScheme.onPrimary,
        textStyle = MaterialTheme.typography.titleMedium,
        fontSize = 14,
    )

@Composable
fun OnboardingButtonUi(
    text: String,
    style: OnboardingButtonUi = defaultButtonStyle(),
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = style.backgroundColor,
                contentColor = style.textColor,
            ),
        shape = RoundedCornerShape(10.dp),
    ) {
        Text(
            text = text,
            fontSize = style.fontSize.sp,
            style = style.textStyle,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonUiPreview() {
    OnboardingButtonUi(text = "Next", onClick = {})
}

@Preview(showBackground = true)
@Composable
fun ButtonUiPreview1() {
    OnboardingButtonUi(
        text = "Back",
        style =
            OnboardingButtonUi(
                backgroundColor = Color.Transparent,
                textColor = Color.Gray,
                textStyle = MaterialTheme.typography.bodySmall,
                fontSize = 10,
            ),
        onClick = {},
    )
}
