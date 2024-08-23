package com.example.kinddiscussion.Home

import PostViewModel
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kinddiscussion.Firebase.Post
import com.example.kinddiscussion.Firebase.Subject
import com.example.kinddiscussion.Home.viewModel.CommentViewModel
import com.example.kinddiscussion.Home.viewModel.SubjectViewModel

import com.example.kinddiscussion.R
import com.example.kinddiscussion.checkCancleDialog
import com.example.kinddiscussion.fieldToImage
import com.example.kinddiscussion.grayLine
import com.example.kinddiscussion.ui.theme.selectedColor
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import java.time.format.TextStyle

lateinit var postList : List<Post>
lateinit var postIdList : List<String>

@ExperimentalMaterialApi
@Composable
fun SubjectPostScreen(
    navController : NavController,
    subjectViewModel: SubjectViewModel = viewModel(),
    postViewModel: PostViewModel = viewModel(),
    commentViewModel: CommentViewModel = viewModel()
) {

    val fieldImage = fieldToImage(subjectViewModel.subject.value.subjectField)
    val subjectTitle = subjectViewModel.subject.value.title
    var showLogInDialog by remember { mutableStateOf(false) }

    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(isRefreshing, {
        isRefreshing = true
        postViewModel.fetchPosts(subjectViewModel.subjectId.value)
        isRefreshing = false
    })


    postList = postViewModel.postList
    postIdList = postViewModel.postIdList

    BackHandler {
        navController.popBackStack()
    }


    Box(
        modifier = Modifier.pullRefresh(pullRefreshState).fillMaxSize()
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back), contentDescription = null,
                        modifier = Modifier
                            .padding(start = 6.dp)
                            .width(35.dp)
                            .height(35.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(

                        painter = painterResource(id = fieldImage), contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .width(45.dp)
                            .height(45.dp)
                    )

                    Text(
                        text = subjectTitle,
                        style = androidx.compose.ui.text.TextStyle(fontSize = 24.sp),
                        modifier = Modifier.padding(start = 20.dp),

                        )
                }

                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    writePostButton(navController, { showLogInDialog = true })
                }

                Spacer(modifier = Modifier.padding(top = 10.dp))
                grayLine()
                Spacer(modifier = Modifier.height(10.dp))
            }


            items(postList.size) { index ->
                postLayout(navController, index, postViewModel, commentViewModel)

            }

        }


        PullRefreshIndicator(
                refreshing = isRefreshing,
        state = pullRefreshState,
        contentColor = Color.Black,
        modifier = Modifier.align(Alignment.TopCenter)
        )


    }




    if(showLogInDialog) {
        checkCancleDialog(onCheck = { navController.navigate("login") },
            onDismiss = { showLogInDialog = false}, dialogText = "로그인 후 이용이 가능합니다. 로그인하시겠습니까?" )
    }
}

@Composable
fun writePostButton (
    navController: NavController,
    onShowDialog : () -> Unit
){
    OutlinedButton(
        onClick = {
            val auth = FirebaseAuth.getInstance()
            val user = auth.currentUser
            if(user!= null) {
                navController.navigate("writePost")
            }
            else {
                onShowDialog()
            }
         },
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = selectedColor
        ),
        border = BorderStroke(1.dp, selectedColor),
        modifier = Modifier.padding(end = 15.dp)

    ) {
        Text(text = "글쓰기")
    }

}

@Composable
fun postLayout (
    navController: NavController, index : Int, postViewModel: PostViewModel, commentViewModel: CommentViewModel
) {

    val post = postList[index]
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clickable {
                postViewModel.setPost(post, postIdList[index])

                val subjecetId = post.subjectId
                commentViewModel.fetchComments(subjecetId, postIdList[index])

                val auth = FirebaseAuth.getInstance()
                val user = auth.currentUser
                if (user != null) {
                    postViewModel.isPostLikedByUser(user.uid)
                }

                navController.navigate("post")
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp),
        ) {
            Text(post.title,
                modifier = Modifier.weight(1f),
                style = androidx.compose.ui.text.TextStyle(fontSize = 16.sp),
                maxLines = 1
            )

            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.like_off), contentDescription = null,
                        tint = Color.Unspecified
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))


                Text(post.likeCount.toString(), color = Color.Gray)
            }


            Spacer(modifier = Modifier.width(5.dp))
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.comment), contentDescription = null,
                        tint = Color.Unspecified
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                    , contentAlignment = Alignment.Center

                ) {
                    Text(post.commentCount.toString(), color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
        }


        Row(
            modifier = Modifier
                .padding(start = 12.dp)
                .align(Alignment.BottomStart)
            , verticalAlignment = Alignment.CenterVertically
        ) {
            Text(post.userName, color = Color.Gray)
            Spacer(modifier = Modifier.width(5.dp))
            Text(post.date, color = Color.Gray)
        }

    }

        Spacer(modifier = Modifier.height(3.dp))
        grayLine()


}


