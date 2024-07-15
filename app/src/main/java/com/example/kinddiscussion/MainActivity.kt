package com.example.kinddiscussion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem

import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kinddiscussion.ui.theme.KindDiscussionTheme
import com.example.kinddiscussion.ui.theme.selectedColor
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            KindDiscussionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BottomMenu()
                }
            }
        }
    }
}

@Composable
fun BottomMenu(
    modifier: Modifier = Modifier,

) {
    val navController = rememberNavController()
    val screens = listOf(
        Screen.Search,
        Screen.Home,
        Screen.Menu
    )


    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                screens.forEach {
                    screen ->
                    BottomNavigationItem(
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(route = screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true

                            }
                                  },
                        icon = {
                            Icon(
                               painter = painterResource(id = screen.icon),
                                contentDescription = null
                            )
                        },

                        selectedContentColor = selectedColor

                    )
                }
            }
        }
    ) {
       innerPadding -> NavHost(
            navController = navController,
            startDestination = "home",

            modifier = Modifier.padding(innerPadding)
           ) {
            composable("search") {
                SearchScreen()
            }
            composable("home") {
                HomeScreen()
            }
            composable("menu") {
                MenuScreen()
            }

        }
    }
}



@Preview(showBackground = true)
@Composable
fun Preview() {
    KindDiscussionTheme {
       BottomMenu()
    }
}