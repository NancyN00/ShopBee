package com.nancy.shopbee.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp


@Composable
fun RowText(
    modifier: Modifier = Modifier,
    text1 : String,
    text2: String,
    onClickText2 : () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ){
        Text(text = text1)
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = text2,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { onClickText2() },
            textDecoration = TextDecoration.Underline
        )
    }
}
