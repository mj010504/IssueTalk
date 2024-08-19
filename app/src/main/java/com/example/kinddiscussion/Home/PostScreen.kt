package com.example.kinddiscussion.Home

import android.graphics.Paint.Align
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kinddiscussion.Firebase.Comment
import com.example.kinddiscussion.Firebase.Post
import com.example.kinddiscussion.Home.viewModel.CommentViewModel
import com.example.kinddiscussion.Home.viewModel.PostViewModel

import com.example.kinddiscussion.R
import com.example.kinddiscussion.checkCancleDialog
import com.example.kinddiscussion.checkDialog
import com.example.kinddiscussion.getCurrentDateFormatted
import com.example.kinddiscussion.grayLine
import com.example.kinddiscussion.ui.theme.selectedColor
import com.google.firebase.auth.FirebaseAuth

lateinit var commentList : List<Comment>
lateinit var commentIdList : List<String>
@Composable
fun PostScreen(
    navController : NavController,
    postViewModel: PostViewModel = viewModel(),
    commentViewModel: CommentViewModel = viewModel()
) {

    commentList = commentViewModel.commentList
    commentIdList = commentViewModel.commentIdList

    var commentText by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var dialogText by remember { mutableStateOf("") }
    var sendButtonColor by remember {mutableStateOf(Color.Gray)}
    var showLogInDialog by remember { mutableStateOf(false) }

    if(commentText == "") sendButtonColor= Color.Gray
    else sendButtonColor = selectedColor


    BackHandler {
        navController.popBackStack()
    }

    val post by postViewModel.post

    var isPostDropDownMenuExpanded by remember { mutableStateOf(false) }
    var isCommentDropDownMenuExpanded = remember { mutableStateOf(false) }
    var showPostDeleteDialog by remember { mutableStateOf(false) }
    var showCommentDeleteDialog by remember { mutableStateOf(false) }
    var selectedIndex = remember { mutableStateOf(-1) }
    val commentCount = post.commentCount.toString()

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

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
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 5.dp), horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)

                    .clickable {

                    }
            ) {

                Box() {
                    IconButton(onClick = {isPostDropDownMenuExpanded = true}) {
                        Icon(
                            painter = painterResource(id = R.drawable.vertical_menu),
                            contentDescription = null
                        )
                    }


                    DropdownMenu(
                        modifier = Modifier
                            .wrapContentSize(),
                        expanded = isPostDropDownMenuExpanded,
                        onDismissRequest = { isPostDropDownMenuExpanded = false }

                    ) {
                        DropdownMenuItem(onClick = {
                            isPostDropDownMenuExpanded = false
                        }
                        ) {
                            Text("수정하기")
                        }

                        DropdownMenuItem(onClick = {
                            isPostDropDownMenuExpanded = false
                            showPostDeleteDialog = true
                        }
                        ) {
                            Text("삭제하기")
                        }
                    }
                }

            }

        }



        Text(
            text = post.title,
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier
                .padding(start = 20.dp, end = 35.dp)
                .fillMaxWidth()
        )
        Spacer(Modifier.height(5.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 20.dp)
        ) {
            Text(post.userName, color = Color.Black)
            Spacer(Modifier.width(5.dp))
            Text(post.date, color = Color.Gray)
        }
        Spacer(Modifier.height(8.dp))
        grayLine()
        Spacer(Modifier.height(15.dp))
        Text(
            text = post.content,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp),
            style = TextStyle(fontSize = 17.sp)
        )


        Spacer(Modifier.height(15.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.like_ic), contentDescription = null,
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .width(30.dp)
                        .height(30.dp)
                )
            }
            Spacer(Modifier.width(5.dp))
            Text(post.likeCount.toString(), style = TextStyle(fontSize = 22.sp))
        }

        Spacer(Modifier.height(10.dp))
        grayLine()

        Text("전체 댓글($commentCount)",
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(start = 15.dp, top = 15.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {

            items(commentList.size) { index ->
                commentLayout(isCommentDropDownMenuExpanded, {showCommentDeleteDialog = true}, index, selectedIndex)

            }
        }
        

            
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth().wrapContentSize()
            ) {
                OutlinedTextField(
                    value = commentText,
                    onValueChange = { newText -> commentText = newText },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = selectedColor,
                        unfocusedBorderColor = Color.Gray

                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f).imePadding(),
                    placeholder = { Text("댓글 작성하기") },
                    shape = RoundedCornerShape(15.dp),
                    textStyle = TextStyle(fontSize = 16.sp)
                )

                IconButton(onClick = {

                    val auth = FirebaseAuth.getInstance()
                    val user = auth.currentUser



                        if (commentText != "") {

                            if (user == null) {
                                showLogInDialog = true
                            } else {
                                val auth = FirebaseAuth.getInstance()
                                val userId = auth.currentUser!!.uid
                                val currentDate = getCurrentDateFormatted()
                                val writeComment = Comment(

                                    userId = userId,
                                    postId = postViewModel.postId.value,
                                    userName = auth.currentUser!!.displayName.toString(),
                                    date = currentDate,
                                    commentContent = commentText
                                )

                                val subjectId = postViewModel.post.value.subjectId
                                val postId = postViewModel.postId.value

                                val isSuccess =
                                    commentViewModel.writeComment(subjectId, postId, writeComment)
                                if (!isSuccess) {
                                    showDialog = true
                                    dialogText = "댓글 작성에 실패했습니다."
                                } else {
                                    commentText = ""

                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                }
                            }

                        }


                }) {

                        Icon(
                            painter = painterResource(id = R.drawable.send_ic), contentDescription = null,
                            modifier = Modifier
                                .padding(start = 6.dp)
                                .width(20.dp)
                                .height(20.dp),
                            tint = sendButtonColor
                        )



                }

            }


    }

    if(showCommentDeleteDialog) {
        checkCancleDialog(onCheck = {  }, onDismiss = { showCommentDeleteDialog = false }, dialogText = "정말로 이 댓글을 삭제하시겠습니까?")
    }


    if(showPostDeleteDialog) {
        checkCancleDialog(
            onCheck = { },
            onDismiss = { showPostDeleteDialog = false },
            dialogText = "정말로 이 게시글을 삭제하시겠습니까?"
        )
    }

    if(showDialog) {
        checkDialog(onDismiss = { showDialog =false }, dialogText = dialogText)
    }

    if(showLogInDialog) {
        checkCancleDialog(onCheck = { navController.navigate("login") },
            onDismiss = { showLogInDialog = false}, dialogText = "로그인 후 이용이 가능합니다. 로그인하시겠습니까?" )
    }

}


