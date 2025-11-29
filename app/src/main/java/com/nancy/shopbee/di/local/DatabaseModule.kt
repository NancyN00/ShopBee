package com.nancy.shopbee.di.local

import android.content.Context
import androidx.room.Room
import com.nancy.shopbee.data.ProductFavDatabase
import com.nancy.shopbee.data.local.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): ProductFavDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ProductFavDatabase::class.java,
            "favorite_books_db",
        ).build()
    }

    @Provides
    fun provideFavoriteProdDao(database: ProductFavDatabase): ProductDao {
        return database.productDao()
    }
}
