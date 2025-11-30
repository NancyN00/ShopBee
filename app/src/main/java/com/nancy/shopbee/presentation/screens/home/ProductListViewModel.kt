package com.nancy.shopbee.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nancy.shopbee.domain.models.Product
import com.nancy.shopbee.domain.repository.ShopBeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        // categories state
        private val _categories = MutableStateFlow<List<String>>(emptyList())
        val categories: StateFlow<List<String>> = _categories.asStateFlow()

        // all products for current category
        private val _products = MutableStateFlow<List<Product>>(emptyList())
        val products: StateFlow<List<Product>> = _products.asStateFlow()

        // filtered products - updates when user searches
        private val _filteredProducts = MutableStateFlow<List<Product>>(emptyList())
        val filteredProducts: StateFlow<List<Product>> = _filteredProducts.asStateFlow()

        // search query from search bar
        private val _searchQuery = MutableStateFlow("")
        val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

        // selected product for details screen
        private val _selectedProduct = MutableStateFlow<Product?>(null)
        val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

        // snackbar error messages - auto-clears
        private val _errorMessage = MutableStateFlow<String?>(null)
        val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

        // loading spinner state during network calls
        private val _isLoading = MutableStateFlow(false)
        val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

        // persistent ERROR - survives navigation
        private val _hasError = MutableStateFlow(false)
        val hasError: StateFlow<Boolean> = _hasError.asStateFlow()

        init {
            fetchCategories()
        }

        private fun fetchCategories() {
            viewModelScope.launch {
                _isLoading.value = true

                try {
                    // get categories from repository (handles API + Room cache)
                    val categoryList = prodRepo.getCategories()
                    _categories.value = categoryList

                    // auto-load first category products
                    if (categoryList.isNotEmpty()) {
                        fetchProductsByCategory(categoryList[0])
                    } else {
                        _hasError.value = true // no categories = error state
                    }
                } catch (e: IOException) {
                    // no INTERNET - persistent error screen shown
                    Log.e("ProductListVM", "Network error fetching categories", e)
                    _errorMessage.value = "No internet connection. Please check your network."
                    _hasError.value = true
                } catch (e: HttpException) {
                    // server error
                    Log.e("ProductListVM", "HTTP error fetching categories", e)
                    _errorMessage.value = "Server error. Please try again later."
                    _hasError.value = true
                } catch (e: SerializationException) {
                    // JSON parsing error
                    Log.e("ProductListVM", "Data parsing error fetching categories", e)
                    _errorMessage.value = "Data error. Please try again later."
                    _hasError.value = true
                } finally {
                    _isLoading.value = false // hide spinner
                }
            }
        }

        fun fetchProductsByCategory(category: String) {
            viewModelScope.launch {
                _isLoading.value = true // show loading spinner
                _hasError.value = false // reset error - give fresh chance

                try {
                    //  net call  - Get fresh products
                    val list = prodRepo.getProductsByCategory(category)
                    _products.value = list // update products list
                    _searchQuery.value = "" // reset search
                    _filteredProducts.value = list // show all products
                    _hasError.value = false // success - error cleared
                } catch (e: IOException) {
                    // no net - WiFi error screen
                    Log.e("ProductListVM", "Network error fetching products", e)
                    _errorMessage.value = "No internet connection. Please check your network."
                    _hasError.value = true // show persistent error
                    _products.value = emptyList() // clear products
                    _filteredProducts.value = emptyList() // clear filtered
                } catch (e: HttpException) {
                    Log.e("ProductListVM", "HTTP error fetching products", e)
                    _errorMessage.value = "Server error. Please try again later."
                    _hasError.value = true
                } catch (e: SerializationException) {
                    // data error
                    Log.e("ProductListVM", "Data parsing error fetching products", e)
                    _errorMessage.value = "Data error. Please try again later."
                    _hasError.value = true
                } finally {
                    _isLoading.value = false // hide spinner
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

        // error handling
        fun clearError() {
            _errorMessage.value = null // Clear snackbar message only
            // hasError stays true - survives navigation throughout the app
        }

        // retry button + swipe refresh - calls main fetch function
        fun retryFetch() {
            categories.value.firstOrNull()?.let { category ->
                fetchProductsByCategory(category) // Resets error + tries again
            }
        }

        // filter search as user types
        fun onSearchQueryChange(query: String) {
            _searchQuery.value = query

            if (query.isBlank()) {
                // show all products when search is empty
                _filteredProducts.value = _products.value
            } else {
                // filter by title, description, OR category (case insensitive)
                val filtered =
                    _products.value.filter { product ->
                        product.title.contains(query, ignoreCase = true) ||
                            product.description.contains(query, ignoreCase = true) ||
                            product.category.contains(query, ignoreCase = true)
                    }
                _filteredProducts.value = filtered
            }
        }
    }
