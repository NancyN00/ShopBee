package com.nancy.shopbee.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.nancy.shopbee.domain.ShopBeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val prodRepo: ShopBeeRepository

) : ViewModel(){

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                _categories.value = prodRepo.getCategories()
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching categories", e)
            }
        }
    }

}