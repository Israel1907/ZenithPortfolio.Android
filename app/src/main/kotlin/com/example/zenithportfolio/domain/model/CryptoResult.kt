package com.example.zenithportfolio.domain.model

data class CryptoResult(
    val cryptos: List<Crypto>,
    val fromCache: Boolean
)
