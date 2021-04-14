package com.intive.registration.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.registration.R
import com.intive.registration.components.CustomButton
import com.intive.registration.components.CustomSpacer
import com.intive.registration.components.InputText
import com.intive.registration.fragments.EmailVerificationFragmentDirections
import com.intive.registration.viewmodels.EmailVerificationViewModel
import com.intive.registration.viewmodels.RegistrationSuccessDialog
import com.intive.registration.viewmodels.SharedViewModel
import com.intive.ui.TitleText

@Composable
fun EmailVerificationScreen(
    viewmodel: EmailVerificationViewModel,
    navController: NavController? = null,
    sharedViewModel: SharedViewModel
) {
    val scrollState = rememberScrollState()

    val code: String by viewmodel.code.observeAsState("")

    val formValid = remember { mutableStateOf(true) }
    val formValidChanged: (Boolean) -> Unit = {
        formValid.value = it
    }
    val formChecker: () -> Unit = {
        formValidChanged(viewmodel.validateCode())
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        TitleText(text = stringResource(R.string.email_verification_title), modifier = Modifier)
        CustomSpacer()
        Text(text = stringResource(R.string.email_verification_subtitle, viewmodel.email))
        CustomSpacer()
        CodeVerificationInput(code, viewmodel, formChecker)
        CustomSpacer()
        CustomButton(
            text = stringResource(R.string.confirm_code_button),
            onClick = {
                val action =
                    if (viewmodel.isCodeCorrect()) {
                        sharedViewModel.successDialog = RegistrationSuccessDialog.SHOW_DIALOG
                        EmailVerificationFragmentDirections.actionSuccess()
                    } else {
                        EmailVerificationFragmentDirections.actionError()
                    }
                navController?.navigate(action)
            },
            enabled = formValid.value
        )
        CustomSpacer()
        CustomButton(
            text = stringResource(R.string.no_code_button),
            onClick = {
                val action = EmailVerificationFragmentDirections
                    .actionNoCode(viewmodel.email)
                navController?.navigate(action)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
        )
    }
}


@Composable
private fun CodeVerificationInput(
    code: String,
    viewmodel: EmailVerificationViewModel,
    formChecker: () -> Unit
) {
    InputText(
        code,
        {
            //enable button when user enter eighth digit
            viewmodel.onCodeChange(it)
            formChecker()
        },
        stringResource(R.string.code_verification),
        viewmodel::validateCode,
        formChecker,
        KeyboardType.Number
    )
}