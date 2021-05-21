package com.intive.registration.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.intive.registration.R
import com.intive.registration.viewmodels.SharedViewModel
import com.intive.shared.NavigationViewModel
import com.intive.shared.navigationModule
import com.intive.ui.components.PrimaryButton

@Composable
fun SuccessScreen(sharedViewModel: SharedViewModel, navigationViewModel: NavigationViewModel) {
    AlertDialogSample(sharedViewModel, navigationViewModel)
}


@Composable
fun AlertDialogSample(sharedViewModel: SharedViewModel, navigationViewModel: NavigationViewModel) {
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
                    PrimaryButton(
                        text = stringResource(R.string.close_dialog)
                    ) {
                        openDialog.value = false
                        navigationViewModel.loginUser(sharedViewModel.login)
                    }
                },
            )
        }
    }
}