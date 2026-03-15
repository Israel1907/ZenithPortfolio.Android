package com.example.zenithportfolio.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.zenithportfolio.domain.model.Crypto
import com.example.zenithportfolio.presentation.theme.Negative
import com.example.zenithportfolio.presentation.theme.Positive

@Composable
fun CryptoCard(
    crypto: Crypto,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = crypto.imageUrl,
                contentDescription = crypto.name,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = crypto.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = crypto.symbol.uppercase(),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "$${formatPrice(crypto.price)}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = formatChangePercent(crypto.changePercent24h),
                    fontSize = 14.sp,
                    color = if (crypto.changePercent24h >= 0) Positive else Negative
                )
                Text(
                    text = if (isFavorite) "\u2B50" else "\u2606",
                    fontSize = 24.sp,
                    modifier = Modifier.clickable(onClick = onFavoriteClick),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

fun formatPrice(price: Double): String {
    return if (price >= 1.0) {
        "%,.2f".format(price)
    } else {
        "%.6f".format(price)
    }
}

fun formatChangePercent(change: Double): String {
    val prefix = if (change >= 0) "+" else ""
    return "$prefix${"%.2f".format(change)}%"
}
