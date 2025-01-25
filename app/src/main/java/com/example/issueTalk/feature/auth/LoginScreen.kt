package com.example.issueTalk.feature.auth


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.issueTalk.feature.auth.LoginViewModel.LoginEvent
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.issueTalk.R
import com.example.issueTalk.core.common.util.clickWithRipple
import com.example.issueTalk.core.designsystem.component.checkDialog
import com.example.issueTalk.core.designsystem.theme.primaryColor
import com.example.issueTalk.core.designsystem.theme.subColor




@Composable
fun LoginRoute(
    navigateToHome: () -> Unit,
    navigateToSignUp: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val emailText by viewModel.emailText.collectAsStateWithLifecycle()
    val passwordText by viewModel.passwordText.collectAsStateWithLifecycle()
    var dialogMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is LoginEvent.NavigateToHome -> navigateToHome()
                is LoginEvent.NavigateToSignUp -> navigateToSignUp()
                is LoginEvent.ShowDialog -> dialogMessage = event.message
            }
        }
    }

    LoginScreen(
        emailText,
        passwordText,
        onEmailTextChanged = viewModel::setEmailText,
        onPasswordTextChanged = viewModel::setPasswordText,
        login = viewModel::login,
        navigateToSignUp = viewModel::navigateToSignUp,
        navigateToHome = viewModel::navigateToHome
    )

    dialogMessage?.let {
        checkDialog(
            onDismiss = { dialogMessage = null },
            dialogText = it
        )
    }
}

@Composable
fun LoginScreen(
    emailText: String,
    passwordText: String,
    onEmailTextChanged: (String) -> Unit,
    onPasswordTextChanged: (String) -> Unit,
    login: () -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToHome: () -> Unit,
) {


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Image(
            painter = painterResource(id = R.drawable.issuetalk),
            contentDescription = null,
            modifier = Modifier
                .width(120.dp)
                .height(120.dp),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.app_name),
            style = TextStyle(fontSize = 25.sp, fontFamily = FontFamily(Font(R.font.app_title)))
        )
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = subColor,
                unfocusedBorderColor = Color.Gray,
                cursorColor = subColor
            ),
            value = emailText,
            onValueChange = { onEmailTextChanged(it) },
            textStyle = TextStyle(fontSize = 14.sp),
            placeholder = { Text("이메일", style = TextStyle(fontSize = 14.sp)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 34.dp)
                .height(50.dp)

        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = subColor,
                unfocusedBorderColor = Color.Gray,
                cursorColor = subColor
            ),
            value = passwordText,
            onValueChange = { onPasswordTextChanged(it) },
            placeholder = { Text("비밀번호", style = TextStyle(fontSize = 14.sp)) },
            textStyle = TextStyle(fontSize = 14.sp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 34.dp)
                .height(50.dp)

        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 34.dp),
        ) {
            Text(
                stringResource(id = R.string.signUp),
                color = Color.Black,
                style = TextStyle(fontSize = 12.sp),
                modifier = Modifier.clickWithRipple {
                    navigateToSignUp()
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                stringResource(id = R.string.find_password),
                color = subColor,
                style = TextStyle(fontSize = 12.sp),
                modifier = Modifier.clickWithRipple { }
            )

        }

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = login,
            colors = ButtonDefaults.buttonColors(primaryColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 34.dp, end = 34.dp)
                .clip(RoundedCornerShape(35.dp))
        ) {
            Text(
                stringResource(id = R.string.login),
                color = Color.White,
                style = TextStyle(fontSize = 20.sp)
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text("또는")
        Spacer(modifier = Modifier.height(20.dp))
        Card(
            elevation = 3.dp
        ) {
            Image(
                painter = painterResource(id = R.drawable.google_login),
                contentDescription = null,
                modifier = Modifier.clickWithRipple {

                }

            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Text(
            "둘러보기",
            color = Color.Black,
            style = TextStyle(fontSize = 16.sp),
            modifier = Modifier.clickWithRipple {
                navigateToHome()
            }

        )
        Spacer(modifier = Modifier.height(70.dp))
    }


}




