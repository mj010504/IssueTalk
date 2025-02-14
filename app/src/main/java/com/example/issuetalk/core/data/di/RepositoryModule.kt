package com.example.issuetalk.core.data.di

import com.example.issuetalk.core.data.repository.auth.AuthRepositoryImpl
import com.example.issuetalk.core.domain.repository.auth.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindsAuthRepository(
        authRepositoryImpl : AuthRepositoryImpl
    ) : AuthRepository

}