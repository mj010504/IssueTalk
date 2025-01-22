package com.example.issueTalk.main
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.issueTalk.core.designsystem.theme.IssueTalkTheme
import com.example.issueTalk.main.navigation.IssueTalkNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    private val subjectViewModel by viewModels<SubjectViewModel>()
//    private val postViewModel by viewModels<PostViewModel>()
//    private val commentViewModel by viewModels<CommentViewModel>()
//    private val searchViewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
//            val coroutineScope = rememberCoroutineScope()
                IssueTalkTheme  {
                   Scaffold(
                       bottomBar = {
                           val navBackStackEntry by navController.currentBackStackEntryAsState()
                           val currentRoute = navBackStackEntry?.destination?.route
                       }
                   ) { innerPadding ->
                        IssueTalkNavHost(navController, modifier = Modifier.padding(innerPadding))
                   }

            }
        }
    }


}








