package com.example.zenithportfolio.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {

    @Query("SELECT cryptoId FROM favorites")
    suspend fun getAll(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE cryptoId = :cryptoId")
    suspend fun delete(cryptoId: String)
}
