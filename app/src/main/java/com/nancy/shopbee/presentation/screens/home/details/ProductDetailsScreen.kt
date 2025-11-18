package com.nancy.shopbee.presentation.screens.home.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nancy.shopbee.presentation.screens.home.ProductListViewModel

@Composable
fun ProductDetailsScreen(
    productId: Int,
    navController: NavHostController,
    viewModel: ProductListViewModel = hiltViewModel(),
) {
    // fetch product when screen opens
    LaunchedEffect(productId) {
        viewModel.fetchProductById(productId)
    }

    val product by viewModel.selectedProduct.collectAsState()

    product?.let { p ->
        ProductDetailsItemLayout(
            product = p,
            onBackClick = { navController.popBackStack() },
            onFavoriteClick = {
                //  add/remove from Room favorites later
            },
            onBuyClick = {
                // trigger MPESA/payment flow later
            },
        )
    } ?: run {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Loading...")
        }
    }
}
