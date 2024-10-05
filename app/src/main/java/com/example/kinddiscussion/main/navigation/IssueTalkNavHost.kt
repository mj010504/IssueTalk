package com.example.kinddiscussion.main.navigation

import com.example.kinddiscussion.feature.post.PostViewModel
import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kinddiscussion.feature.post.editpost.EditPostScreen
import com.example.kinddiscussion.feature.home.HomeScreen
import com.example.kinddiscussion.feature.post.PostScreen
import com.example.kinddiscussion.feature.postlist.SubjectPostScreen
import com.example.kinddiscussion.feature.subject.SubjectScreen
import com.example.kinddiscussion.feature.post.writepost.WritePostScreen
import com.example.kinddiscussion.feature.subject.writesubject.WriteSubjectScreen
import com.example.kinddiscussion.feature.post.CommentViewModel
import com.example.kinddiscussion.feature.search.SearchViewModel
import com.example.kinddiscussion.feature.home.SubjectViewModel
import com.example.kinddiscussion.feature.menu.MenuScreen
import com.example.kinddiscussion.feature.search.SearchScreen
import com.example.kinddiscussion.feature.splash.SplashScreen
import com.example.kinddiscussion.feature.auth.LoginScreen
import com.example.kinddiscussion.feature.auth.SignUpScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun navigation(
    subjectViewModel: SubjectViewModel, postViewModel: PostViewModel, commentViewModel: CommentViewModel,
    searchViewModel: SearchViewModel, context : Context
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
            composable("home") { HomeScreen(navController, subjectViewModel) }
            composable("search") { SearchScreen(navController, searchViewModel, subjectViewModel, context) }
            composable("menu") { MenuScreen(navController) }
            composable("writeSubject") {
                WriteSubjectScreen(navController, subjectViewModel)
            }
            composable("subject") {
                SubjectScreen(navController, subjectViewModel, postViewModel)
            }
            composable("subjectPost") {
                SubjectPostScreen(navController, subjectViewModel, postViewModel, commentViewModel)
            }
            composable("post") {
                PostScreen(navController, postViewModel, commentViewModel)
            }
            composable("splash") { SplashScreen(navController) }
            composable("login") { LoginScreen(navController) }
            composable("signUp") { SignUpScreen(navController) }
            composable("writePost") { WritePostScreen(navController, subjectViewModel, postViewModel) }
            composable("editPost") { EditPostScreen(navController, postViewModel) }
        }
    }
}
