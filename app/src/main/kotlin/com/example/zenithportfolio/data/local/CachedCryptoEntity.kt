package com.example.zenithportfolio.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_cryptos")
data class CachedCryptoEntity(
    @PrimaryKey val id: String,
    val name: String,
    val symbol: String,
    val price: Double,
    val changePercent24h: Double,
    val imageUrl: String,
    val marketCap: Long,
    val rank: Int
)
