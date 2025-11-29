package com.nancy.shopbee.data.repository

import com.nancy.shopbee.data.remote.ShopBeeApiService
import com.nancy.shopbee.domain.models.Product
import com.nancy.shopbee.domain.repository.ShopBeeRepository
import javax.inject.Inject

class ShopBeeRepositoryImpl
    @Inject
    constructor(
        private val api: ShopBeeApiService,
    ) : ShopBeeRepository {
        override suspend fun getCategories(): List<String> = api.getCategories()

        override suspend fun getProductsByCategory(category: String): List<Product> = api.getProductsByCategory(category)

        override suspend fun getProductById(productId: Int): Product = api.getProductById(productId)
    }
