package com.nancy.shopbee.presentation.screens.home.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nancy.shopbee.domain.models.ProductDetailsItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsItemLayout(
    product: ProductDetailsItem, // Use your ProductDetailsItem with isFavorite
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onBuyClick: () -> Unit,
    isFavorite: Boolean
) {
    Scaffold(
        topBar = {
            ProductDetailsTopBar(
                onBackClick = onBackClick,
                onFavoriteClick = onFavoriteClick,
                isFavorite = isFavorite
            )
        },
    ) { paddingValues ->
        ProductDetailsContent(
            product = product,
            paddingValues = paddingValues,
            onBuyClick = onBuyClick,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsTopBar(
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    isFavorite: Boolean
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier
                    .size(48.dp)  // Material 3 touch target size
            ) {
                Icon(
                    imageVector = if (isFavorite) {
                        Icons.Default.Favorite
                    } else {
                        Icons.Default.FavoriteBorder
                    },
                    contentDescription = if (isFavorite) {
                        "Remove from favorites"
                    } else {
                        "Add to favorites"
                    },
                    tint = if (isFavorite) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    modifier = Modifier.size(24.dp)
                )
            }

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    )
}

@Composable
fun ProductDetailsContent(
    product: ProductDetailsItem,
    paddingValues: PaddingValues,
    onBuyClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
    ) {
        ProductImage(product)
        ProductInfo(product)
        MpesaBuyButton(onBuyClick)
    }
}

@Composable
fun ProductImage(product: ProductDetailsItem) {
    AsyncImage(
        model = product.image,
        contentDescription = product.title,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop,
    )

    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun ProductInfo(product: ProductDetailsItem) {
    Text(
        text = product.title,
        style = MaterialTheme.typography.titleMedium,
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = "$${product.price}",
        style = MaterialTheme.typography.titleSmall,
        color = Color(0xFFFFA500),
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = product.description,
        style = MaterialTheme.typography.bodyMedium,
    )

    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
fun MpesaBuyButton(onBuyClick: () -> Unit) {
    Button(
        onClick = onBuyClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text("Buy", color = Color.White)
    }

    Spacer(modifier = Modifier.height(16.dp))
}
