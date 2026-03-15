package com.example.zenithportfolio.presentation.viewmodel

sealed class DetailEffect {
    data class ShowError(val message: String) : DetailEffect()
}
