package com.intive.registration.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.intive.registration.components.InputText
import com.intive.registration.viewmodels.EmailVerificationViewModel
import com.intive.ui.TitleText

@Composable
fun EmailVerificationScreen(viewmodel: EmailVerificationViewModel, navController: NavController? = null) {
    val scrollState = rememberScrollState()

    val code: String by viewmodel.code.observeAsState("")

    val formValid = remember { mutableStateOf(true) }
    val formValidChanged: (Boolean) -> Unit = {
        formValid.value = it
    }
    val formChecker: () -> Unit = {
        formValidChanged(viewmodel.validateCode())
    }
    MaterialTheme {
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        TitleText(text = stringResource(R.string.email_verification_title), modifier = Modifier)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = stringResource(R.string.email_verification_subtitle, viewmodel.email))
        Spacer(modifier = Modifier.height(8.dp))
        CodeVerificationInput(code, viewmodel, formChecker)
        Spacer(modifier = Modifier.height(8.dp))
        CustomButton(
            text = stringResource(R.string.confirm_code_button),
            {
//                val action =
//                if(viewmodel.isCodeCorrect()) {
//                    EmailVerificationFragmentDirections.actionSuccess()
//                }
//                else {
//                    EmailVerificationFragmentDirections.actionError()
//                }
//                navController?.navigate(action)
            },
            formValid.value
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomButton(
            text = stringResource(R.string.no_code_button),
            {
//                val action = RegistrationEmailVerificationFragmentDirections
//                    .actionNoCode(viewmodel.email)
//                navController?.navigate(action)
            }
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
        viewmodel::onCodeChange,
        stringResource(R.string.code_verification),
        viewmodel::validateCode,
        formChecker,
        KeyboardType.Number
    )
}