package com.example.kinddiscussion.Auth


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.checkScrollableContainerConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldColors
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kinddiscussion.R
import com.example.kinddiscussion.SplashScreen
import com.example.kinddiscussion.ui.theme.KindDiscussionTheme
import com.example.kinddiscussion.ui.theme.selectedColor
import java.time.format.TextStyle


@Composable
fun LoginScreen(
    navController: NavController
) {
    var idText by remember { mutableStateOf("") }
    var pwText by remember { mutableStateOf("") }

    Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Icon(painter =  painterResource(id = R.drawable.home), contentDescription = null
            ,modifier = Modifier
                    .width(160.dp)
                    .height(160.dp))
            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(

                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = selectedColor,
                    unfocusedBorderColor = Color.Gray
                ),
                value = idText,
                onValueChange = { newText -> idText = newText },
                placeholder = { Text("아이디") },
                modifier = Modifier
                    .wrapContentSize()

            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = selectedColor,
                    unfocusedBorderColor = Color.Gray
                ),
                value = pwText,
                onValueChange = { newText -> pwText = newText },
                placeholder = { Text("비밀번호") },
                modifier = Modifier
                    .wrapContentSize()

        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(selectedColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 53.dp, end = 53.dp)
        ) {
            Text(stringResource(id = R.string.login), color = Color.White,style = androidx.compose.ui.text.TextStyle(fontSize = 20.sp))
        }
        Spacer(modifier = Modifier.height(10.dp))
        TextButton(onClick = {navController.navigate("signUp")}) {
            Text(stringResource(id = R.string.signUp), color = Color.Black,  style =androidx.compose.ui.text.TextStyle(fontSize = 20.sp))
        }
        Spacer(modifier = Modifier.height(40.dp))
        TextButton(onClick = {navController.navigate("home")}) {
            Text("둘러보기", color = Color.Black,  style =androidx.compose.ui.text.TextStyle(fontSize = 16.sp))
        }
        }

}


@Preview(showBackground = true)
@Composable
fun lPreview() {
    KindDiscussionTheme {
        val navController = rememberNavController()
        LoginScreen(navController)
    }
}