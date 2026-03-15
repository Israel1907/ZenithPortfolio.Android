package com.example.zenithportfolio.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class MarketChartDto(
    val prices: List<List<Double>>
)
