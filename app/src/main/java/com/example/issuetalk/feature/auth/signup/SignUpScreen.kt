package com.example.issuetalk.feature.auth.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.issuetalk.R
import com.example.issuetalk.core.designsystem.component.checkDialog
import com.example.issuetalk.core.designsystem.theme.lightRed
import com.example.issuetalk.core.designsystem.theme.primaryColor
import com.example.issuetalk.core.designsystem.theme.subColor

@Composable
fun SignUpRoute(
    navigateToWelcome: () -> Unit,
    popBackStack: () -> Unit,
    viewModel: SignUpViewModel = viewModel()
) {
    val emailText by viewModel.emailText.collectAsStateWithLifecycle()
    val nameText by viewModel.nameText.collectAsStateWithLifecycle()
    val passwordText by viewModel.passwordText.collectAsStateWithLifecycle()
    val passwordCheckText by viewModel.passwordCheckText.collectAsStateWithLifecycle()
    val isNameValid by viewModel.isNameValid.collectAsStateWithLifecycle()
    val isEmailValid by viewModel.isEmailValid.collectAsStateWithLifecycle()
    val isPasswordValid by viewModel.isPasswordValid.collectAsStateWithLifecycle()
    val isPasswordMatch by viewModel.isPasswordMatch.collectAsStateWithLifecycle()
    var dialogMessage by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is SignUpViewModel.SignUpEvent.NavigateToWelcome -> navigateToWelcome()
                is SignUpViewModel.SignUpEvent.ShowDialog -> dialogMessage = event.message
            }
        }
    }

    SignUpScreen(
        emailText,
        nameText,
        passwordText,
        passwordCheckText,
        isNameValid,
        isEmailValid,
        isPasswordValid,
        isPasswordMatch,
        viewModel::signUp,
        viewModel::setEmailText,
        viewModel::setNameText,
        viewModel::setPasswordText,
        viewModel::setPasswordCheckText,
        popBackStack
    )

    dialogMessage?.let {
        checkDialog(
            onDismiss = { dialogMessage = null },
            dialogText = it
        )
    }
}

@Composable
fun SignUpScreen(
    emailText: String,
    nameText: String,
    passwordText: String,
    passwordCheckText: String,
    isNameValid: Boolean,
    isEmailValid: Boolean,
    isPasswordValid: Boolean,
    isPasswordMatch: Boolean,
    signUp: () -> Unit,
    onEmailChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordCheckChange: (String) -> Unit,
    popBackStack: () -> Unit
) {

    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val passwordCheckFocusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = {
                    popBackStack()

                },
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "뒤로 가기",
                    modifier = Modifier
                        .padding(start = 10.dp, top = 15.dp)
                        .width(30.dp)
                        .height(30.dp)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.issuetalk), contentDescription = null,
                tint = Color.Unspecified, modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(
                    id = R.string.sign_up
                ), style = TextStyle(fontSize = 25.sp), fontWeight = FontWeight.Bold
            )

        }
        Spacer(Modifier.height(25.dp))
        Text(
            text = "이름", modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            style = TextStyle(fontSize = 15.sp)
        )
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = subColor,
                unfocusedBorderColor = Color.Gray,
                cursorColor = subColor
            ),
            value = nameText,
            onValueChange = { onNameChange(it) },
            placeholder = { Text("이름을 입력해주세요", style = TextStyle(fontSize = 14.sp)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(50.dp),
            maxLines = 1,
            textStyle = TextStyle(fontSize = 14.sp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { emailFocusRequester.requestFocus() }
            )

        )
        Spacer(Modifier.height(5.dp))
        if (!isNameValid && nameText.isNotEmpty()) Text(
            text = "이름은 1 ~ 12자 사이만 가능합니다", modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            style = TextStyle(fontSize = 12.sp, color = lightRed)
        )
        Spacer(Modifier.height(25.dp))
        Text(
            text = "이메일", modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            style = TextStyle(fontSize = 15.sp)
        )
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = subColor,
                unfocusedBorderColor = Color.Gray,
                cursorColor = subColor
            ),
            value = emailText,
            onValueChange = { onEmailChange(it) },
            placeholder = { Text("예: issueTalk@naver.com", style = TextStyle(fontSize = 14.sp)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(50.dp)
                .focusRequester(emailFocusRequester),
            maxLines = 1,
            textStyle = TextStyle(fontSize = 14.sp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { passwordFocusRequester.requestFocus() }
            )
        )

        Spacer(Modifier.height(5.dp))
        if (!isEmailValid && emailText.isNotEmpty()) Text(
            text = "올바르지 않은 이메일 형식입니다", modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            style = TextStyle(fontSize = 12.sp, color = lightRed)
        )
        Spacer(Modifier.height(25.dp))
        Text(
            text = "비밀번호", modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            style = TextStyle(fontSize = 15.sp)
        )
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = subColor,
                unfocusedBorderColor = Color.Gray,
                cursorColor = subColor
            ),
            value = passwordText,
            onValueChange = { onPasswordChange(it) },
            placeholder = {
                Text(
                    "8~16자의 영문/숫자/특수문자 조합으로 입력",
                    style = TextStyle(fontSize = 14.sp)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(50.dp)
                .focusRequester(passwordFocusRequester),
            maxLines = 1,
            textStyle = TextStyle(fontSize = 14.sp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { passwordCheckFocusRequester.requestFocus() }
            )

        )
        Spacer(Modifier.height(5.dp))
        if (!isPasswordValid && passwordText.isNotEmpty()) Text(
            text = "올바르지 않은 비밀번호 형식입니다", modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            style = TextStyle(fontSize = 12.sp, color = lightRed)
        )
        Spacer(Modifier.height(25.dp))
        Text(
            text = "비밀번호 확인", modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            style = TextStyle(fontSize = 15.sp)
        )
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = subColor,
                unfocusedBorderColor = Color.Gray,
                cursorColor = subColor
            ),
            value = passwordCheckText,
            onValueChange = { onPasswordCheckChange(it) },
            placeholder = { Text("비밀번호를 다시 입력해주세요.", style = TextStyle(fontSize = 14.sp)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(50.dp)
                .focusRequester(passwordCheckFocusRequester),
            maxLines = 1,
            textStyle = TextStyle(fontSize = 14.sp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            )

        )
        Spacer(Modifier.height(5.dp))
        if (!isPasswordMatch && passwordCheckText.isNotEmpty()) Text(
            text = "비밀번호가 일치하지 않습니다", modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            style = TextStyle(fontSize = 12.sp, color = lightRed)
        )

        Spacer(Modifier.weight(1f))
        Button(
            onClick = {
                signUp()
            },
            colors = ButtonDefaults.buttonColors(subColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .clip(RoundedCornerShape(15.dp))
        ) {
            Text(
                stringResource(id = R.string.finish),
                color = Color.White,
                style = TextStyle(fontSize = 20.sp)
            )
        }
        Spacer(modifier = Modifier.height(60.dp))
    }
}




