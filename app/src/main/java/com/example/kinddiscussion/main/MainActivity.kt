package com.example.kinddiscussion.main

import com.example.kinddiscussion.feature.post.PostViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.kinddiscussion.feature.post.CommentViewModel
import com.example.kinddiscussion.feature.search.SearchViewModel
import com.example.kinddiscussion.feature.home.SubjectViewModel
import com.example.kinddiscussion.core.designsystem.theme.KindDiscussionTheme
import com.example.kinddiscussion.main.navigation.navigation


class MainActivity : ComponentActivity() {

    private val subjectViewModel by viewModels<SubjectViewModel>()
    private val postViewModel by viewModels<PostViewModel>()
    private val commentViewModel by viewModels<CommentViewModel>()
    private val searchViewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {


                KindDiscussionTheme {

                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background,
                    ) {

                        navigation(subjectViewModel, postViewModel, commentViewModel, searchViewModel, this)
                    }

            }
        }
    }


}








