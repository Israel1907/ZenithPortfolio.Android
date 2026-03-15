package com.example.zenithportfolio.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.zenithportfolio.R
import com.example.zenithportfolio.presentation.components.DayButton
import com.example.zenithportfolio.presentation.components.EmptyStateView
import com.example.zenithportfolio.presentation.components.ErrorStateView
import com.example.zenithportfolio.presentation.components.LoadingStateView
import com.example.zenithportfolio.presentation.components.PriceChart
import com.example.zenithportfolio.presentation.components.StatCard
import com.example.zenithportfolio.presentation.components.formatChangePercent
import com.example.zenithportfolio.presentation.components.formatPrice
import com.example.zenithportfolio.presentation.theme.Negative
import com.example.zenithportfolio.presentation.theme.Positive
import com.example.zenithportfolio.presentation.viewmodel.DetailEffect
import com.example.zenithportfolio.presentation.viewmodel.DetailIntent
import com.example.zenithportfolio.presentation.viewmodel.DetailViewModel

@Composable
fun CryptoDetailScreen(
    viewModel: DetailViewModel,
    onBack: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val currentState = state.value

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is DetailEffect.ShowError -> { /* handled via state */ }
            }
        }
    }

    if (currentState.isLoading && currentState.crypto == null) {
        LoadingStateView(message = stringResource(R.string.loading_cryptos))
        return
    }

    if (currentState.error != null && currentState.crypto == null) {
        ErrorStateView(
            message = currentState.error,
            onRetry = { viewModel.onIntent(DetailIntent.LoadDetail) },
            onBack = onBack
        )
        return
    }

    val crypto = currentState.crypto ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back button
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onBack),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.size(40.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "\u2039",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Crypto image
        AsyncImage(
            model = crypto.imageUrl,
            contentDescription = crypto.name,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Name and symbol
        Text(
            text = crypto.name,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = crypto.symbol.uppercase(),
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Price card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.current_price),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "$${formatPrice(crypto.price)}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${formatChangePercent(crypto.changePercent24h)} (24h)",
                    fontSize = 16.sp,
                    color = if (crypto.changePercent24h >= 0) Positive else Negative
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatCard(
                        title = stringResource(R.string.rank_label),
                        value = "#${crypto.rank}",
                        modifier = Modifier.weight(1f).padding(end = 8.dp)
                    )
                    StatCard(
                        title = stringResource(R.string.market_cap_label),
                        value = formatMarketCap(crypto.marketCap),
                        modifier = Modifier.weight(1f).padding(start = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Day range label
        Text(
            text = if (currentState.selectedDays == 1) {
                stringResource(R.string.last_24h)
            } else {
                stringResource(R.string.last_n_days, currentState.selectedDays)
            },
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Day selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val dayOptions = listOf(
                1 to stringResource(R.string.day_24h),
                7 to stringResource(R.string.day_7d),
                30 to stringResource(R.string.day_30d),
                90 to stringResource(R.string.day_90d)
            )
            dayOptions.forEach { (days, label) ->
                DayButton(
                    label = label,
                    selected = currentState.selectedDays == days,
                    onClick = { viewModel.onIntent(DetailIntent.ChangeChartDays(days)) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Chart area
        key(currentState.selectedDays) {
            when {
                currentState.isChartLoading -> {
                    LoadingStateView(
                        message = stringResource(R.string.loading_chart),
                        modifier = Modifier.height(250.dp)
                    )
                }
                currentState.chartError != null -> {
                    ErrorStateView(
                        message = currentState.chartError,
                        onRetry = { viewModel.onIntent(DetailIntent.RetryChart) },
                        modifier = Modifier.height(250.dp)
                    )
                }
                currentState.priceHistory.isEmpty() -> {
                    EmptyStateView(
                        icon = "\uD83D\uDCCA",
                        title = stringResource(R.string.chart_unavailable_title),
                        subtitle = stringResource(R.string.chart_unavailable_subtitle),
                        modifier = Modifier.height(250.dp)
                    )
                }
                else -> {
                    PriceChart(
                        prices = currentState.priceHistory,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

private fun formatMarketCap(marketCap: Long): String {
    return when {
        marketCap >= 1_000_000_000_000 -> "${"%.1f".format(marketCap / 1_000_000_000_000.0)}T"
        marketCap >= 1_000_000_000 -> "${"%.1f".format(marketCap / 1_000_000_000.0)}B"
        marketCap >= 1_000_000 -> "${"%.1f".format(marketCap / 1_000_000.0)}M"
        else -> "$marketCap"
    }
}
