package com.nancy.shopbee.presentation.screens.home.details

import com.nancy.shopbee.presentation.screens.home.ProductListViewModel
import com.nancy.shopbee.presentation.screens.home.details.mpesa.MpesaPaymentDialog
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nancy.shopbee.domain.models.ProductDetailsEntity
import com.nancy.shopbee.presentation.screens.favorite.FavProdViewModel

@Composable
fun ProductDetailsScreen(
    productId: Int,
    navController: NavHostController,
    viewModel: ProductListViewModel = hiltViewModel(),
    favoriteProdViewModel: FavProdViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    var showPayDialog by remember { mutableStateOf(false) }

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
        val productDetails = ProductDetailsEntity(
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
            onBuyClick = { showPayDialog = true },
            isFavorite = isFavorite,
        )

        // M-Pesa payment dialog
        if (showPayDialog) {
            MpesaPaymentDialog(
                productName = productDetails.title,
                amount = productDetails.price,
                onDismiss = { showPayDialog = false }
            )
        }

    } ?: run {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                "Loading product details...",
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

