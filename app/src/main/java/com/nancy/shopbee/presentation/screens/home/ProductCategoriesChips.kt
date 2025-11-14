package com.nancy.shopbee.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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

@Composable
fun ProductCategoriesChips(
    categories: List<String>,
    onCategorySelected: (String) -> Unit
) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            val isSelected = category == selectedCategory

            Box(
                modifier = Modifier
                    .background(
                        color = if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable {
                        selectedCategory = category
                        onCategorySelected(category)
                    }
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Text(
                    text = category,
                    fontSize = 14.sp,
                    color = if(isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground
                )


            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun ProductCategoriesChipPreview() {
    val sampleCategories = listOf("Electronics", "Books", "Clothing", "Toys", "Groceries")

    ProductCategoriesChips(
        categories = sampleCategories,
        onCategorySelected = { selected ->
            // Just for preview, no action needed
            println("Selected: $selected")
        }
    )
}