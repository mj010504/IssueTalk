package com.example.kinddiscussion.Menu


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kinddiscussion.Home.HomeScreen
import com.example.kinddiscussion.R
import com.example.kinddiscussion.blackLine
import com.example.kinddiscussion.blackLine2
import com.example.kinddiscussion.checkCancleDialog
import com.example.kinddiscussion.checkDialog
import com.example.kinddiscussion.grayLine
import com.google.common.io.Files.append
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


@Composable
fun MenuScreen(
    navCotnroller : NavController
) {
    var showLogOutDialog by remember { mutableStateOf(false) }
    var showDeleteUserDialog by remember { mutableStateOf(false) }
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    Column(
       modifier = Modifier
           .fillMaxSize()
           .padding(top = 15.dp)
    ) {


        val nickNameText : String = if(user != null) {
            user.displayName.toString()
        } else {
            "로그인"
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.profile_ic), contentDescription = null,
                modifier = Modifier
                    .padding(start = 22.dp)
                    .width(25.dp)
                    .height(25.dp)
            )

            if(user != null) {
                Text(
                    nickNameText,
                    style = TextStyle(fontSize = 24.sp),
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            else {
                TextButton(onClick = { navCotnroller.navigate("login") }) {
                    Text(
                        nickNameText,
                        color = Color.Black,
                        style = TextStyle(fontSize = 24.sp, textDecoration = TextDecoration.Underline),
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }

        }

        Spacer(Modifier.height(18.dp))
        blackLine2()

        if(user != null) {

            Box(

                modifier = Modifier
                    .clickable {
                        // val newPassword = "SOME-SECURE-PASSWORD"

//                    user!!.updatePassword(newPassword)
//                        .addOnCompleteListener { task ->
//                            if (task.isSuccessful) {
//                                Log.d(TAG, "User password updated.")
//                            }
//                        }
                    }
                    .padding(top = 18.dp, bottom = 14.dp, start = 22.dp)
            ) {
                Text("비밀번호 변경",

                    color = Color.Black, style = TextStyle(fontSize = 16.sp))
            }

            grayLine()


            Box(
                modifier = Modifier
                    .clickable {
                        showLogOutDialog = true
                    }
                    .padding(top = 18.dp, bottom = 14.dp, start = 22.dp)
            ) {
                Text("로그아웃",
                    color = Color.Black, style = TextStyle(fontSize = 16.sp))
            }

            grayLine()

            Box(
                modifier = Modifier
                    .clickable {
                        showDeleteUserDialog = true



                    }
                    .padding(top = 18.dp, bottom = 14.dp, start = 22.dp)
            ) {
                Text("회원탈퇴",
                    color = Color.Red, style = TextStyle(fontSize = 16.sp))
            }


         }
        }

        if(showLogOutDialog) {
            checkCancleDialog(onCheck = {
                auth.signOut()
                navCotnroller.navigate("login") },
                onDismiss = { showLogOutDialog = false }, dialogText = "정말 로그아웃하시겠습니까?")
        }

        if(showDeleteUserDialog) {
            checkCancleDialog(onCheck = {
                user!!.delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navCotnroller.navigate("login")
                        }
                    }
            }, onDismiss = { showDeleteUserDialog = false }, dialogText = "정말로 탈퇴하시겠습니까?")
        }

    }


@Preview(showBackground = true)
@Composable
fun PreviewTextFielsddExample() {
    val navController = rememberNavController()
    MenuScreen(navController)
}
