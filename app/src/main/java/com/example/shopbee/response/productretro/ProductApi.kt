package com.example.shopbee.response.productretro

import com.example.shopbee.response.data.Product
import retrofit2.http.GET

//pass the methods
interface ProductApi {

    @GET("products")
    suspend fun getProducts(): List<Product>

}