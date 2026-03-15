package com.example.zenithportfolio.presentation.viewmodel

sealed class CryptoListEffect {
    data class ShowError(val message: String) : CryptoListEffect()
    data class NavigateToDetail(val cryptoId: String) : CryptoListEffect()
    data class ShowToast(val message: String) : CryptoListEffect()
}
