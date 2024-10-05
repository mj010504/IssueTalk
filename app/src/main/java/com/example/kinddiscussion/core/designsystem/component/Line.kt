package com.example.kinddiscussion.core.designsystem.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun blackLine(

)  {
    Divider(
        color = Color.Black,
        thickness = 1.dp,
        modifier = Modifier.padding(vertical = 14.dp)
    )
}
@Composable
fun blackLine2() {
    Divider(
        color = Color.Black,
        thickness = 1.dp,
    )
}

@Composable
fun grayLine(
    modifier : Modifier = Modifier
)  {
    Divider(
        color = Color.LightGray,
        thickness = 0.55.dp,
        modifier = modifier

    )
}