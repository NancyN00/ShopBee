package com.nancy.shopbee.di.remote

import com.google.firebase.auth.FirebaseAuth
import com.nancy.shopbee.data.repository.AuthRepositoryImpl
import com.nancy.shopbee.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

}