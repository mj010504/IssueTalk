package com.example.issueTalk.main.navigation


import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.example.issueTalk.feature.auth.navigation.LoginRoute
import com.example.issueTalk.feature.auth.navigation.loginScreen
import com.example.issueTalk.feature.auth.navigation.navigateToLogin
import com.example.issueTalk.feature.auth.signup.navigation.navigateToSignUp
import com.example.issueTalk.feature.auth.signup.navigation.signUpScreen
import com.example.issueTalk.feature.home.navigation.HomeRoute
import com.example.issueTalk.feature.home.navigation.homeScreen
import com.example.issueTalk.feature.home.navigation.navigateToHome
import com.example.issueTalk.feature.splash.navigation.SplashRoute
import com.example.issueTalk.feature.splash.navigation.splashScreen


@Composable
fun IssueTalkNavHost(
    navController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = SplashRoute,
        modifier = modifier
    ) {
        splashScreen(
            navigateToLogin = {
                navController.navigateToLogin(
                    navOptions {
                        popUpTo<LoginRoute> { inclusive = true }
                    }
                )
            },
            navigateToHome = {
                navController.navigateToHome(
                    navOptions {
                        popUpTo<HomeRoute> { inclusive = true }
                    }
                )
            }
        )
        loginScreen(
            navigateToSignUp = {
                navController.navigateToSignUp(
                    navOptions {
                        popUpTo<LoginRoute> { inclusive = true }
                    }
                )
            },
            navigateToHome = {
                navController.navigateToHome(
                    navOptions {
                        popUpTo<HomeRoute> { inclusive = true }
                    }
                )
            }
        )
        signUpScreen(
            navigateToHome = {
                navController.navigateToHome(
                    navOptions {
                        popUpTo<HomeRoute> { inclusive = true }
                    }
                )
            }
        )
        homeScreen(

        )

    }
}

