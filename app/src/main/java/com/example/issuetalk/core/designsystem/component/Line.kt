package com.example.issuetalk.core.designsystem.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun blackLine(

) {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 14.dp),
        thickness = 1.dp,
        color = Color.Black
    )
}

@Composable
fun blackLine2() {
    HorizontalDivider(
        thickness = 1.dp,
        color = Color.Black
    )
}

@Composable
fun grayLine(
    modifier: Modifier = Modifier
) {
    HorizontalDivider(
        modifier = modifier,
        thickness = 0.55.dp,
        color = Color.LightGray
    )
}