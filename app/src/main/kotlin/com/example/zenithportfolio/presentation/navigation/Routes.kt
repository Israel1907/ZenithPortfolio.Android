package com.example.zenithportfolio.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object SplashRoute

@Serializable
data object CryptoListRoute

@Serializable
data class CryptoDetailRoute(val cryptoId: String)
