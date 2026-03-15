package com.example.zenithportfolio.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.zenithportfolio.presentation.screens.CryptoDetailScreen
import com.example.zenithportfolio.presentation.screens.CryptoListScreen
import com.example.zenithportfolio.presentation.screens.SplashScreen
import com.example.zenithportfolio.presentation.viewmodel.CryptoListViewModel
import com.example.zenithportfolio.presentation.viewmodel.DetailViewModel

@Composable
fun ZenithNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = SplashRoute
    ) {
        composable<SplashRoute> {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(CryptoListRoute) {
                        popUpTo(SplashRoute) { inclusive = true }
                    }
                }
            )
        }

        composable<CryptoListRoute> {
            val viewModel: CryptoListViewModel = hiltViewModel()
            CryptoListScreen(
                viewModel = viewModel,
                onNavigateToDetail = { cryptoId ->
                    navController.navigate(CryptoDetailRoute(cryptoId = cryptoId))
                }
            )
        }

        composable<CryptoDetailRoute> {
            val viewModel: DetailViewModel = hiltViewModel()
            CryptoDetailScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
