package com.example.kinddiscussion.feature.subject

import com.example.kinddiscussion.feature.post.PostViewModel
import androidx.activity.compose.BackHandler
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
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kinddiscussion.core.network.model.Post
import com.example.kinddiscussion.feature.home.SubjectViewModel

import com.example.kinddiscussion.R
import com.example.kinddiscussion.core.common.util.fieldToImage
import com.example.kinddiscussion.core.designsystem.component.blackLine
import com.example.kinddiscussion.core.designsystem.component.blackLine2
import com.example.kinddiscussion.core.designsystem.component.checkCancleDialog
import com.example.kinddiscussion.core.designsystem.component.checkDialog

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


lateinit var threePosts : List<Post>

@Composable
fun SubjectScreen(
    navController : NavController,
    subjectViewModel: SubjectViewModel = viewModel(),
    postViewModel: PostViewModel = viewModel()

) {

    val subject by subjectViewModel.subject
    threePosts = subjectViewModel.threePosts

    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showLogInDialog by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var dialogText by remember { mutableStateOf("") }

    var choice by remember { mutableStateOf("") }
    choice = subjectViewModel.choice.value


    val coroutineScope = rememberCoroutineScope()
    var isClickable by remember { mutableStateOf(true) }

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    BackHandler {
        navController.popBackStack()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(modifier = Modifier.fillMaxWidth() ) {
            IconButton(onClick = {
                if(isClickable) {
                    isClickable = false
                    navController.popBackStack()
                }

            }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back), contentDescription = null,
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .width(35.dp)
                        .height(35.dp)
                )
            }
            Spacer(Modifier.weight(1f))

            if(user != null && user.uid == subject.userId) {
            Box() {
                IconButton(onClick = {
                    isDropDownMenuExpanded = true

                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.vertical_menu),
                        contentDescription = null,
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                    )
                }


                DropdownMenu(
                    modifier = Modifier
                        .wrapContentSize(),
                    expanded = isDropDownMenuExpanded,
                    onDismissRequest = { isDropDownMenuExpanded = false }

                ) {
                    DropdownMenuItem(onClick = {
                        isDropDownMenuExpanded = false
                        showDeleteDialog = true
                    }
                    ) {
                        Text("삭제하기")
                    }
                }

            }


            }


        }




        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

            val fieldImage = fieldToImage(subject.subjectField)
            Icon(painter = painterResource(id = fieldImage) , contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp))

        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)) {
            Text(subject.date, color = Color.Gray,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.BottomStart)
            )
                Text(subject.subjectField, style = TextStyle(fontSize = 18.sp), modifier = Modifier.align(Alignment.Center))


        }

        blackLine()

        Text(subject.title,
            style = TextStyle(fontSize = 16.sp), modifier = Modifier.padding(start = 16.dp)
        )

        Row(
            modifier = Modifier.padding(start = 8.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.yes) , contentDescription = null,
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp), tint = Color.Unspecified)

            Text(subject.agreeText,
                style = TextStyle(fontSize = 12.sp), modifier = Modifier.padding(start = 10.dp),
            )
        }

        Row(
            modifier = Modifier.padding(start = 8.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.no) ,tint = Color.Unspecified, contentDescription = null,
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp))

            Text(subject.disagreeText,
                style = TextStyle(fontSize = 12.sp), modifier = Modifier.padding(start = 10.dp),
            )
        }

        blackLine()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = {
                    if(user == null) {
                        showLogInDialog = true
                    }
                    else {
                        if(isClickable) {
                            isClickable = false
                            subjectViewModel.setUserVote(user.uid, "agree")

                            coroutineScope.launch {
                                delay(300)
                                isClickable = true
                            }
                        }

                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.agree), contentDescription = null,
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp),
                        tint = if(choice == "agree") Color.Black else Color.Gray
                    )
                }

                Text(subject.agreeCount.toString(), style = TextStyle(fontSize = 25.sp, fontWeight = Bold)
                    ,color = if(choice == "agree") Color.Black else Color.Gray)


            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = {
                    if(user == null) {
                        showLogInDialog = true
                    }
                    else {
                        if(isClickable) {
                            isClickable = false
                            subjectViewModel.setUserVote(user.uid, "neutral")

                            coroutineScope.launch {
                                delay(300)
                                isClickable = true
                            }
                        }
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.scale), contentDescription = null,
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp),
                        tint = if(choice == "neutral") Color.Black else Color.Gray
                    )
                }

                Text(subject.neutralCount.toString(), style = TextStyle(fontSize = 25.sp, fontWeight = Bold)
                ,color = if(choice == "neutral") Color.Black else Color.Gray)


            }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = {
                    if(user == null) {
                        showLogInDialog = true
                    }
                    else {
                        if(isClickable) {
                            isClickable = false
                            subjectViewModel.setUserVote(user.uid, "disagree")

                            coroutineScope.launch {
                                delay(300)
                                isClickable = true
                            }
                        }

                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.disagree), contentDescription = null,
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp),
                        tint = if(choice == "disagree") Color.Black else Color.Gray
                    )
                }

                Text(subject.disagreeCount.toString(), style = TextStyle(fontSize = 25.sp, fontWeight = Bold)
                    ,color = if(choice == "disagree") Color.Black else Color.Gray)


            }
        }

        blackLine()

        Spacer(Modifier.height(80.dp))

        val postCount  = subject.postCount
        TextButton(onClick = {
            if(isClickable) {
                isClickable = false
                postViewModel.fetchPosts(subjectViewModel.subjectId.value)
                navController.navigate("subjectPost")

                coroutineScope.launch {
                    delay(300)
                    isClickable = true
                }
            }

        }
        ) {
            Text("게시글 보러가기 (${postCount})", modifier = Modifier.padding(start = 8.dp),
                style = TextStyle(fontSize = 16.sp), color = Color.Black
            )
        }

        if(postCount == 0) {
            blackLine2()
        }
        else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
            ) {
                items(threePosts.size) { index ->
                    postPreviewLayout(navController, index, subjectViewModel, postViewModel)

                }
            }

        }


    }




    if(showDeleteDialog) {
        checkCancleDialog(onCheck = {
            showDeleteDialog = false
           val isSuccess = subjectViewModel.deleteSubject()
            if(isSuccess) navController.popBackStack()
            else {
                showDialog = true
                dialogText = "주제 삭제에 실패했습니다."
            }

        }, onDismiss = { showDeleteDialog = false }, dialogText = "정말로 이 주제를 삭제하시겠습니까?")
    }

    if(showLogInDialog) {
        checkCancleDialog(onCheck = { navController.navigate("login") },
            onDismiss = { showLogInDialog = false}, dialogText = "로그인 후 이용이 가능합니다. 로그인하시겠습니까?" )
    }

    if(showDialog) {
        checkDialog(onDismiss = {showDialog =false}, dialogText = dialogText )
    }

}



@Composable
fun postPreviewLayout(
    navController: NavController,
    index : Int,
    subjectViewModel: SubjectViewModel, postViewModel: PostViewModel
) {
    val post = threePosts[index]
    var isClickable by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    blackLine2()
    Spacer(Modifier.height(3.dp))
    Box(
        modifier = Modifier
            .clickable {
                if(isClickable) {
                    isClickable = false
                    postViewModel.fetchPosts(subjectViewModel.subjectId.value)
                    navController.navigate("subjectPost")

                    coroutineScope.launch {
                        delay(300)
                        isClickable = true
                    }
                }

            }
            .wrapContentSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(post.title,  style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.weight(1f),
                maxLines = 1)

            Column(
                modifier = Modifier
                    .wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                        .padding(top = 5.dp)

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
                   ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                        .padding(top = 5.dp)
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

    }


}



