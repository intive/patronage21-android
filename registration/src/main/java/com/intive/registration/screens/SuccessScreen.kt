package com.intive.registration.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.intive.registration.R
import com.intive.registration.components.CustomButton

@Composable
fun SuccessScreen() {
    AlertDialogSample()
}


@Composable
fun AlertDialogSample() {
    Column {
        val openDialog = remember { mutableStateOf(true) }

        if (openDialog.value) {

            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                    openDialog.value = false
                },
                title = {
                    Text(stringResource(R.string.registration_succes_title))
                },
                text = {
                    Text(stringResource(R.string.registration_success_subtitle))
                },
                confirmButton = {
                    CustomButton(
                        onClick = {
                            openDialog.value = false
                        }, text = stringResource(R.string.close_dialog), enabled = true
                    )
                },
            )
        }
    }
}