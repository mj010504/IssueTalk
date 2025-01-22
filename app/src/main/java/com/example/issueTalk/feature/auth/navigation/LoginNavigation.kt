package com.example.issueTalk.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.issueTalk.feature.auth.LoginRoute
import com.example.issueTalk.feature.splash.navigation.SplashRoute
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    navigate(SplashRoute, navOptions)
}

fun NavGraphBuilder.loginScreen(
    navigateToSignUp: () -> Unit,
    navigateToHome: () -> Unit,
) {
    composable<SplashRoute> {
        LoginRoute(
            navigateToHome = navigateToHome,
            navigateToSignUp = navigateToSignUp
        )
    }
}