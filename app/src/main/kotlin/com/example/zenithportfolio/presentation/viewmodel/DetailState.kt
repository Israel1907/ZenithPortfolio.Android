package com.example.zenithportfolio.presentation.viewmodel

import com.example.zenithportfolio.domain.model.Crypto

data class DetailState(
    val isLoading: Boolean = false,
    val crypto: Crypto? = null,
    val priceHistory: List<Double> = emptyList(),
    val selectedDays: Int = 7,
    val isChartLoading: Boolean = false,
    val chartError: String? = null,
    val error: String? = null
)
