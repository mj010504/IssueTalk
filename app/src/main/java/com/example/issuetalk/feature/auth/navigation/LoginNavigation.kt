package com.example.issuetalk.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.issuetalk.feature.auth.LoginRoute
import com.example.issuetalk.feature.splash.navigation.SplashRoute
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    navigate(LoginRoute, navOptions)
}

fun NavGraphBuilder.loginScreen(
    navigateToSignUp: () -> Unit,
    navigateToHome: () -> Unit,
) {
    composable<LoginRoute> {
        LoginRoute(
            navigateToHome = navigateToHome,
            navigateToSignUp = navigateToSignUp
        )
    }
}