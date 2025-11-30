package com.nancy.shopbee.presentation.screens.home.productlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nancy.shopbee.domain.models.Product
import com.nancy.shopbee.presentation.screens.home.ProductListViewModel

@Suppress("UNUSED_PARAMETER")
@Composable
fun ProductList(
    modifier: Modifier = Modifier,
    products: List<Product> = emptyList(),
    onCardClick: (Product) -> Unit = {},
    viewModel: ProductListViewModel = hiltViewModel(),
) {
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredProducts by viewModel.filteredProducts.collectAsState()
    val hasError by viewModel.hasError.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearError()
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.categories.value.firstOrNull()?.let {
                    viewModel.fetchProductsByCategory(it)
                }
            },
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(modifier = modifier.fillMaxSize()) {
                when {
                    isLoading -> LoadingState()
                    filteredProducts.isNotEmpty() ->
                        ProductGrid(
                            products = filteredProducts,
                            onCardClick = onCardClick,
                            padding = padding,
                        )
                    searchQuery.isNotBlank() && filteredProducts.isEmpty() -> EmptySearchState(searchQuery)
                    !isLoading && filteredProducts.isEmpty() && hasError -> PersistentEmptyState(viewModel)
                }
            }
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(8.dp))
            Text("Loading products...", color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
private fun ProductGrid(
    products: List<Product>,
    onCardClick: (Product) -> Unit,
    padding: PaddingValues,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        contentPadding = padding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(products) { product ->
            ProductItemLayout(product = product, onCardClick = onCardClick)
        }
    }
}

@Composable
private fun EmptySearchState(searchQuery: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text("No products found for '$searchQuery'")
    }
}

@Composable
private fun PersistentEmptyState(viewModel: ProductListViewModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp),
        ) {
            Icon(
                imageVector = Icons.Default.WifiOff,
                contentDescription = "No internet",
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "No products available",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Please check your internet connection",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = { viewModel.retryFetch() }) {
                Text("Retry")
            }
        }
    }
}