@Composable
fun commentLayout(
    menuExpanded : MutableState<Boolean>, onShowDialog : () -> Unit,
    index : Int, selectedIndex : MutableState<Int>
) {
    val comment = commentList[index]
    Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 12.dp)
                .wrapContentSize()


            ) {

                Text(comment.userName, color = Color.Gray)
                Spacer(modifier = Modifier.width(3.dp))
                Text(comment.date, color = Color.Gray, modifier = Modifier.padding(3.dp))
                 Spacer(modifier = Modifier.weight(1f))

            Box() {
                IconButton(onClick = {menuExpanded.value = true
                        selectedIndex.value = index
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.vertical_menu), contentDescription = null,
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)

                    )
                }

                if (selectedIndex.value == index && menuExpanded.value) {
                    DropdownMenu(
                        modifier = Modifier
                            .wrapContentSize(),
                        expanded = menuExpanded.value,
                        onDismissRequest = { menuExpanded.value = false }

                    ) {
                        DropdownMenuItem(onClick = {
                            menuExpanded.value = false
                        }
                        ) {
                            Text("수정하기")
                        }

                        DropdownMenuItem(onClick = {
                            menuExpanded.value = false
                            onShowDialog()
                        }
                        ) {
                            Text("삭제하기")
                        }
                    }
                }


            }

        }

    Text(comment.commentContent,  modifier = Modifier.padding(start = 12.dp) )
    Spacer(Modifier.height(8.dp))

}



@Preview(showBackground = true)
@Composable
fun PreviewTssdssextFieldExample() {
    val navController = rememberNavController()
    PostScreen(navController)
}
