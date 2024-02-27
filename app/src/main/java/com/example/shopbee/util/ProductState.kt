package com.example.shopbee.util

import com.example.shopbee.response.data.Product

data class ProductState(

    val isLoading:Boolean = false,
    val products:List<Product> = emptyList(),
    val message:String =""
)
