package com.example.kinddiscussion.core.designsystem.component

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import com.example.kinddiscussion.core.designsystem.theme.selectedColor

@Composable
fun checkDialog(
    onDismiss : () -> Unit ,dialogText : String
) {
    AlertDialog(
        onDismissRequest = { onDismiss()},
        text = { Text(dialogText) },
        confirmButton = {
            TextButton(onClick = {  onDismiss() } ) {
                Text("확인", color = selectedColor)
            }
        }
    )
}

@Composable
fun checkCancleDialog (
    onCheck : () -> Unit, onDismiss : () -> Unit, dialogText : String
) {
    AlertDialog(
        onDismissRequest = {onDismiss()},
        text = { Text(dialogText) },
        confirmButton = {
            TextButton(onClick = {  onCheck() } ) {
                Text("확인", color = selectedColor)
            }
        },
        dismissButton = {
            TextButton(onClick = {  onDismiss() } ) {
                Text("취소", color = selectedColor)
            }
        }
    )
}


