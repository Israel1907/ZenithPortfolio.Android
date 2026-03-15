package com.example.zenithportfolio.presentation.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zenithportfolio.R
import com.example.zenithportfolio.presentation.theme.Accent
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    var animationStarted by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (animationStarted) 1f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "alpha"
    )

    val scale by animateFloatAsState(
        targetValue = if (animationStarted) 1f else 0.5f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "scale"
    )

    LaunchedEffect(Unit) {
        animationStarted = true
        delay(2500)
        onSplashFinished()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .alpha(alpha)
            .scale(scale),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "\uD83D\uDC8E",
            fontSize = 72.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.splash_title),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Accent
        )
        Text(
            text = stringResource(R.string.splash_subtitle),
            fontSize = 24.sp,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.splash_tagline),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
