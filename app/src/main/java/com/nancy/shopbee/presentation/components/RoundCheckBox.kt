package com.nancy.shopbee.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun RoundCheckBox(
    modifier: Modifier = Modifier,
    title: String,
    isChecked: Boolean,
    onCheckChange: (Boolean) -> Unit,
)  {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckChange,
            modifier = modifier,
            colors =
                CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.onBackground,
                    checkmarkColor = MaterialTheme.colorScheme.background,
                ),
        )
        Text(text = title)
    }
}
