package com.example.kinddiscussion.Home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kinddiscussion.Firebase.Subject
import com.example.kinddiscussion.Home.viewModel.SubjectViewModel

import com.example.kinddiscussion.R
import com.example.kinddiscussion.checkDialog
import com.example.kinddiscussion.getCurrentDateFormatted
import com.example.kinddiscussion.ui.theme.selectedColor
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun WriteSubjectScreen(
    navController : NavController,
    subjectViewModel: SubjectViewModel = viewModel()

) {
    BackHandler {
        navController.popBackStack()
    }
    var selectedField  by remember { mutableStateOf<String>("분야") }
    var subjectText by remember { mutableStateOf("") }
    var agreeText by remember {mutableStateOf("")}
    var disagreeText by remember {mutableStateOf("")}

    var dialogText by  remember {mutableStateOf("")}
    var showDialog by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    var isClickable by remember { mutableStateOf(true) }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        IconButton(onClick= {
            if(isClickable) {
                isClickable = false
                navController.popBackStack()
            }

        }) {
            Icon(painter = painterResource(id = R.drawable.arrow_back), contentDescription = null,
                modifier =  Modifier.padding(top = 4.dp, start = 6.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))
        fieldDropDownMenu(selectedField ,onFieldChange = {selectedField = it})

        Text("주제", style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold),
            modifier =  Modifier.padding(top = 20.dp, start = 24.dp))

        OutlinedTextField(
            value = subjectText,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = selectedColor,
                unfocusedBorderColor = Color.Gray
            ),
            textStyle = TextStyle(fontSize = 20.sp),
            onValueChange = { newText -> subjectText = newText },
            placeholder = { Text("흥미로운 주제를 입력해보세요!") },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(start = 24.dp, end = 24.dp)
        )

        Text("찬성 의견", style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold),
            modifier =  Modifier.padding(top = 20.dp, start = 24.dp))

        OutlinedTextField(
            value = agreeText,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = selectedColor,
                unfocusedBorderColor = Color.Gray,
                cursorColor = selectedColor
            ),
            textStyle = TextStyle(fontSize = 20.sp),
            onValueChange = { newText -> agreeText = newText },
            placeholder = { Text("찬성 측 의견을 작성해주세요.") },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(start = 24.dp, end = 24.dp)
        )


        Text("반대 의견", style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold),
            modifier =  Modifier.padding(top = 20.dp, start = 24.dp))

        OutlinedTextField(
            value = disagreeText,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = selectedColor,
                unfocusedBorderColor = Color.Gray,
                cursorColor = selectedColor
            ),
            textStyle = TextStyle(fontSize = 20.sp),
            onValueChange = { newText -> disagreeText = newText },
            placeholder = { Text("반대 측 의견을 작성해주세요.") },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(start = 24.dp, end = 24.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 31.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            OutlinedButton(
                onClick ={
                    if(selectedField == "분야") {
                        showDialog = true
                        dialogText = "분야를 선택해주세요."
                    }
                    else if(subjectText == "") {
                       showDialog = true
                        dialogText = "주제를 입력해주세요."
                    }
                    else if(agreeText == "" || disagreeText == "") {
                        showDialog = true
                        dialogText = "찬성의견과 반대의견을 작성해주세요."
                    }
                    else {
                        if(isClickable) {
                            val auth = FirebaseAuth.getInstance()
                            val userId = auth.currentUser!!.uid
                            val currentDate = getCurrentDateFormatted()
                            val writeSubject = Subject(
                                title = subjectText,
                                agreeText = agreeText,
                                disagreeText = disagreeText,
                                0,0,0, userId, 0, selectedField, currentDate
                            )
                            val isSuccess = subjectViewModel.writeSubject(writeSubject)
                            if(isSuccess) {
                                navController.popBackStack()
                            }
                            else {
                                showDialog =  true
                                dialogText = "주제 등록에 실패했습니다."}
                        }

                        coroutineScope.launch {
                            delay(300)
                            isClickable = true
                        }
                    }


                },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = selectedColor
                ),
                border = BorderStroke(1.dp, selectedColor),
                modifier = Modifier
                    .width(200.dp)
                    .height(60.dp),
                shape =  RoundedCornerShape(8.dp)

            ) {
                Text(
                    text = "주제 등록하기",
                    fontSize = 19.sp
                )
            }
        }
        }

    if(showDialog) {
        checkDialog(onDismiss = { showDialog = false}, dialogText = dialogText )
    }

}

@Composable
fun fieldDropDownMenu(
    fieldText : String, onFieldChange : (String) -> Unit
) {

    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    Row(modifier = Modifier.padding(start = 24.dp)) {
        OutlinedButton(
            onClick ={ isDropDownMenuExpanded = true },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.Black
            ),
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier
                .width(100.dp)


        ) {


                Text(text = fieldText
                     , style = TextStyle(fontSize = 20.sp))
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    painter = painterResource(R.drawable.arrow_drop_down),
                    contentDescription = null
                )

        }

        val fields = listOf("사회", "정치", "경제", "연예", "기타")
        DropdownMenu(
            modifier = Modifier
                .wrapContentSize(),
            expanded = isDropDownMenuExpanded,
            onDismissRequest = { isDropDownMenuExpanded = false }

        ) {
            fields.forEach{fieldName ->
                DropdownMenuItem(onClick = {
                    onFieldChange(fieldName)
                    isDropDownMenuExpanded = false}
                ) {
                    Text(fieldName)
                }
            }
        }
    }

}



@Preview(showBackground = true)
@Composable
fun PreviewTextFieldExample() {
    val navController = rememberNavController()
   WriteSubjectScreen(navController)
}