package com.example.issuetalk.feature.auth.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.issuetalk.feature.auth.signup.SignUpRoute
import kotlinx.serialization.Serializable

@Serializable
data object SignUpRoute

fun NavController.navigateToSignUp(navOptions: NavOptions? = null) {
    navigate(SignUpRoute, navOptions)
}

fun NavGraphBuilder.signUpScreen(
    navigateToWelcome: () -> Unit,
    popBackStack: () -> Unit
) {
    composable<SignUpRoute> {
        SignUpRoute(
            navigateToWelcome = navigateToWelcome,
            popBackStack = popBackStack

        )
    }
}