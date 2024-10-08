package com.example.kinddiscussion.feature.post.editpost

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kinddiscussion.R
import com.example.kinddiscussion.core.designsystem.component.checkCancleDialog
import com.example.kinddiscussion.core.designsystem.component.checkDialog
import com.example.kinddiscussion.core.designsystem.theme.selectedColor
import com.example.kinddiscussion.feature.post.PostViewModel

@Composable
fun EditPostScreen(
    navController: NavController,
    postViewModel : PostViewModel = viewModel()
) {

    var titleText by remember { mutableStateOf("") }
    var contentText by remember { mutableStateOf(TextFieldValue(postViewModel.post.value.content)) }
    var showEditDialog by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var dialogText by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }


    LaunchedEffect(Unit) {
        titleText = postViewModel.post.value.title
        contentText = contentText.copy(selection = TextRange(contentText.text.length))
        focusRequester.requestFocus()
    }

    BackHandler {
       showEditDialog = true
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = {
                showEditDialog = true
            }) {
                Icon(painter = painterResource(id = R.drawable.arrow_back), contentDescription = null,
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .width(35.dp)
                        .height(35.dp))
            }
            OutlinedButton(
                onClick = {
                    val isSuccess = postViewModel.editPost(titleText, contentText.text)
                    if(isSuccess) navController.popBackStack()
                    else {showDialog = true; dialogText = "게시글 수정에 실패했습니다."}
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = selectedColor
                ),
                border = BorderStroke(1.dp, selectedColor),
                modifier = Modifier.padding(end = 15.dp)

            ) {
                Text(text = "등록")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        // 제목
        TextField(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxWidth(),
            value = titleText,
            onValueChange = { newText -> titleText = newText },
            placeholder = {
                Text(
                    text = "제목",
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(fontSize = 25.sp)
                )
            }, colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = selectedColor,
                cursorColor = selectedColor
            ),
            textStyle = TextStyle(fontSize = 25.sp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        // 내용
        TextField(
            value = contentText,
            onValueChange = { newText ->
                contentText = newText
            },

            textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, bottom = 20.dp)
                .fillMaxHeight().focusRequester(focusRequester),
            placeholder = { Text("내용을 입력해주세요.") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = selectedColor,
                cursorColor = selectedColor
            )

        )


    }

    if(showEditDialog) {
       checkCancleDialog(onCheck = {
          showEditDialog = false
           navController.popBackStack()
       }, onDismiss = { showEditDialog = false },
           dialogText = "게시글 수정을 취소하시겠습니까?\n 수정된 내용은 저장되지 않습니다."  )
    }

    if(showDialog) {
        checkDialog(onDismiss = { showDialog = false}, dialogText =  dialogText)
    }
}
