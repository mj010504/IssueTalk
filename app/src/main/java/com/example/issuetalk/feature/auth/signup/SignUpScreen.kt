package com.example.issuetalk.feature.auth.signup


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue

import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.issuetalk.R
import com.example.issuetalk.core.designsystem.component.checkDialog
import com.example.issuetalk.core.designsystem.theme.lightRed
import com.example.issuetalk.core.designsystem.theme.primaryColor
import com.example.issuetalk.core.designsystem.theme.subColor
import com.example.issuetalk.feature.auth.signup.SignUpViewModel.Gender


@Composable
fun SignUpRoute(
    navigateToHome: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val nameText by viewModel.nameText.collectAsStateWithLifecycle()
    val isNameValid by viewModel.isNameValid.collectAsStateWithLifecycle()
    val gender by viewModel.gender.collectAsStateWithLifecycle()
    val birthYear by viewModel.birthYear.collectAsStateWithLifecycle()

    var dialogMessage by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is SignUpViewModel.SignUpEvent.NavigateToHome -> navigateToHome()
                is SignUpViewModel.SignUpEvent.ShowDialog -> dialogMessage = event.message
            }
        }
    }

    SignUpScreen(
        nameText,
        isNameValid,
        gender,
        birthYear,
        viewModel::setBirthYear,
        viewModel::setGender,
        viewModel::setNameText,
        viewModel::signUp,
    )

    dialogMessage?.let {
        checkDialog(
            onDismiss = { dialogMessage = null },
            dialogText = it
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    nameText: String,
    isNameValid: Boolean,
    gender: Gender?,
    birthYear : String,
    onBirthYearChange : (String) -> Unit,
    onGenderChange: (Gender) -> Unit,
    onNameChange: (String) -> Unit,
    signUp: () -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    var showBottomBirthYearSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()


    val signUpAvailability = isNameValid && gender != null
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState()),
    ) {


        CenterAlignedTopAppBar(
            title = {
                Row(
                    modifier = Modifier.wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Icon(
                        painter = painterResource(id = R.drawable.issuetalk),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                    )
                    Spacer(Modifier.width(3.dp))
                    Text(
                        text = stringResource(id = R.string.sign_up),
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                        color = Color.Black
                    )
                }
            },

            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
            ),
            windowInsets = WindowInsets(top = 0.dp, bottom = 0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawLine(
                        color = Color.Gray,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                }
        )
        Spacer(Modifier.height(20.dp))
        Text(
            "환영합니다!",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(start = 20.dp)
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = buildAnnotatedString {

                withStyle(
                    style = SpanStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraLight,
                        color = primaryColor
                    )
                ) {
                    append("이슈토크")
                }
                withStyle(style = SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.ExtraLight)) {
                    append("에서 당신의 다양한 생각을 공유해주세요.")
                }
            },
            modifier = Modifier.padding(start = 20.dp)
        )
        Spacer(Modifier.height(40.dp))
        Text(
            text = "이름", modifier = Modifier
                .padding(start = 20.dp),
            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            value = nameText,
            onValueChange = { onNameChange(it) },
            placeholder = { Text("이름을 입력해주세요", style = TextStyle(fontSize = 14.sp)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(50.dp),
            maxLines = 1,
            textStyle = TextStyle(fontSize = 14.sp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedIndicatorColor = subColor,
                unfocusedIndicatorColor = Color.Gray
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
        )
        Spacer(Modifier.height(5.dp))
        if (!isNameValid && nameText.isNotEmpty()) Text(
            text = "이름은 1 ~ 12자 사이만 가능합니다", modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            style = TextStyle(fontSize = 12.sp, color = lightRed)
        )
        Spacer(Modifier.height(15.dp))
        Text(
            text = "성별", modifier = Modifier
                .padding(start = 20.dp),
            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.man),
                    contentDescription = "남자",
                    modifier = Modifier
                        .size(120.dp)
                        .clickable {
                            onGenderChange(Gender.MALE)
                        },
                    colorFilter = if (gender != Gender.MALE)  ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) }) else null


                )
                Spacer(Modifier.height(8.dp))
              Text(
                    "남자",
                    style = TextStyle(fontSize = 16.sp),
                  color = if(gender == Gender.MALE) Color.Black else Color.Gray
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.woman),
                    contentDescription = "여자",
                    modifier = Modifier
                        .size(120.dp)
                        .clickable {
                            onGenderChange(Gender.FEMALE)
                        },
                    colorFilter = if (gender != Gender.FEMALE) ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) }) else null
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "여자",
                    style = TextStyle(fontSize = 16.sp),
                    color = if(gender == Gender.FEMALE) Color.Black else Color.Gray
                )
            }


        }
        Spacer(Modifier.height(15.dp))
        Text(
            text = "출생연도", modifier = Modifier
                .padding(start = 20.dp),
            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = birthYear,
            singleLine = true,
            readOnly = true,
            onValueChange = {  },
            placeholder = { Text("출생연도를 입력해주세요", style = TextStyle(fontSize = 14.sp)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(50.dp)
                .clickable {
                    showBottomBirthYearSheet = true
                },
            maxLines = 1,
            textStyle = TextStyle(fontSize = 14.sp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedIndicatorColor = subColor,
                unfocusedIndicatorColor = Color.Gray
            ),
        )

        Spacer(Modifier.weight(1f))
        Button(
            onClick = {
                if (signUpAvailability) signUp()
            },
            colors = if (signUpAvailability) ButtonDefaults.buttonColors(subColor) else ButtonDefaults.buttonColors(
                Color.Gray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .clip(RoundedCornerShape(4.dp))
        ) {
            Text(
                stringResource(id = R.string.sign_up_finish),
                color = Color.White,
                style = TextStyle(fontSize = 20.sp)
            )
        }
        Spacer(modifier = Modifier.height(60.dp))
    }

}



@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    SignUpScreen("", false, Gender.FEMALE, "", {}, {}, {}, {})
}



