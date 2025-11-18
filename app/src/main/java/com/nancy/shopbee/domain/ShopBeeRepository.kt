package com.nancy.shopbee.domain

import com.nancy.shopbee.domain.models.Product

interface ShopBeeRepository {
    suspend fun getCategories(): List<String>

    suspend fun getProductsByCategory(category: String): List<Product>

    suspend fun getProductById(productId: Int): Product
}
