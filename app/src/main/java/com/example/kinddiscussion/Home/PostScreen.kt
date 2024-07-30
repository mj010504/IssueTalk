package com.example.kinddiscussion.Home

import android.graphics.Paint.Align
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.IntrinsicSize

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kinddiscussion.R
import com.example.kinddiscussion.grayLine
import com.example.kinddiscussion.ui.theme.selectedColor

@Composable
fun PostScreen(
    navController : NavController
) {

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

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(end = 5.dp), horizontalArrangement = Arrangement.End) {
        Box(
            modifier = Modifier
                .width(20.dp)
                .height(20.dp)

                .clickable {

                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.home), contentDescription = null
            )
        }
    }


    Text(text = "제목 제목 제목",
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
       Text("닉네임", color = Color.Black)
       Spacer(Modifier.width(5.dp))
       Text("00.00.00", color = Color.Gray)
   }
    Spacer(Modifier.height(8.dp))
    grayLine()
    Spacer(Modifier.height(15.dp))
    Text(text = stringResource(R.string.ipsum),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp))

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        IconButton(onClick = {  }) {
            Icon(
                painter = painterResource(id = R.drawable.home), contentDescription = null,
                modifier = Modifier
                    .padding(start = 6.dp)
                    .width(35.dp)
                    .height(35.dp)
            )
        }
        Spacer(Modifier.width(5.dp))
        Text("0", style = TextStyle(fontSize = 26.sp))
    }

    Spacer(Modifier.height(10.dp))
    grayLine()

    LazyColumn(

        modifier = Modifier

    ) {
        items(3) { index ->
            commentLayout()

        }
     }

    Spacer(Modifier.height(20.dp))
    var commentText by remember { mutableStateOf("") }
    
    Row(
        modifier = Modifier.padding(end = 10.dp)
    ) {
        OutlinedTextField(
            value = commentText,
            onValueChange = {newText -> commentText = newText},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = selectedColor,
                unfocusedBorderColor = Color.Gray

                ),
            placeholder = { Text("댓글 작성하기") },
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp).weight(1f),
            shape = RoundedCornerShape(15.dp),
            textStyle = TextStyle(fontSize = 20.sp)
        )

        IconButton(onClick = {  }) {
            Icon(
                painter = painterResource(id = R.drawable.home), contentDescription = null,
                modifier = Modifier
                    .padding(start = 6.dp)
                    .width(30.dp)
                    .height(30.dp)
            )
        }

    }



}
}

@Composable
fun commentLayout(

) {

    Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 12.dp)
                .wrapContentSize()

            ) {

                Text("닉네임", color = Color.Gray)
                Spacer(modifier = Modifier.width(3.dp))
                Text("00.00.00", color = Color.Gray, modifier = Modifier.padding(3.dp))
                 Spacer(modifier = Modifier.weight(1f))


            IconButton(onClick = {  }) {
                Icon(
                    painter = painterResource(id = R.drawable.home), contentDescription = null,
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)

                )
            }
        }

    Text("댓글 내용 댓글 내용",  modifier = Modifier.padding(start = 12.dp) )
    Spacer(Modifier.height(8.dp))
    grayLine()


}



@Preview(showBackground = true)
@Composable
fun PreviewTssdssextFieldExample() {
    val navController = rememberNavController()
    PostScreen(navController)
}
