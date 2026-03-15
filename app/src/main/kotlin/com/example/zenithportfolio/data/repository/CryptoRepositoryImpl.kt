package com.example.zenithportfolio.data.repository

import com.example.zenithportfolio.data.local.CachedCryptoDao
import com.example.zenithportfolio.data.mapper.toDomain
import com.example.zenithportfolio.data.mapper.toEntity
import com.example.zenithportfolio.data.remote.CoinGeckoApi
import com.example.zenithportfolio.domain.model.Crypto
import com.example.zenithportfolio.domain.model.CryptoResult
import com.example.zenithportfolio.domain.repository.CryptoRepository
import javax.inject.Inject

class CryptoRepositoryImpl @Inject constructor(
    private val api: CoinGeckoApi,
    private val cachedCryptoDao: CachedCryptoDao
) : CryptoRepository {

    override suspend fun getCryptos(): Result<CryptoResult> {
        return try {
            val response = api.getMarkets()
            val cryptos = response.map { it.toDomain() }
            cachedCryptoDao.replaceAll(cryptos.map { it.toEntity() })
            Result.success(CryptoResult(cryptos, fromCache = false))
        } catch (e: Exception) {
            val cached = cachedCryptoDao.getAll().map { it.toDomain() }
            if (cached.isNotEmpty()) {
                Result.success(CryptoResult(cached, fromCache = true))
            } else {
                Result.failure(e)
            }
        }
    }

    override suspend fun getCryptoById(id: String): Result<Crypto> {
        return try {
            Result.success(api.getCoinById(id).toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMarketChart(id: String, days: Int): Result<List<Double>> {
        return try {
            val chart = api.getMarketChart(id = id, days = days)
            Result.success(chart.prices.map { it[1] })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
