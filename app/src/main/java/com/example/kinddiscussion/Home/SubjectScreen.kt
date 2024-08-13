package com.example.kinddiscussion.Home

import android.graphics.Paint.Align
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kinddiscussion.Firebase.Subject
import com.example.kinddiscussion.Home.viewModel.PostViewModel
import com.example.kinddiscussion.Home.viewModel.SubjectViewModel

import com.example.kinddiscussion.R
import com.example.kinddiscussion.blackLine
import com.example.kinddiscussion.blackLine2
import com.example.kinddiscussion.checkCancleDialog
import com.example.kinddiscussion.fieldToImage
import com.example.kinddiscussion.grayLine


@Composable
fun SubjectScreen(
    navController : NavController,
  subjectViewModel: SubjectViewModel = viewModel(),
    postViewModel: PostViewModel = viewModel()

) {

    val subject by subjectViewModel.subject
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    BackHandler {
        navController.popBackStack()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(modifier = Modifier.fillMaxWidth() ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back), contentDescription = null,
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .width(35.dp)
                        .height(35.dp)
                )
            }
            Spacer(Modifier.weight(1f))

            Box() {
                IconButton(onClick = { isDropDownMenuExpanded = true} ) {
                    Icon(
                        painter = painterResource(id = R.drawable.vertical_menu), contentDescription = null,
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
                color = Color.Gray
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
                color = Color.Gray
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
                IconButton(onClick = {  }) {
                    Icon(
                        painter = painterResource(id = R.drawable.agree), contentDescription = null,
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp),
                        tint = Color.Unspecified
                    )
                }

                Text(subject.agreeCount.toString(), style = TextStyle(fontSize = 25.sp, fontWeight = Bold))


            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(id = R.drawable.scale), contentDescription = null,
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                    )
                }

                Text(subject.neutralCount.toString(), style = TextStyle(fontSize = 25.sp, fontWeight = Bold))


            }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = {  }) {
                    Icon(
                        painter = painterResource(id = R.drawable.disagree), contentDescription = null,
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                    )
                }

                Text(subject.disagreeCount.toString(), style = TextStyle(fontSize = 25.sp, fontWeight = Bold))


            }
        }

        blackLine()

        Spacer(Modifier.height(80.dp))

        val postCount  = subject.postCount
        TextButton(onClick = {
            postViewModel.fetchPosts(subjectViewModel.subjectId.value)
            navController.navigate("subjectPost")
        }
        ) {
            Text("게시글 보러가기 (${postCount})", modifier = Modifier.padding(start = 8.dp),
                style = TextStyle(fontSize = 16.sp), color = Color.Black
            )
        }

        if(postCount == 0) {
            blackLine2()
        }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier

            ) {
                items(postCount) { index ->
                    postPreviewLayout(navController)

                }
            }

    }




    if(showDeleteDialog) {
        checkCancleDialog(onCheck = {  }, onDismiss = { showDeleteDialog = false }, dialogText = "정말로 이 주제를 삭제하시겠습니까?")
    }


}



@Composable
fun postPreviewLayout(
    navController: NavController
) {
    blackLine2()
    Spacer(Modifier.height(15.dp))
    Box(
        modifier = Modifier
            .clickable {

                navController.navigate("subjectPost")
            }
            .fillMaxWidth()
    ) {
        Text("50%만 허용해야한다. 왜냐하면 ~ 하기 때문이다.",  style = TextStyle(fontSize = 16.sp),
            modifier = Modifier.padding(start = 8.dp))
    }


}

@Preview(showBackground = true)
@Composable
fun PreviewTextFieldssddExample() {
    val navController = rememberNavController()
    SubjectScreen(navController)
}




