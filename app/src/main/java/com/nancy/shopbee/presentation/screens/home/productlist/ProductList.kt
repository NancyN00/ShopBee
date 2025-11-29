package com.nancy.shopbee.presentation.screens.home.productlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
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

@Composable
fun ProductList(
    modifier: Modifier = Modifier,
    products: List<Product> = emptyList(),
    onCardClick: (Product) -> Unit = {},
    viewModel: ProductListViewModel = hiltViewModel(),
) {
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Show Snackbar when error occurs
    LaunchedEffect(errorMessage) {
        errorMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { padding ->

        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.categories.value.firstOrNull()?.let { category ->
                    viewModel.fetchProductsByCategory(category)
                }
            },
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = modifier.fillMaxSize(),
            ) {
                when {
                    isLoading -> {
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

                    products.isNotEmpty() -> {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 128.dp),
                            contentPadding = padding,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            items(products) { product ->
                                ProductItemLayout(
                                    product = product,
                                    onCardClick = onCardClick,
                                )
                            }
                        }
                    }

                    else -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                "No products available",
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                        }
                    }
                }
            }
        }
    }
}
