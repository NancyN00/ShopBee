package com.nancy.shopbee.data.remote

import com.nancy.shopbee.domain.models.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface ShopBeeApiService {
    // fetch all categories
    @GET("products/categories")
    suspend fun getCategories(): List<String>

    // fetch products by category
    @GET("products/category/{categoryName}")
    suspend fun getProductsByCategory(
        @Path("categoryName") categoryName: String,
    ): List<Product>

    // fetch details of products by id
    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") productId: Int,
    ): Product
}
