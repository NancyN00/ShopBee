package com.nancy.shopbee.di.remote

import com.nancy.shopbee.BuildConfig
import com.nancy.shopbee.data.remote.MpesaApiService
import com.nancy.shopbee.data.remote.MpesaConfig
import com.nancy.shopbee.data.repository.MpesaRepositoryImpl
import com.nancy.shopbee.domain.repository.MpesaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MpesaModule {

    @Provides
    @Singleton
    @Named("mpesa")
    fun provideMpesaRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(MpesaConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMpesaApiService(@Named("mpesa") retrofit: Retrofit): MpesaApiService {
        return retrofit.create(MpesaApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMpesaRepository(
        mpesaApiService: MpesaApiService
    ): MpesaRepository {
        return MpesaRepositoryImpl(
            mpesaApiService = mpesaApiService,
            consumerKey = BuildConfig.MPESA_CONSUMER_KEY,
            consumerSecret = BuildConfig.MPESA_CONSUMER_SECRET,
            callbackUrl = BuildConfig.MPESA_CALLBACK_URL,
            shortCode = BuildConfig.MPESA_SHORT_CODE,
            passKey = BuildConfig.MPESA_PASSKEY

        )
    }
}
