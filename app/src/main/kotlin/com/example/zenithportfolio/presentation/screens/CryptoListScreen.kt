package com.example.zenithportfolio.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.zenithportfolio.R
import com.example.zenithportfolio.presentation.components.CryptoCard
import com.example.zenithportfolio.presentation.components.EmptyStateView
import com.example.zenithportfolio.presentation.components.ErrorStateView
import com.example.zenithportfolio.presentation.components.LoadingStateView
import com.example.zenithportfolio.presentation.components.SearchBar
import com.example.zenithportfolio.presentation.viewmodel.CryptoListEffect
import com.example.zenithportfolio.presentation.viewmodel.CryptoListIntent
import com.example.zenithportfolio.presentation.viewmodel.CryptoListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoListScreen(
    viewModel: CryptoListViewModel,
    onNavigateToDetail: (String) -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CryptoListEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_LONG).show()
                }
                is CryptoListEffect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_LONG).show()
                }
                is CryptoListEffect.NavigateToDetail -> {
                    onNavigateToDetail(effect.cryptoId)
                }
            }
        }
    }

    val currentState = state.value

    when {
        // Loading state
        currentState.isLoading && currentState.cryptos.isEmpty() -> {
            LoadingStateView(
                message = stringResource(R.string.loading_cryptos)
            )
        }
        // Error state
        !currentState.isLoading && currentState.error != null && currentState.cryptos.isEmpty() -> {
            ErrorStateView(
                message = currentState.error,
                onRetry = { viewModel.onIntent(CryptoListIntent.LoadCryptos) }
            )
        }
        // Empty state
        !currentState.isLoading && currentState.cryptos.isEmpty() && currentState.error == null -> {
            EmptyStateView(
                icon = "\uD83D\uDCED",
                title = stringResource(R.string.no_cryptos_title),
                subtitle = stringResource(R.string.no_cryptos_subtitle),
                actionLabel = stringResource(R.string.retry),
                onAction = { viewModel.onIntent(CryptoListIntent.LoadCryptos) }
            )
        }
        // Content state
        else -> {
            PullToRefreshBox(
                isRefreshing = currentState.isRefreshing,
                onRefresh = { viewModel.onIntent(CryptoListIntent.Refresh) },
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    SearchBar(
                        query = currentState.searchQuery,
                        onQueryChange = { viewModel.onIntent(CryptoListIntent.Search(it)) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (currentState.filteredCryptos.isEmpty() && currentState.searchQuery.isNotBlank()) {
                        EmptyStateView(
                            icon = "\uD83D\uDD0D",
                            title = stringResource(R.string.no_results_title),
                            subtitle = stringResource(R.string.no_results_subtitle, currentState.searchQuery),
                            actionLabel = stringResource(R.string.clear_search),
                            onAction = { viewModel.onIntent(CryptoListIntent.Search("")) }
                        )
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(bottom = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(
                                items = currentState.filteredCryptos,
                                key = { it.id }
                            ) { crypto ->
                                CryptoCard(
                                    crypto = crypto,
                                    isFavorite = crypto.id in currentState.favorites,
                                    onClick = { viewModel.onIntent(CryptoListIntent.SelectCrypto(crypto.id)) },
                                    onFavoriteClick = { viewModel.onIntent(CryptoListIntent.ToggleFavorite(crypto.id)) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
