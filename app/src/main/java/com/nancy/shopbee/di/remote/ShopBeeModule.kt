package com.nancy.shopbee.di.remote

import com.nancy.shopbee.data.remote.ShopBeeApiService
import com.nancy.shopbee.data.repository.ShopBeeRepositoryImpl
import com.nancy.shopbee.domain.repository.ShopBeeRepository
import com.nancy.shopbee.utils.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ShopBeeModule {
    @Singleton
    @Provides
    fun provideRetrofitApi(): ShopBeeApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ShopBeeApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideProduct(api: ShopBeeApiService): ShopBeeRepository {
        return ShopBeeRepositoryImpl(api)
    }
}
