package com.nancy.shopbee.presentation.screens.home

import android.util.Log
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nancy.shopbee.navigation.Screens
import com.nancy.shopbee.presentation.screens.home.categories.ProductCategoriesSection
import com.nancy.shopbee.presentation.screens.home.productlist.ProductList
import com.nancy.shopbee.presentation.screens.search.ShopBeeSearch
import com.nancy.shopbee.ui.theme.ShopBeeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: ProductListViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ShopBee") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
            )
        },
        content = { paddingValues ->
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
            ) {
                ShopBeeSearch(Modifier)

                Spacer(modifier = Modifier.height(8.dp))

                // category chips
                ProductCategoriesSection(
                    viewModel = viewModel,
                    onCategorySelected = { selectedCategory ->
                        viewModel.fetchProductsByCategory(selectedCategory)

                        Log.d("HomeScreen", "Selected category: $selectedCategory")
                    },
                )

                Spacer(modifier = Modifier.height(8.dp))

                // product Grid
                val products by viewModel.products.collectAsState()

                ProductList(
                    modifier = Modifier.weight(1f),
                    products = products,
                    onCardClick = { product ->
                        // Save the productId before navigating
                        navController.navigate("${Screens.ProductDetailsScreen.name}/${product.id}")
                    },
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

                //   Spacer(modifier = Modifier.height(8.dp))

                // before api implementation
//                val sampleProducts = listOf(
//                    ProductItemDto(1, "Smartphone", "$299", R.drawable.ic_menu_camera),
//                    ProductItemDto(2, "Laptop", "$799", R.drawable.ic_menu_gallery),
//                    ProductItemDto(3, "Headphones", "$49", R.drawable.ic_menu_slideshow)
//                )
//
//                ProductList(products = sampleProducts) { product ->
//                    // handle buy click for preview
//                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ShopBeeTheme {
        val navController: NavHostController
        // HomeScreen(navController =  navController)
    }
}
