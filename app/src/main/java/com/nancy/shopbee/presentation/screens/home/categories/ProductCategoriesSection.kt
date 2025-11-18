package com.nancy.shopbee.presentation.screens.home.categories

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.nancy.shopbee.presentation.screens.home.ProductListViewModel

@Composable
fun ProductCategoriesSection(
    viewModel: ProductListViewModel = hiltViewModel(),
    onCategorySelected: (String) -> Unit = {},
) {
    val categories by viewModel.categories.collectAsState()

    ProductCategoriesChips(
        categories = categories,
        onCategorySelected = onCategorySelected,
    )
}
