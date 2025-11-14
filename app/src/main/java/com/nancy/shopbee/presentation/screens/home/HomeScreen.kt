package com.nancy.shopbee.presentation.screens.home

import android.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nancy.shopbee.presentation.screens.search.ShopBeeSearch
import com.nancy.shopbee.ui.theme.ShopBeeTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

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

                val sampleCategories = listOf("Electronics", "Books", "Clothing", "Toys", "Groceries")

                ProductCategoriesChips(
                    categories = sampleCategories,
                    onCategorySelected = { selected ->
                        // Just for preview, no action needed
                        println("Selected: $selected")
                    }
                )

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
