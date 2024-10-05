package com.example.kinddiscussion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kinddiscussion.core.designsystem.theme.selectedColor

@Composable
fun testScreen(

) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
       var commentText by remember { mutableStateOf("")}

        Text("dsf")

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {

            items(70) { index ->
                Text("index $index")

            }
        }

        OutlinedTextField(
            value = commentText,
            onValueChange = { newText -> commentText = newText },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = selectedColor,
                unfocusedBorderColor = Color.Gray

            ),
            modifier = Modifier
                .fillMaxWidth()
             ,
            placeholder = { Text("댓글 작성하기") },
            shape = RoundedCornerShape(15.dp),
            textStyle = TextStyle(fontSize = 16.sp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTextFieldssddExample() {
    testScreen()
}
