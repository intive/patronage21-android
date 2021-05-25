package com.intive.registration.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.registration.Constants.SPACER_HEIGHT
import com.intive.registration.components.InputText
import com.intive.registration.viewmodels.LoginViewModel
import com.intive.registration.R
import com.intive.registration.fragments.LoginFragmentDirections
import com.intive.repository.local.LocalRepository
import com.intive.shared.NavigationViewModel
import com.intive.ui.components.PrimaryButton
import com.intive.ui.components.SecondaryButton
import com.intive.ui.components.TitleText


@ExperimentalComposeUiApi
@Composable
fun LoginScreen(viewmodel: LoginViewModel, navController: NavController) {
    val scrollState = rememberScrollState()

    val login: String by viewmodel.login.observeAsState("")
    val password: String by viewmodel.password.observeAsState("")


    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        TitleText(text = stringResource(R.string.login_title), modifier = Modifier)
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        LoginInput(login, viewmodel)
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        PasswordInput(password, viewmodel)
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        PrimaryButton(
            text = stringResource(R.string.login_button)
        ) {
            //check login & password if correct navigate to HomeScreen
        }
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        SecondaryButton(
            text = stringResource(R.string.registration_button)
        ) {
            val action = LoginFragmentDirections.actionRegister()
            navController.navigate(action)
        }
    }
}

@ExperimentalComposeUiApi
@Composable
private fun LoginInput(
    login: String,
    viewmodel: LoginViewModel
) {
    InputText(
        text = login,
        onTextChange = viewmodel::onLoginChange,
        label = stringResource(R.string.login_label_text)
    )
}

@ExperimentalComposeUiApi
@Composable
private fun PasswordInput(
    password: String,
    viewmodel: LoginViewModel
) {
    InputText(
        text = password,
        onTextChange = viewmodel::onPasswordChange,
        label = stringResource(R.string.password_label_text),
        keyboardType = KeyboardType.Password
    )
}