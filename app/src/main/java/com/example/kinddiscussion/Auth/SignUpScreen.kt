package com.example.kinddiscussion.Auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.example.kinddiscussion.R

import com.example.kinddiscussion.ui.theme.KindDiscussionTheme
import com.example.kinddiscussion.ui.theme.selectedColor
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest


@Composable
fun SignUpScreen(
    navController: NavController,
    auth: FirebaseAuth
) {


    var emailText by remember { mutableStateOf("") }
    var nameText by remember { mutableStateOf("") }
    var pwText by remember { mutableStateOf("") }
    var pwCheckTest by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        IconButton(onClick = {navController.popBackStack()}) {
            Icon(painter = painterResource(id = R.drawable.arrow_back), contentDescription = null,
                modifier = Modifier
                    .padding(start = 6.dp)
                    .width(35.dp)
                    .height(35.dp))
        }

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
                .padding(start = 20.dp, end = 20.dp, top = 10.dp),
            maxLines = 1,
            textStyle = TextStyle(fontSize = 20.sp)

        )

        Text(text = "이메일", modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp),
            style = TextStyle(fontSize = 20.sp)
        )
        OutlinedTextField(

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = selectedColor,
                unfocusedBorderColor = Color.Gray
            ),
            value = emailText,
            onValueChange = { newText -> emailText = newText },
            placeholder = { Text("이메일을 입력해주세요.") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp),
            maxLines = 1,
            textStyle = TextStyle(fontSize = 20.sp),


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
                .padding(start = 20.dp, end = 20.dp, top = 10.dp),
            maxLines = 1,
            textStyle = TextStyle(fontSize = 20.sp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            )

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
            value = pwCheckTest,
            onValueChange = { newText -> pwCheckTest = newText },
            placeholder = { Text("비밀번호를 다시 입력해주세요.") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp),
            maxLines = 1,
            textStyle = TextStyle(fontSize = 20.sp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            )

        )

        Spacer(modifier = Modifier.height(30.dp))
        val context = LocalContext.current
        Button(
            onClick = {

                if (pwText != "" && pwText == pwCheckTest) {
                    auth.createUserWithEmailAndPassword(emailText, pwText)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // 회원가입 성공

                                val user = Firebase.auth.currentUser

                                val profileUpdates = userProfileChangeRequest {
                                    displayName = nameText
                                }

                                user!!.updateProfile(profileUpdates)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(context,"회원가입에 성공했습니다.",Toast.LENGTH_SHORT).show()
                                            navController.navigate("login")
                                        }
                                    }


                            } else {
                                // 회원가입 실패
                              Toast.makeText(context, task.exception?.message ,Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "비밀번호가 일치하지 않습니다." ,Toast.LENGTH_SHORT).show()
                }
            },
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

        val navController = rememberNavController()
        val auth = Firebase.auth
        SignUpScreen(navController, auth)


}