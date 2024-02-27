package com.example.shopbee.util

sealed class ProductResource<T>(val data: T? = null, val message: String? = null){

    class Success<T>(data: T) : ProductResource<T>(data)

    class Error<T>(message: String, data: T? = null): ProductResource<T>(data, message)

    class Loading<T>(data: T? = null): ProductResource<T>(data)
}
