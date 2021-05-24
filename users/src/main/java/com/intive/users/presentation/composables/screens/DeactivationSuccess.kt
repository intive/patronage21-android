package com.intive.users.presentation.composables.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.intive.ui.components.PrimaryButton
import com.intive.users.R
import com.intive.users.presentation.deactivate_user.DeactivateUserViewModel

@Composable
fun DeactivationSuccess(viewModel: DeactivateUserViewModel) {
    Column {
        val openDialog = remember { mutableStateOf(true) }

        if (openDialog.value) {

            AlertDialog(
                onDismissRequest = {},
                title = {
                    Text(stringResource(R.string.successfully_deactivated_account))
                },
                text = {
                    Text(stringResource(R.string.redirect_to_login_screen))
                },
                confirmButton = {
                    PrimaryButton(
                        text = stringResource(R.string.ok)
                    ) {
                        openDialog.value = false
                        viewModel.shouldShowDeactivationSuccessfulDialog.value = false
                        viewModel.onDialogDismiss()
                    }
                },
            )
        }
    }
}