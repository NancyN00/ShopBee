package com.nancy.shopbee.presentation.screens.home

import android.R
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nancy.shopbee.presentation.screens.home.categories.ProductCategoriesSection
import com.nancy.shopbee.presentation.screens.search.ShopBeeSearch
import com.nancy.shopbee.ui.theme.ShopBeeTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen( viewModel: ProductListViewModel = hiltViewModel()) {

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("ShopBee") },
//                colors = TopAppBarDefaults.mediumTopAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primary
//                )
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                ShopBeeSearch(Modifier)

                // Categories Chips


                Spacer(modifier = Modifier.height(8.dp))


                // Only show category chips
                ProductCategoriesSection(
                    viewModel = viewModel,
                    onCategorySelected = { selectedCategory ->
                        // For now, just log the clicked category or do nothing
                        Log.d("HomeScreen", "Selected category: $selectedCategory")
                    }
                )

//             before api:   val sampleCategories = listOf("Electronics", "Books", "Clothing", "Toys", "Groceries")
//
//              ProductCategoriesChips(
//                    categories = sampleCategories,
//                    onCategorySelected = { selected ->
//                        // Just for preview, no action needed
//                        println("Selected: $selected")
//                    }
//                )

                Spacer(modifier = Modifier.height(8.dp))

                val sampleProducts = listOf(
                    ProductItemDto(1, "Smartphone", "$299", android.R.drawable.ic_menu_camera),
                    ProductItemDto(2, "Laptop", "$799", android.R.drawable.ic_menu_gallery),
                    ProductItemDto(3, "Headphones", "$49", R.drawable.ic_menu_slideshow)
                )

                ProductList(products = sampleProducts) { product ->
                    // handle buy click for preview
                }




                // Products List
            }


        }
    )

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ShopBeeTheme {
        HomeScreen()
    }
}
