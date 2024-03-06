package com.example.shopbee.response.repository


import com.example.shopbee.response.data.Product
import com.example.shopbee.util.ProductResource
import kotlinx.coroutines.flow.Flow


interface GetProductRepository{
    suspend fun getProducts(): Flow<ProductResource<List<Product>>>
}
