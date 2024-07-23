package com.example.kinddiscussion.Search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import com.example.kinddiscussion.Home.WriteSubjectScreen
import com.example.kinddiscussion.R
import com.example.kinddiscussion.grayLine


@Composable
fun SearchScreen(
    navController : NavController
) {

    var searchText by remember { mutableStateOf("") }

    BackHandler {
        navController.popBackStack()
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = null,
                    modifier = Modifier.padding()
                )
            }

            TextField(
                modifier = Modifier.padding(),
                value = searchText,
                onValueChange = { newText -> searchText = newText },
                placeholder = {
                    Text(
                        text = "원하는 주제를 입력해주세요.",
                        style = TextStyle(fontSize = 20.sp)
                    )
                }, colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )

            IconButton(onClick = { navController.popBackStack() }) {
                Icon(painter = painterResource(id = R.drawable.search), contentDescription = null)
            }
        }

        Text(
            text = "최근 검색어",
            color = Color.Gray,
            modifier = Modifier.padding(start = 10.dp)
        )
        Spacer(modifier = Modifier.height(25.dp))
        // 최근 검색어 Lazy layout
        val searches = List(100) { "최근 검색어 #$it" }


        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.padding(start = 12.dp, end = 12.dp)

        ) {
            items(10) { index ->
                recentSearch(searchWord = searches[index])

            }
        }

    Row(
        modifier = Modifier.padding(start = 15.dp)
    ) {
        TextButton(onClick = {}) {
            Text(text = "전체 삭제", color = Color.Gray, style = TextStyle(fontSize = 12.sp))
        }

        TextButton(onClick = {}) {
            Text(text = "자동저장 끄기", color = Color.Gray, style = TextStyle(fontSize = 12.sp))
        }

    }

}


}

@Composable
fun recentSearch(
    searchWord : String
) {
    Text(
        text = searchWord, color = Color.Black, modifier = Modifier.padding(start = 8.dp, bottom = 5.dp ),
        style = TextStyle(fontSize = 16.sp))
    grayLine()
}


@Preview(showBackground = true)
@Composable
fun PreviewTextFsieldExample() {
    val navController = rememberNavController()
    SearchScreen(navController)
}