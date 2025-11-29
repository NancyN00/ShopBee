package com.nancy.shopbee.domain.repository

import com.nancy.shopbee.data.local.ProductDao
import com.nancy.shopbee.domain.models.FavoriteProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteProductRepo
    @Inject
    constructor(private val productDao: ProductDao) {
        suspend fun addToFavorite(prod: FavoriteProductEntity) {
            productDao.addToFavorite(prod)
        }

        suspend fun removeFromFavorite(prod: FavoriteProductEntity) {
            productDao.removeFromFavorite(prod)
        }

        // Expose Flow directly, no suspend here
        fun getFavoriteProduct(): Flow<List<FavoriteProductEntity>> {
            return productDao.getFavoritesProducts()
        }

        suspend fun isProductInFav(prodId: Int): Boolean {
            return productDao.isProductInFavorites(prodId)
        }
    }
