package com.example.shopbee.response.repository

import com.example.shopbee.response.data.Product
import com.example.shopbee.response.productretro.ProductApi
import com.example.shopbee.util.ProductResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


class GetRepositoryImplRepository(private val api: ProductApi) : GetProductRepository{

    override suspend fun getProducts(): Flow<ProductResource<List<Product>>> = flow{
        try {
            emit(ProductResource.Loading(data = null))

            emit(ProductResource.Success(data = api.getProducts()))


        } catch (e: HttpException) {
            emit(ProductResource.Error(message = e.localizedMessage ?: "Something went wrong"))

        } catch (e: IOException) {
            emit(ProductResource.Error(message = "network not connected"))
        }
    }
}