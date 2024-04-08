package com.example.shopbee.response.repository


import com.example.shopbee.response.data.Product
import com.example.shopbee.util.ProductResource
import kotlinx.coroutines.flow.Flow

//this retrieve a list of products asynchronously using kotlin flows and coroutine.

interface GetProductRepository{
    suspend fun getProducts(): Flow<ProductResource<List<Product>>>
}
