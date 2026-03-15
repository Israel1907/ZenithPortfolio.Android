package com.example.zenithportfolio.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CachedCryptoEntity::class, FavoriteEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cachedCryptoDao(): CachedCryptoDao
    abstract fun favoriteDao(): FavoriteDao
}
