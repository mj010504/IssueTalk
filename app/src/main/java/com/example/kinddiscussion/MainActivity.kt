package com.example.kinddiscussion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kinddiscussion.Auth.LoginScreen
import com.example.kinddiscussion.Auth.SignUpScreen
import com.example.kinddiscussion.Home.HomeScreen
import com.example.kinddiscussion.Home.PostScreen
import com.example.kinddiscussion.Home.SubjectPostScreen
import com.example.kinddiscussion.Home.SubjectScreen
import com.example.kinddiscussion.Home.WritePostScreen
import com.example.kinddiscussion.Home.WriteSubjectScreen
import com.example.kinddiscussion.Menu.MenuScreen
import com.example.kinddiscussion.Search.SearchScreen
import com.example.kinddiscussion.ui.theme.KindDiscussionTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            KindDiscussionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {

                    navigation()
                }
            }
        }
    }
}



@Composable
fun navigation(

) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentDestination !in listOf(
                    "splash","search", "writeSubject", "subject","subjectPost", "post","login", "signUp", "writePost")) {
                BottomMenu(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "splash",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen(navController) }
            composable("search") { SearchScreen(navController) }
            composable("menu") { MenuScreen(navController) }
            composable("writeSubject") {
                WriteSubjectScreen(navController)
            }
            composable("subject") {
                SubjectScreen(navController)
            }
            composable("subjectPost") {
                SubjectPostScreen(navController)
            }
            composable("post") {
                PostScreen(navController)
            }
            composable("splash") { SplashScreen(navController) }
            composable("login") { LoginScreen(navController) }
            composable("signUp") { SignUpScreen(navController) }
            composable("writePost") { WritePostScreen(navController) }
        }
    }
}



@Composable
fun blackLine(

)  {
    Divider(
        color = Color.Black,
        thickness = 0.7.dp,
        modifier = Modifier.padding(vertical = 14.dp)
    )
}

@Composable
fun verticalBlackLine(

) {
    Box(
        modifier = Modifier
            .height(70.dp)
            .width(0.7.dp)
            .background(color = Color.Black)
    )
}

@Composable
fun grayLine(
    modifier : Modifier = Modifier
)  {
    Divider(
        color = Color.Gray,
        thickness = 0.7.dp,
        modifier = modifier

    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    KindDiscussionTheme {
        val navController = rememberNavController()
        SplashScreen(navController)
    }
}