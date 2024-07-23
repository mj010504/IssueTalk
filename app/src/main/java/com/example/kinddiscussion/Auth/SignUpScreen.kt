package com.example.kinddiscussion.Auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kinddiscussion.R
import com.example.kinddiscussion.ui.theme.KindDiscussionTheme
import com.example.kinddiscussion.ui.theme.selectedColor


@Composable
fun SignUpScreen(
    navController: NavController
) {
    var nameText by remember { mutableStateOf("") }
    var idText by remember { mutableStateOf("") }
    var pwText by remember { mutableStateOf("") }
    var pwCheckTest by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter =  painterResource(id = R.drawable.home), contentDescription = null
                ,modifier = Modifier
                    .width(55.dp)
                    .height(55.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = stringResource(
                id = R.string.signUp), style = TextStyle(fontSize = 20.sp), fontWeight = FontWeight.Bold)

        }

        Text(text = "이름", modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp),
            style = TextStyle(fontSize = 20.sp)
        )
        OutlinedTextField(

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = selectedColor,
                unfocusedBorderColor = Color.Gray
            ),
            value = nameText,
            onValueChange = { newText -> nameText = newText },
            placeholder = { Text("이름을 입력해주세요.") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start =20.dp, end= 20.dp, top = 10.dp)

        )

        Text(text = "아이디", modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp),
            style = TextStyle(fontSize = 20.sp)
        )
        OutlinedTextField(

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = selectedColor,
                unfocusedBorderColor = Color.Gray
            ),
            value = idText,
            onValueChange = { newText -> idText = newText },
            placeholder = { Text("아이디를 입력해주세요.") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start =20.dp, end= 20.dp, top = 10.dp)

        )

        Text(text = "비밀번호", modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp),
            style = TextStyle(fontSize = 20.sp)
        )
        OutlinedTextField(

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = selectedColor,
                unfocusedBorderColor = Color.Gray
            ),
            value = pwText,
            onValueChange = { newText -> pwText = newText },
            placeholder = { Text("비밀번호를 입력해주세요.") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start =20.dp, end= 20.dp, top = 10.dp)

        )

        Text(text = "비밀번호 확인", modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp),
            style = TextStyle(fontSize = 20.sp)
        )
        OutlinedTextField(

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = selectedColor,
                unfocusedBorderColor = Color.Gray
            ),
            value = idText,
            onValueChange = { newText -> idText = newText },
            placeholder = { Text("비밀번호를 다시 입력해주세요.") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start =20.dp, end= 20.dp, top = 10.dp)

        )

        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(selectedColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Text(stringResource(id = R.string.signUp), color = Color.White,style = TextStyle(fontSize = 20.sp))
        }


    }
}

@Preview(showBackground = true)
@Composable
fun Psreview() {
    KindDiscussionTheme {
        val navController = rememberNavController()
        SignUpScreen(navController)
    }
}