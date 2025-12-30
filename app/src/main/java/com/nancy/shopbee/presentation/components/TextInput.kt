package com.nancy.shopbee.presentation.components

import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    onVal : (String) -> Unit,
    value : String,
    placeholder : String
){

    val borderColor = MaterialTheme.colorScheme.primary.copy(.5f)

    OutlinedTextField(
        onValueChange = onVal,
        value = value,
        placeholder = {
            Text(text = placeholder)
        },
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = borderColor,
            unfocusedIndicatorColor = borderColor,
            cursorColor = borderColor,
            focusedPlaceholderColor = borderColor,
            unfocusedPlaceholderColor = borderColor,
        )

    )

}

