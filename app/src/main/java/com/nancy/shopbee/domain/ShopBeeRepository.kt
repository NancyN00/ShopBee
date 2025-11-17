package com.nancy.shopbee.domain

interface ShopBeeRepository {

    suspend fun getCategories(): List<String>

}