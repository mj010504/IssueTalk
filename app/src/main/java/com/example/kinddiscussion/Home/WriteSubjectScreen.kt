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
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.example.kinddiscussion.R
import com.example.kinddiscussion.ui.theme.selectedColor


@Composable
fun WriteSubjectScreen(
    navController : NavController

) {
    BackHandler {
        navController.popBackStack()
    }
    var selectedField  by remember { mutableStateOf<String?>(null) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        IconButton(onClick= {navController.popBackStack()}) {
            Icon(painter = painterResource(id = R.drawable.arrow_back), contentDescription = null,
                modifier =  Modifier.padding(top = 4.dp, start = 6.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))
        fieldDropDownMenu(onFieldChange = {selectedField = it})
        subjectTextField()
        registerSubjectButton()
    }
}

@Composable
fun registerSubjectButton (

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 31.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        OutlinedButton(
            onClick ={},
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

@Composable
fun fieldDropDownMenu(
    onFieldChange : (String?) -> Unit
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


                Text(text = "분야", style = TextStyle(fontSize = 20.sp))
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    painter = painterResource(R.drawable.arrow_drop_down),
                    contentDescription = null
                )

        }

        val fields = listOf("전체", "사회", "정치", "경제", "연예", "기타")
        DropdownMenu(
            modifier = Modifier
                .wrapContentSize(),
            expanded = isDropDownMenuExpanded,
            onDismissRequest = { isDropDownMenuExpanded = false }

        ) {
            fields.forEach{fieldName ->
                DropdownMenuItem(onClick = {onFieldChange(fieldName)}) {
                    Text(fieldName)
                }
            }
        }
    }

}


@Composable
fun subjectTextField ()
{
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(top = 30.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { newText -> text = newText },
            label = { Text("흥미로운 주제를 입력해보세요!") },
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
        )

    }
}



@Preview(showBackground = true)
@Composable
fun PreviewTextFieldExample() {
    val navController = rememberNavController()
   WriteSubjectScreen(navController)
}