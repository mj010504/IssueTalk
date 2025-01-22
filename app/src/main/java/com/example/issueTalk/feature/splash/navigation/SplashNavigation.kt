package com.example.issueTalk.feature.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.issueTalk.feature.splash.SplashRoute
import kotlinx.serialization.Serializable

@Serializable data object SplashRoute

fun NavController.navigateToSplash(navOptions: NavOptions ? = null) {
    navigate(SplashRoute, navOptions)
}

fun NavGraphBuilder.splashScreen(
    navigateToLogin : () -> Unit,
    navigateToHome : () -> Unit,
) {
    composable<SplashRoute> {
         SplashRoute(
             navigateToLogin = navigateToLogin,
             navigateToHome = navigateToHome
         )
    }
}