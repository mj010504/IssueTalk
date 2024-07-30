package com.example.kinddiscussion.Home

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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kinddiscussion.R
import com.example.kinddiscussion.ui.theme.selectedColor

@Composable
fun WritePostScreen(
    navController: NavController
) {
    var titleText by remember { mutableStateOf("") }
    var contentText by remember { mutableStateOf("") }
    BackHandler {
        navController.popBackStack()
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
            IconButton(onClick = {navController.popBackStack()}) {
                Icon(painter = painterResource(id = R.drawable.arrow_back), contentDescription = null,
                modifier = Modifier
                    .padding(start = 6.dp)
                    .width(35.dp)
                    .height(35.dp))
            }
            registerPostButton()
        }
        Spacer(modifier = Modifier.height(10.dp))
        // 제목
        TextField(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp),
            value = titleText,
            onValueChange = { newText -> titleText = newText },
            placeholder = {
                Text(
                    text = "제목",
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(fontSize = 25.sp)
                )
            }, colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White
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
                .fillMaxHeight(),
            placeholder = {Text("내용을 입력해주세요.")},
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )

        )


    }
}

@Composable
fun registerPostButton (

    ){
        OutlinedButton(
            onClick = {  },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = selectedColor
            ),
            border = BorderStroke(1.dp, selectedColor),
            modifier = Modifier.padding(end = 15.dp)

        ) {
            Text(text = "등록")
        }

}



@Preview(showBackground = true)
@Composable
fun PreviewTsdextFieldExample() {
    val navController = rememberNavController()
    WritePostScreen(navController)
}