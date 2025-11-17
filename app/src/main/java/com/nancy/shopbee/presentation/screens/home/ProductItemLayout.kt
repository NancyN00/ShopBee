package com.nancy.shopbee.presentation.screens.home

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class ProductItemDto(
    val id : Int,
    val title : String,
    val price : String,
    val imageRes : Int

)


@Composable
fun ProductItemLayout(
    product: ProductItemDto,
    onBuyClick: (product: ProductItemDto) -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                )
                .padding(16.dp)
        ) {
            // Title and Price Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = product.title, fontSize = 16.sp)
                Text(text = product.price, fontSize = 16.sp, color = Color(0xFFFFA500))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Product Image
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Buy Button
            Button(
                onClick = { onBuyClick(product) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Buy", color = Color.White)
            }
        }
    }
}

@Composable
fun ProductList(
    products: List<ProductItemDto>,
    onBuyClick: (ProductItemDto) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ProductItemLayout (product = product, onBuyClick = onBuyClick)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ProductListPreview() {
    val sampleProducts = listOf(
        ProductItemDto(1, "Smartphone", "$299", android.R.drawable.ic_menu_camera),
        ProductItemDto(2, "Laptop", "$799", android.R.drawable.ic_menu_gallery),
        ProductItemDto(3, "Headphones", "$49", R.drawable.ic_menu_slideshow)
    )

    ProductList(products = sampleProducts) { product ->
        // handle buy click for preview
    }
}