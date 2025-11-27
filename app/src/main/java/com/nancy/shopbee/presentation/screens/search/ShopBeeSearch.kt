package com.nancy.shopbee.presentation.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nancy.shopbee.ui.theme.ShopBeeTheme

@Composable
fun ShopBeeSearch(
     modifier: Modifier,
    onSearchClick: () -> Unit = {},
) {
    var searchText by remember { mutableStateOf("") }

    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        placeholder = { Text("What are you looking for?", fontSize = 14.sp) },
        singleLine = true,
        modifier =
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(color = MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(12.dp),
        trailingIcon = {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        },
        colors =
            TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
    )
}

@Preview(showBackground = true)
@Composable
fun ShopBeeSearchPreview() {
    ShopBeeTheme {
        ShopBeeSearch(
            modifier =
                Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
        )
    }
}
