package com.nancy.shopbee.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nancy.shopbee.domain.repository.ShopBeeRepository
import com.nancy.shopbee.domain.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
    val categories: StateFlow<List<String>> = _categories

    // product state
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    // details screen product state
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct

    init {
        fetchCategories()
        fetchProductsByCategory("electronics")
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val categoryList = prodRepo.getCategories()
                _categories.value = categoryList

                if (categoryList.isNotEmpty()) {
                    fetchProductsByCategory(categoryList[0])
                }
            } catch (e: IOException) {
                Log.e("ProductListVM", "Network error fetching categories", e)
            } catch (e: HttpException) {
                Log.e("ProductListVM", "HTTP error fetching categories", e)
            } catch (e: Exception) {
                Log.e("ProductListVM", "Unexpected error fetching categories", e)
            }
        }
    }

    fun fetchProductsByCategory(category: String) {
        viewModelScope.launch {
            try {
                _products.value = prodRepo.getProductsByCategory(category)
            } catch (e: IOException) {
                Log.e("ProductListVM", "Network error fetching products", e)
            } catch (e: HttpException) {
                Log.e("ProductListVM", "HTTP error fetching products", e)
            } catch (e: Exception) {
                Log.e("ProductListVM", "Unexpected error fetching products", e)
            }
        }
    }

    fun fetchProductById(productId: Int) {
        viewModelScope.launch {
            try {
                _selectedProduct.value = prodRepo.getProductById(productId)
            } catch (e: IOException) {
                Log.e("ProductListVM", "Network error fetching product by ID", e)
            } catch (e: HttpException) {
                Log.e("ProductListVM", "HTTP error fetching product by ID", e)
            } catch (e: Exception) {
                Log.e("ProductListVM", "Unexpected error fetching product by ID", e)
            }
        }
    }
}
