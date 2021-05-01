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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.registration.Constants.SPACER_HEIGHT
import com.intive.registration.R
import com.intive.registration.components.CustomButton
import com.intive.registration.components.InputText
import com.intive.registration.fragments.EmailVerificationFragmentDirections
import com.intive.registration.viewmodels.*
import com.intive.repository.util.Resource
import com.intive.ui.components.TitleText

@Composable
fun EmailVerificationScreen(
    viewmodel: EmailVerificationViewModel,
    navController: NavController,
    sharedViewModel: SharedViewModel
) {
    val scrollState = rememberScrollState()

    val code: String by viewmodel.code.observeAsState("")

    val response = viewmodel.responseState.value
    val formValid = remember { mutableStateOf(true) }
    val formValidChanged: (Boolean) -> Unit = {
        formValid.value = it
    }
    val formChecker: () -> Unit = {
        formValidChanged(viewmodel.isCodeValid())
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        TitleText(text = stringResource(R.string.email_verification_title), modifier = Modifier)
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        when(response) {
            is Resource.Error -> {
                Text(
                    text = response.message?: stringResource(R.string.internet_connection_error),
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(SPACER_HEIGHT))
            }
            is Resource.Success -> {
                sharedViewModel.successDialogState = RegistrationSuccessDialogState.SHOW_DIALOG
                val action = EmailVerificationFragmentDirections.actionSuccess()
                navController.navigate(action)
            }
        }
        Text(text = stringResource(R.string.email_verification_subtitle, viewmodel.email))
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        CodeVerificationInput(code, viewmodel, formChecker)
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        CustomButton(
            text = if (response !is Resource.Loading) stringResource(R.string.confirm_code_button)
            else stringResource(R.string.processing),
            onClick = {
                viewmodel.sendCodeToServer()
            },
            enabled = formValid.value
        )
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        CustomButton(
            text = stringResource(R.string.no_code_button),
            onClick = {
                viewmodel.resetResponseState()
                val action = EmailVerificationFragmentDirections
                    .actionNoCode(viewmodel.email)
                navController.navigate(action)
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
        text = code,
        onTextChange = {
            //enable button when user enter eighth digit
            viewmodel.onCodeChange(it)
            formChecker()
        },
        label = stringResource(R.string.code_verification),
        isValid = viewmodel::isCodeValid,
        formChecker = formChecker,
        keyboardType = KeyboardType.Number
    )
}