package com.example.issuetalk.core.designsystem.component

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.issuetalk.R
import com.example.issuetalk.core.designsystem.theme.subColor

@Composable
fun checkDialog(
    onDismiss: () -> Unit, dialogText: String
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        text = { Text(dialogText) },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(stringResource(R.string.dialog_confirm), color = subColor)
            }
        }
    )
}

@Composable
fun checkCancleDialog(
    onCheck: () -> Unit, onDismiss: () -> Unit, dialogText: String
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        text = { Text(dialogText) },
        confirmButton = {
            TextButton(onClick = { onCheck() }) {
                Text(stringResource(R.string.dialog_confirm), color = subColor)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(stringResource(R.string.dialog_cancel), color = subColor)
            }
        }
    )
}


