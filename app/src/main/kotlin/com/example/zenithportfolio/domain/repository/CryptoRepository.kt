package com.example.zenithportfolio.domain.repository

import com.example.zenithportfolio.domain.model.Crypto
import com.example.zenithportfolio.domain.model.CryptoResult

interface CryptoRepository {
    suspend fun getCryptos(): Result<CryptoResult>
    suspend fun getCryptoById(id: String): Result<Crypto>
    suspend fun getMarketChart(id: String, days: Int): Result<List<Double>>
}
