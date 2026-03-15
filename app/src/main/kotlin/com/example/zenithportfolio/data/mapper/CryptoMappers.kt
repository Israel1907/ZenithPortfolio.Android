package com.example.zenithportfolio.data.mapper

import com.example.zenithportfolio.data.local.CachedCryptoEntity
import com.example.zenithportfolio.data.remote.CryptoDto
import com.example.zenithportfolio.domain.model.Crypto

fun CryptoDto.toDomain() = Crypto(
    id = id,
    name = name,
    symbol = symbol,
    price = currentPrice,
    changePercent24h = priceChangePercentage24h ?: 0.0,
    imageUrl = imageUrl,
    marketCap = marketCap,
    rank = marketCapRank ?: 0
)

fun Crypto.toEntity() = CachedCryptoEntity(
    id = id,
    name = name,
    symbol = symbol,
    price = price,
    changePercent24h = changePercent24h,
    imageUrl = imageUrl,
    marketCap = marketCap,
    rank = rank
)

fun CachedCryptoEntity.toDomain() = Crypto(
    id = id,
    name = name,
    symbol = symbol,
    price = price,
    changePercent24h = changePercent24h,
    imageUrl = imageUrl,
    marketCap = marketCap,
    rank = rank
)
