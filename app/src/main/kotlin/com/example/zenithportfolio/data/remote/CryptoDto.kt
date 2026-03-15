package com.example.zenithportfolio.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CryptoDto(
    val id: String,
    val name: String,
    val symbol: String,
    @SerialName("current_price") val currentPrice: Double,
    @SerialName("price_change_percentage_24h") val priceChangePercentage24h: Double? = null,
    @SerialName("image") val imageUrl: String,
    @SerialName("market_cap") val marketCap: Long,
    @SerialName("market_cap_rank") val marketCapRank: Int? = null
)
