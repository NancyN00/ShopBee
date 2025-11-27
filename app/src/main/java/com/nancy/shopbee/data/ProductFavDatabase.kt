package com.nancy.shopbee.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nancy.shopbee.data.local.ProductDao
import com.nancy.shopbee.domain.models.FavoriteProductEntity

@Database(entities = [FavoriteProductEntity::class], version = 1)
abstract class ProductFavDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
