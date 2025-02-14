package com.example.issuetalk.main.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.example.issuetalk.feature.auth.navigation.LoginRoute
import com.example.issuetalk.feature.auth.navigation.loginScreen
import com.example.issuetalk.feature.auth.navigation.navigateToLogin
import com.example.issuetalk.feature.auth.signup.navigation.SignUpRoute
import com.example.issuetalk.feature.auth.signup.navigation.navigateToSignUp
import com.example.issuetalk.feature.auth.signup.navigation.signUpScreen
import com.example.issuetalk.feature.home.navigation.HomeRoute
import com.example.issuetalk.feature.home.navigation.homeScreen
import com.example.issuetalk.feature.home.navigation.navigateToHome
import com.example.issuetalk.feature.splash.navigation.SplashRoute
import com.example.issuetalk.feature.splash.navigation.splashScreen


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
                        popUpTo<SplashRoute> { inclusive = true }
                    }
                )
            },
            navigateToHome = {
                navController.navigateToHome(
                    navOptions {
                        popUpTo<SplashRoute> { inclusive = true }
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
                navController.navigateToHome()
            }
        )
        signUpScreen(
            navigateToHome = {
                navController.navigateToHome(
                    navOptions {
                        popUpTo<SignUpRoute> { inclusive = true }
                    }
                )
            },

        )
        homeScreen(

        )

    }
}

