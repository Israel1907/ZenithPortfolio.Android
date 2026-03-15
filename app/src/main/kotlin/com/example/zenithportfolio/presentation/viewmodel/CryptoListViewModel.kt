package com.example.zenithportfolio.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zenithportfolio.domain.repository.CryptoRepository
import com.example.zenithportfolio.domain.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val cryptoRepository: CryptoRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CryptoListState())
    val state: StateFlow<CryptoListState> = _state.asStateFlow()

    private val _effect = Channel<CryptoListEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        loadFavorites()
        onIntent(CryptoListIntent.LoadCryptos)
    }

    fun onIntent(intent: CryptoListIntent) {
        when (intent) {
            is CryptoListIntent.LoadCryptos -> loadCryptos()
            is CryptoListIntent.Refresh -> refresh()
            is CryptoListIntent.Search -> search(intent.query)
            is CryptoListIntent.ToggleFavorite -> toggleFavorite(intent.cryptoId)
            is CryptoListIntent.SelectCrypto -> selectCrypto(intent.cryptoId)
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            try {
                val favorites = favoriteRepository.getAllFavorites()
                _state.update { it.copy(favorites = favorites) }
            } catch (_: Exception) {
                // Favorites loading failure is non-critical
            }
        }
    }

    private fun loadCryptos() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            cryptoRepository.getCryptos()
                .onSuccess { result ->
                    _state.update { current ->
                        val filtered = applySearchFilter(result.cryptos, current.searchQuery)
                        current.copy(
                            isLoading = false,
                            cryptos = result.cryptos,
                            filteredCryptos = filtered,
                            fromCache = result.fromCache,
                            error = null
                        )
                    }
                    if (result.fromCache) {
                        _effect.send(CryptoListEffect.ShowToast("Datos del cache - CoinGecko limitado"))
                    }
                }
                .onFailure { e ->
                    val message = e.message ?: "Error desconocido"
                    _state.update { it.copy(isLoading = false, error = message) }
                    _effect.send(CryptoListEffect.ShowError(message))
                }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true) }
            cryptoRepository.getCryptos()
                .onSuccess { result ->
                    _state.update { current ->
                        val filtered = applySearchFilter(result.cryptos, current.searchQuery)
                        current.copy(
                            isRefreshing = false,
                            cryptos = result.cryptos,
                            filteredCryptos = filtered,
                            fromCache = result.fromCache
                        )
                    }
                    if (result.fromCache) {
                        _effect.send(CryptoListEffect.ShowToast("Datos del cache - CoinGecko limitado"))
                    }
                }
                .onFailure { e ->
                    _state.update { it.copy(isRefreshing = false) }
                    _effect.send(CryptoListEffect.ShowError(e.message ?: "Error al refrescar"))
                }
        }
    }

    private fun search(query: String) {
        _state.update { current ->
            val filtered = applySearchFilter(current.cryptos, query)
            current.copy(searchQuery = query, filteredCryptos = filtered)
        }
    }

    private fun toggleFavorite(cryptoId: String) {
        viewModelScope.launch {
            val currentFavorites = _state.value.favorites
            if (cryptoId in currentFavorites) {
                favoriteRepository.removeFavorite(cryptoId)
                _state.update { it.copy(favorites = currentFavorites - cryptoId) }
            } else {
                favoriteRepository.addFavorite(cryptoId)
                _state.update { it.copy(favorites = currentFavorites + cryptoId) }
            }
            _effect.send(CryptoListEffect.ShowToast("Favorito actualizado"))
        }
    }

    private fun selectCrypto(cryptoId: String) {
        viewModelScope.launch {
            _effect.send(CryptoListEffect.NavigateToDetail(cryptoId))
        }
    }

    private fun applySearchFilter(
        cryptos: List<com.example.zenithportfolio.domain.model.Crypto>,
        query: String
    ): List<com.example.zenithportfolio.domain.model.Crypto> {
        if (query.isBlank()) return cryptos
        val lowerQuery = query.lowercase()
        return cryptos.filter { crypto ->
            crypto.name.lowercase().contains(lowerQuery) ||
                crypto.symbol.lowercase().contains(lowerQuery)
        }
    }
}
