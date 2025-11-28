package com.nancy.shopbee.data.repository

import com.nancy.shopbee.data.remote.ShopBeeApiService
import com.nancy.shopbee.domain.ShopBeeRepository
import com.nancy.shopbee.domain.models.Product
import javax.inject.Inject

class ShopBeeRepositoryImpl
@Inject
constructor(
    private val shopBeeApiService: ShopBeeApiService,
) : ShopBeeRepository {
    override suspend fun getCategories(): List<String> = shopBeeApiService.getCategories()

    override suspend fun getProductsByCategory(category: String): List<Product> =
        shopBeeApiService.getProductsByCategory(category)

    override suspend fun getProductById(productId: Int): Product =
        shopBeeApiService.getProductById(productId)
}
