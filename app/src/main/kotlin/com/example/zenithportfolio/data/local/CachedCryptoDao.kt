package com.example.zenithportfolio.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface CachedCryptoDao {

    @Query("SELECT * FROM cached_cryptos ORDER BY rank ASC")
    suspend fun getAll(): List<CachedCryptoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cryptos: List<CachedCryptoEntity>)

    @Query("DELETE FROM cached_cryptos")
    suspend fun deleteAll()

    @Transaction
    suspend fun replaceAll(cryptos: List<CachedCryptoEntity>) {
        deleteAll()
        insertAll(cryptos)
    }
}
