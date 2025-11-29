package com.nancy.shopbee.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nancy.shopbee.domain.models.Product
import com.nancy.shopbee.domain.repository.ShopBeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel
    @Inject
    constructor(
        private val prodRepo: ShopBeeRepository,
    ) : ViewModel() {
        // Categories state
        private val _categories = MutableStateFlow<List<String>>(emptyList())
        val categories: StateFlow<List<String>> = _categories

        // Product state
        private val _products = MutableStateFlow<List<Product>>(emptyList())
        val products: StateFlow<List<Product>> = _products

        // Details screen product state
        private val _selectedProduct = MutableStateFlow<Product?>(null)
        val selectedProduct: StateFlow<Product?> = _selectedProduct

        // Error messages state
        private val _errorMessage = MutableStateFlow<String?>(null)
        val errorMessage: StateFlow<String?> = _errorMessage

        // Loading state
        private val _isLoading = MutableStateFlow(false)
        val isLoading: StateFlow<Boolean> = _isLoading

        init {
            fetchCategories()
            fetchProductsByCategory("electronics")
        }

        private fun fetchCategories() {
            viewModelScope.launch {
                _isLoading.value = true
                try {
                    val categoryList = prodRepo.getCategories()
                    _categories.value = categoryList

                    if (categoryList.isNotEmpty()) {
                        fetchProductsByCategory(categoryList[0])
                    }
                } catch (e: IOException) {
                    Log.e("ProductListVM", "Network error fetching categories", e)
                    _errorMessage.value = "No internet connection. Please check your network."
                } catch (e: HttpException) {
                    Log.e("ProductListVM", "HTTP error fetching categories", e)
                    _errorMessage.value = "Server error. Please try again later."
                } catch (e: SerializationException) {
                    Log.e("ProductListVM", "Data parsing error fetching categories", e)
                    _errorMessage.value = "Data error. Please try again later."
                } finally {
                    _isLoading.value = false
                }
            }
        }

        fun fetchProductsByCategory(category: String) {
            viewModelScope.launch {
                _isLoading.value = true
                try {
                    _products.value = prodRepo.getProductsByCategory(category)
                } catch (e: IOException) {
                    Log.e("ProductListVM", "Network error fetching products", e)
                    _errorMessage.value = "No internet connection. Please check your network."
                } catch (e: HttpException) {
                    Log.e("ProductListVM", "HTTP error fetching products", e)
                    _errorMessage.value = "Server error. Please try again later."
                } catch (e: SerializationException) {
                    Log.e("ProductListVM", "Data parsing error fetching products", e)
                    _errorMessage.value = "Data error. Please try again later."
                } finally {
                    _isLoading.value = false
                }
            }
        }

        fun fetchProductById(productId: Int) {
            viewModelScope.launch {
                _isLoading.value = true
                try {
                    _selectedProduct.value = prodRepo.getProductById(productId)
                } catch (e: IOException) {
                    Log.e("ProductListVM", "Network error fetching product by ID", e)
                    _errorMessage.value = "No internet connection. Please check your network."
                } catch (e: HttpException) {
                    Log.e("ProductListVM", "HTTP error fetching product by ID", e)
                    _errorMessage.value = "Server error. Please try again later."
                } catch (e: SerializationException) {
                    Log.e("ProductListVM", "Data parsing error fetching product by ID", e)
                    _errorMessage.value = "Data error. Please try again later."
                } finally {
                    _isLoading.value = false
                }
            }
        }

        // Reset error after showing
        fun clearError() {
            _errorMessage.value = null
        }
    }
