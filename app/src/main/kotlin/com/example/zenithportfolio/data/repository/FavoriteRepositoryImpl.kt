package com.example.zenithportfolio.data.repository

import com.example.zenithportfolio.data.local.FavoriteDao
import com.example.zenithportfolio.data.local.FavoriteEntity
import com.example.zenithportfolio.domain.repository.FavoriteRepository
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteRepository {

    override suspend fun getAllFavorites(): Set<String> = favoriteDao.getAll().toSet()

    override suspend fun addFavorite(cryptoId: String) {
        favoriteDao.insert(FavoriteEntity(cryptoId))
    }

    override suspend fun removeFavorite(cryptoId: String) {
        favoriteDao.delete(cryptoId)
    }
}
