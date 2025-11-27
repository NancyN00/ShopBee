package com.nancy.shopbee.presentation.screens.favorite

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.nancy.shopbee.navigation.Screens

@Composable
fun FavoriteScreen(
    favoriteProdViewModel: FavoriteProductsViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current
    val favoriteProducts by favoriteProdViewModel.favoriteProducts.collectAsStateWithLifecycle()

    // ✅ Add Toast collection for FavoriteScreen too
    LaunchedEffect(Unit) {
        favoriteProdViewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = "Favorite Products",
            fontWeight = FontWeight.SemiBold,
            fontSize = 25.sp
        )

        if (favoriteProducts.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No favorite product found", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(favoriteProducts) { prod ->
                    FavItemLayout(
                        product = prod,
                        onClick = {
                            navController.navigate("${Screens.ProductDetailsScreen.name}/${prod.id}")
                        }
                    )
                }
            }
        }
    }
}

