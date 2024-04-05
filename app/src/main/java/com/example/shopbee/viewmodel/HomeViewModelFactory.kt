package com.example.shopbee.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shopbee.response.repository.GetProductRepository

class HomeViewModelFactory(val productRepository: GetProductRepository)
    : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(productRepository) as T
    }
}
