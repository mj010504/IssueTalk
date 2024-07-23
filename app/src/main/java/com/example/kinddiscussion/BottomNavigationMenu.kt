package com.example.kinddiscussion

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kinddiscussion.Home.HomeScreen
import com.example.kinddiscussion.Menu.MenuScreen
import com.example.kinddiscussion.Search.SearchScreen
import com.example.kinddiscussion.ui.theme.selectedColor

sealed class Screen(
    val route : String,
    @DrawableRes val icon : Int
) {
    object Search :
            Screen(
                route = "search",
                icon = R.drawable.search
            )

    object Home :
            Screen(
                route = "home",
                icon = R.drawable.home
            )

    object Menu :
            Screen(
                route = "menu",
                icon = R.drawable.setting_ic
            )
}

@Composable
fun BottomMenu(
    navController : NavController

    ) {
    val screens = listOf(
        Screen.Search,
        Screen.Home,
        Screen.Menu
    )

    BottomNavigation ( backgroundColor = Color.White) {
        screens.forEach { screen ->
            BottomNavigationItem(


                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = null
                    )
                },
                selected = navController.currentBackStackEntry?.destination?.route == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                selectedContentColor = selectedColor,
                unselectedContentColor = Color.Black
            )
        }
    }

 }


