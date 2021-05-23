package com.intive.registration.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.registration.Constants.SPACER_HEIGHT
import com.intive.registration.R
import com.intive.registration.components.InputText
import com.intive.registration.fragments.NoCodeFragmentDirections
import com.intive.registration.viewmodels.NoCodeViewModel
import com.intive.ui.components.IntroSection
import com.intive.ui.components.PrimaryButton
import com.intive.ui.components.TitleText

@ExperimentalComposeUiApi
@Composable
fun NoCodeScreen(viewmodel: NoCodeViewModel, navController: NavController) {
    val scrollState = rememberScrollState()

    val email: String by viewmodel.email.observeAsState("")

    val formValid = remember { mutableStateOf(false) }
    val formValidChanged: (Boolean) -> Unit = {
        formValid.value = it
    }
    val formChecker: () -> Unit = {
        formValidChanged(viewmodel.isEmailValid())
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        IntroSection(
            title = stringResource(R.string.no_code_title),
            text = stringResource(R.string.no_code_subtitle)
        )
        EmailInput(email, viewmodel, formChecker)
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        PrimaryButton(
            text = stringResource(R.string.send_code_button)
        ) {
            viewmodel.sendRequestForCode()
            val action = NoCodeFragmentDirections.actionVerifyEmailAgain(email, viewmodel.login)
            navController.navigate(action)
        }
    }
}

@ExperimentalComposeUiApi
@Composable
private fun EmailInput(
    email: String,
    viewmodel: NoCodeViewModel,
    formChecker: () -> Unit
) {
    InputText(
        text = email,
        onTextChange = {
            //enable butten when user enter correct email
            viewmodel.onEmailChange(it)
            formChecker()
        },
        label = stringResource(R.string.email_hint),
        isValid = viewmodel::isEmailValid,
        formChecker = formChecker,
        keyboardType = KeyboardType.Email
    )
}