package com.example.zenithportfolio.presentation.viewmodel

sealed class DetailIntent {
    data object LoadDetail : DetailIntent()
    data class ChangeChartDays(val days: Int) : DetailIntent()
    data object RetryChart : DetailIntent()
}
