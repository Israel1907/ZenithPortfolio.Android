package com.example.zenithportfolio.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zenithportfolio.domain.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val cryptoRepository: CryptoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val cryptoId: String = savedStateHandle["cryptoId"] ?: ""

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()

    private val _effect = Channel<DetailEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        onIntent(DetailIntent.LoadDetail)
    }

    fun onIntent(intent: DetailIntent) {
        when (intent) {
            is DetailIntent.LoadDetail -> loadDetail()
            is DetailIntent.ChangeChartDays -> {
                _state.update { it.copy(selectedDays = intent.days) }
                loadChart(intent.days)
            }
            is DetailIntent.RetryChart -> loadChart(_state.value.selectedDays)
        }
    }

    private fun loadDetail() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            cryptoRepository.getCryptoById(cryptoId)
                .onSuccess { crypto ->
                    _state.update { it.copy(isLoading = false, crypto = crypto) }
                    loadChart(_state.value.selectedDays)
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                    _effect.send(DetailEffect.ShowError(e.message ?: "Error desconocido"))
                }
        }
    }

    private fun loadChart(days: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isChartLoading = true, chartError = null, priceHistory = emptyList()) }
            delay(300) // Rate limit protection
            cryptoRepository.getMarketChart(cryptoId, days)
                .onSuccess { prices ->
                    val sampled = samplePrices(prices, days)
                    _state.update { it.copy(isChartLoading = false, priceHistory = sampled) }
                }
                .onFailure { e ->
                    _state.update { it.copy(isChartLoading = false, chartError = e.message) }
                }
        }
    }

    private fun samplePrices(prices: List<Double>, days: Int): List<Double> {
        val maxPoints = when (days) {
            1 -> 24
            7 -> 42
            30 -> 30
            90 -> 45
            else -> 50
        }
        if (prices.size <= maxPoints) return prices
        val step = prices.size / maxPoints
        return prices.filterIndexed { index, _ -> index % step == 0 }
    }
}
