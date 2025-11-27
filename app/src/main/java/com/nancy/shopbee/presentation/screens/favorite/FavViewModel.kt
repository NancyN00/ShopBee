package com.nancy.shopbee.presentation.screens.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nancy.shopbee.domain.models.FavoriteProductEntity
import com.nancy.shopbee.domain.repository.FavoriteProductRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException


@HiltViewModel
class FavoriteProductsViewModel @Inject constructor(
    private val repository: FavoriteProductRepo
) : ViewModel() {

    // Collect flow as StateFlow exposed immutable
    val favoriteProducts: StateFlow<List<FavoriteProductEntity>> = repository
        .getFavoriteProduct()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    //toast state
    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    fun checkIfProductIsFavorite(prodId: Int) {
        viewModelScope.launch {
            _isFavorite.value = repository.isProductInFav(prodId)
        }
    }

    fun toggleFavorite(product: FavoriteProductEntity) {
        viewModelScope.launch {
            try {
                val isFav = repository.isProductInFav(product.id)
                if (isFav) {
                    repository.removeFromFavorite(product)
                    _toastMessage.emit("Removed from favorites")
                } else {
                    repository.addToFavorite(product)
                    _toastMessage.emit("Added to favorites")
                }
                _isFavorite.value = !isFav
            } catch (e: CancellationException) {
                // Safe to ignore
            } catch (e: Exception) {
                Log.e("FavoriteViewModel", "Error toggling favorite", e)
                _toastMessage.emit("Error toggling favorite")
            }
        }
    }
}
