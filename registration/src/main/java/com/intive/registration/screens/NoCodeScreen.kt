package com.intive.registration.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
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
import com.intive.registration.fragments.NoCodeFragmentDirections
import com.intive.registration.viewmodels.NoCodeViewModel
import com.intive.ui.TitleText

@Composable
fun NoCodeScreen(viewmodel: NoCodeViewModel, navController: NavController? = null) {
    val scrollState = rememberScrollState()

    val email: String by viewmodel.email.observeAsState("")

    val formValid = remember { mutableStateOf(false) }
    val formValidChanged: (Boolean) -> Unit = {
        formValid.value = it
    }
    val formChecker: () -> Unit = {
        formValidChanged(viewmodel.validateEmail())
    }
    MaterialTheme {
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        TitleText(text = stringResource(R.string.no_code_title), modifier = Modifier)
        CustomSpacer()
        EmailInput(email, viewmodel, formChecker)
        CustomSpacer()
        CustomButton(
            text = stringResource(R.string.send_code_button),
            onClick = {
                val action = NoCodeFragmentDirections.actionVerifyEmailAgain(email)
                navController?.navigate(action)
            },
            enabled = formValid.value
        )
    }
}

@Composable
private fun EmailInput(
    email: String,
    viewmodel: NoCodeViewModel,
    formChecker: () -> Unit
) {
    InputText(
        email,
        viewmodel::onEmailChange,
        stringResource(R.string.email_hint),
        viewmodel::validateEmail,
        formChecker,
        KeyboardType.Email
    )
}