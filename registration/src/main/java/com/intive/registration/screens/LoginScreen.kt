package com.intive.registration.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.registration.components.InputText
import com.intive.registration.viewmodels.LoginViewModel
import com.intive.ui.TitleText
import com.intive.registration.R
import com.intive.registration.components.CustomButton


@Composable
fun LoginScreen(viewmodel: LoginViewModel, navController: NavController? = null) {
    val scrollState = rememberScrollState()

    val login: String by viewmodel.login.observeAsState("")
    val password: String by viewmodel.password.observeAsState("")

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        TitleText(text = "Logowanie", modifier = Modifier)
        Spacer(modifier = Modifier.height(8.dp))
        LoginInput(login, viewmodel)
        Spacer(modifier = Modifier.height(8.dp))
        PasswordInput(password, viewmodel)
        Spacer(modifier = Modifier.height(8.dp))
        CustomButton(
            text = stringResource(R.string.login_button),
            {
                //check login & password if correct navigate to HomeScreen
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomButton(
            text = stringResource(R.string.registration_button),
            {
                //navigate to registration form
            }
        )
    }
}

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

@Composable
private fun PasswordInput(
    password: String,
    viewmodel: LoginViewModel
) {
    InputText(
        text = password,
        onTextChange = viewmodel::onLoginChange,
        label = stringResource(R.string.login_label_text),
        keyboardType = KeyboardType.Password
    )
}