package com.example.zenithportfolio.presentation.viewmodel

sealed class CryptoListIntent {
    data object LoadCryptos : CryptoListIntent()
    data object Refresh : CryptoListIntent()
    data class Search(val query: String) : CryptoListIntent()
    data class ToggleFavorite(val cryptoId: String) : CryptoListIntent()
    data class SelectCrypto(val cryptoId: String) : CryptoListIntent()
}
