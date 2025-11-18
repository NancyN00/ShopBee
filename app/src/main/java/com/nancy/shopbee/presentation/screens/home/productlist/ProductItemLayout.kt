package com.nancy.shopbee.presentation.screens.home.productlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nancy.shopbee.domain.models.Product

@Composable
fun ProductItemLayout(
    product: Product,
    onCardClick: (Product) -> Unit,
) {
    Card(
        modifier =
            Modifier.fillMaxWidth()
                .clickable { onCardClick(product) },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .background(
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                    .padding(16.dp),
        ) {
            Text(
                text = product.title,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // product Image from URL
            AsyncImage(
                model = product.image,
                contentDescription = product.title,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "$${product.price}", fontSize = 16.sp, color = Color(0xFFFFA500))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "⭐ ${product.rating.rate}", fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "(${product.rating.count})", fontSize = 12.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// before API intergration
// data class ProductItemDto(
//    val id : Int,
//    val title : String,
//    val price : String,
//    val imageRes : Int
//
// )

// Before api implementation
// @Preview(showBackground = true)
// @Composable
// fun ProductListPreview() {
//    val sampleProducts = listOf(
//        ProductItemDto(1, "Smartphone", "$299", R.drawable.ic_menu_camera),
//        ProductItemDto(2, "Laptop", "$799", R.drawable.ic_menu_gallery),
//        ProductItemDto(3, "Headphones", "$49", R.drawable.ic_menu_slideshow)
//    )
//
//    ProductList(products = sampleProducts) { product ->
//        // handle buy click for preview
//    }
// }
