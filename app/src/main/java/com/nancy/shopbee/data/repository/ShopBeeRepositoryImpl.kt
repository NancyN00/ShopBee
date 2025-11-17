package com.nancy.shopbee.data.repository

import com.nancy.shopbee.data.remote.ShopBeeApiService
import com.nancy.shopbee.domain.ShopBeeRepository
import javax.inject.Inject

class ShopBeeRepositoryImpl @Inject constructor(
    private val shopBeeApiService: ShopBeeApiService
) : ShopBeeRepository {
    override suspend fun getCategories(): List<String> = shopBeeApiService.getCategories()
}