package com.intive.registration.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.intive.registration.components.CustomButton

@Composable
fun SuccessScreen(navController: NavController? = null) {
    //HomeScreen(navController = navController)
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
                    Text("Twoja rejestracja przebiegła pomyślnie!")
                },
                text = {
                    Text("Konto zostało utworzone, możesz korzystać z aplikacji")
                },
                confirmButton = {
                    CustomButton(
                        onClick = {
                            openDialog.value = false
                        }, text = "Zamknij", enabled = true
                    )
                },
            )
        }
    }
}