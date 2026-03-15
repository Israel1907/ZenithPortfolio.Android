package com.example.zenithportfolio.presentation.viewmodel

import com.example.zenithportfolio.domain.model.Crypto

data class CryptoListState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val cryptos: List<Crypto> = emptyList(),
    val filteredCryptos: List<Crypto> = emptyList(),
    val favorites: Set<String> = emptySet(),
    val searchQuery: String = "",
    val error: String? = null,
    val fromCache: Boolean = false
)
