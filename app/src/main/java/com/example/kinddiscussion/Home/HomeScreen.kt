
package com.example.kinddiscussion.Home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kinddiscussion.R
import com.example.kinddiscussion.SplashScreen

import com.example.kinddiscussion.blackLine
import com.example.kinddiscussion.ui.theme.selectedColor
import com.example.kinddiscussion.ui.theme.tabGreenColor


@Composable
fun HomeScreen(
    navCotnroller : NavController
) {
    val tabs = listOf("전체", "사회", "정치", "경제", "연예", "기타")
    var selectedTabIndex by remember {mutableStateOf(0)}



    Column (
        modifier = Modifier.fillMaxSize()
    )
    {

        TabRow(
            selectedTabIndex = selectedTabIndex,
            backgroundColor = tabGreenColor,
            contentColor = Color.White
        ){
            tabs.forEachIndexed {index, title ->
                Tab(
                    text = {Text(title, style = TextStyle(fontWeight = FontWeight.Bold))},
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index}

                )
            }
        }

        writeSubjectButton(navCotnroller)
        blackLine()

        LazyColumn(

            modifier = Modifier

        ) {
            items(3) { index ->

                subjectLayout(navCotnroller)
            }
        }
    }

}

@Composable
fun writeSubjectButton (
    navCotnroller : NavController

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, end = 11.dp),
        horizontalArrangement = Arrangement.End
    ) {
        OutlinedButton(
            onClick ={navCotnroller.navigate("writeSubject")},
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = selectedColor
            ),
            border = BorderStroke(1.dp, selectedColor)

        ) {
            Text(text = "주제 작성하기")
        }
    }

}

@Composable
fun subjectLayout(
    navCotnroller: NavController
) {
        Box(
            modifier = Modifier.clickable {
                navCotnroller.navigate("subject")
            }
                .wrapContentSize()
        ) {
            Row() {
                Icon(
                    painter = painterResource(id = R.drawable.society), contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .width(30.dp)
                        .height(30.dp)
                )

                Text(
                    "낙태를 허용해도 될까?",
                    modifier = Modifier.weight(1f).padding(start = 18.dp, end = 15.dp),
                    maxLines = 1
                )

            }
        }


    Box(
        modifier = Modifier.clickable {
            navCotnroller.navigate("subject")
        }
            .wrapContentSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        )
        {
            Text("사회", modifier = Modifier.padding(start = 6.dp, top = 3.dp)
            ,style  =TextStyle(fontWeight = FontWeight.Bold)
            )
            Icon(
                painter = painterResource(id = R.drawable.agree), contentDescription = null,
                modifier = Modifier
                    .padding(start = 18.dp)
                    .width(20.dp)
                    .height(20.dp)

            )

            Text("10", modifier = Modifier.padding(top = 5.dp, start = 4.dp))
            Icon(
                painter = painterResource(id = R.drawable.disagree), contentDescription = null,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .width(20.dp)
                    .height(20.dp)

            )

            Text("10", modifier = Modifier.padding(top = 5.dp, start = 4.dp))

            Icon(
                painter = painterResource(id = R.drawable.scale), contentDescription = null,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .width(20.dp)
                    .height(20.dp)

            )

            Text("10", modifier = Modifier.padding(top = 5.dp, start = 4.dp))

            Text(
                "24.07.07",
                modifier = Modifier.padding(top = 5.dp, start = 11.dp),
                color = Color.Gray,
                style = TextStyle(fontSize = 12.sp)
            )


        }
    }

    blackLine()
}


@Preview(showBackground = true)
@Composable
fun PreviewTextFielsddExample() {
    val navController = rememberNavController()
    HomeScreen(navController)
}

