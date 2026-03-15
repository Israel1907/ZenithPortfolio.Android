package com.example.zenithportfolio.di

import com.example.zenithportfolio.data.repository.CryptoRepositoryImpl
import com.example.zenithportfolio.data.repository.FavoriteRepositoryImpl
import com.example.zenithportfolio.domain.repository.CryptoRepository
import com.example.zenithportfolio.domain.repository.FavoriteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCryptoRepository(impl: CryptoRepositoryImpl): CryptoRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository
}
