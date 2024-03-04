package com.example.shopbee.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopbee.response.data.Product
import com.example.shopbee.response.repository.GetProductRepository
import com.example.shopbee.util.ProductResource
import com.example.shopbee.util.ProductState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel (private val productRepository: GetProductRepository) :ViewModel() {
    private val _state = MutableStateFlow(ProductState())
    val state = _state.asStateFlow()
    init {
        getProducts()
    }
    private fun getProducts() {
              viewModelScope.launch {
                  productRepository.getProducts().collect{ result->
                      when(result){
                          is ProductResource.Loading ->{
                              _state.value = ProductState(
                                  isLoading = true
                              ) }
                          is ProductResource.Success ->{
                              _state.value = ProductState(
                                  products = result.data ?: emptyList()

                              )
                          }
                          is ProductResource.Error ->{
                              _state.value = ProductState(
                                  message = result.message ?: "Something went wrong"
                              )
                          }
                      }

                  }
              }
    }



}