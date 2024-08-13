package com.example.kinddiscussion.Home

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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.kinddiscussion.Home.viewModel.PostViewModel
import com.example.kinddiscussion.Home.viewModel.SubjectViewModel

import com.example.kinddiscussion.R
import com.example.kinddiscussion.Search.recentSearch
import com.example.kinddiscussion.fieldToImage
import com.example.kinddiscussion.grayLine
import com.example.kinddiscussion.ui.theme.selectedColor
import java.time.format.TextStyle

lateinit var postList : List<Post>
lateinit var postIdList : List<String>


@Composable
fun SubjectPostScreen(
    navController : NavController,
    subjectViewModel: SubjectViewModel = viewModel(),
    postViewModel: PostViewModel = viewModel()
) {

    val fieldImage = fieldToImage(subjectViewModel.subject.value.subjectField)
    val subjectTitle = subjectViewModel.subject.value.title

    postList = postViewModel.postList
    postIdList = postViewModel.postIdList

    BackHandler {
        navController.popBackStack()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
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
            writePostButton(navController)
        }

        Spacer(modifier = Modifier.padding(top = 10.dp))
        grayLine()

        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier

        ) {
            items(postList.size) { index ->
                postLayout(navController, index, postViewModel)

            }
        }

    }
}

@Composable
fun writePostButton (
    navController: NavController
){
    OutlinedButton(
        onClick = { navController.navigate("writePost")  },
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
    navController: NavController, index : Int, postViewModel: PostViewModel
) {

    val post = postList[index]
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clickable {
                postViewModel.setPost(post, postIdList[index])
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
                        painter = painterResource(id = R.drawable.like_ic), contentDescription = null,
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


@Preview(showBackground = true)
@Composable
fun PreviewTsdssextFieldExample() {
    val navController = rememberNavController()
    SubjectPostScreen(navController)
}