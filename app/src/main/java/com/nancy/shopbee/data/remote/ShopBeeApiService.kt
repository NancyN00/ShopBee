package com.nancy.shopbee.data.remote

import retrofit2.http.GET

interface ShopBeeApiService {

    @GET("products/categories")
    suspend fun getCategories(): List<String>
}