package com.nancy.shopbee.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nancy.shopbee.domain.models.FavoriteProductEntity
import kotlinx.coroutines.flow.Flow

/*this is a bridge between app and db
* it manages the favorites */
@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorite(product: FavoriteProductEntity)

    @Delete
    suspend fun removeFromFavorite(product: FavoriteProductEntity)

    @Query("SELECT * FROM favorite_products")
    fun getFavoritesProducts(): Flow<List<FavoriteProductEntity>>  // <-- Flow instead of List

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_products WHERE id = :productId)")
    suspend fun isProductInFavorites(productId: Int): Boolean
}
