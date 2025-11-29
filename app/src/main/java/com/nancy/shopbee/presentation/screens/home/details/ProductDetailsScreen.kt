package com.nancy.shopbee.presentation.screens.home.details

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nancy.shopbee.domain.models.ProductDetailsEntity
import com.nancy.shopbee.presentation.screens.favorite.FavProdViewModel
import com.nancy.shopbee.presentation.screens.home.ProductListViewModel

@Composable
fun ProductDetailsScreen(
    productId: Int,
    navController: NavHostController,
    viewModel: ProductListViewModel = hiltViewModel(),
    favoriteProdViewModel: FavProdViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    // ✅ FIXED: Collect SharedFlow properly with LaunchedEffect
    LaunchedEffect(Unit) {
        favoriteProdViewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(productId) {
        viewModel.fetchProductById(productId)
        favoriteProdViewModel.checkIfProductIsFavorite(productId)
    }

    val product by viewModel.selectedProduct.collectAsState()
    val isFavorite by favoriteProdViewModel.isFavorite.collectAsState()

    product?.let { p ->
        val productDetails =
            ProductDetailsEntity(
                id = p.id,
                title = p.title,
                price = p.price,
                description = p.description,
                category = p.category,
                image = p.image,
                rate = p.rating.rate,
                count = p.rating.count,
            )

        ProductDetailsItemLayout(
            product = productDetails,
            onBackClick = { navController.popBackStack() },
            onFavoriteClick = {
                favoriteProdViewModel.toggleFavorite(productDetails.toProductEntity())
            },
            onBuyClick = { /* Trigger MPESA/payment */ },
            isFavorite = isFavorite,
        )
    } ?: run {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                "Loading product details...",
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
