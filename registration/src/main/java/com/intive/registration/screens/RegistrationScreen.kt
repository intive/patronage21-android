package com.intive.registration.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.registration.viewmodels.RegistrationViewModel
import com.intive.ui.TitleText
import com.intive.registration.R
import com.intive.registration.components.*
import com.intive.registration.fragments.RegistrationFragmentDirections


@Composable
fun RegistrationScreen(viewmodel: RegistrationViewModel, navController: NavController? = null) {
    val scrollState = rememberScrollState()
    val title: String by viewmodel.title.observeAsState(stringArrayResource(R.array.titles_array)[0])
    val firstName: String by viewmodel.firstName.observeAsState("")
    val lastName: String by viewmodel.lastName.observeAsState("")
    val email: String by viewmodel.email.observeAsState("")
    val phoneNumber: String by viewmodel.phoneNumber.observeAsState("")
    val login: String by viewmodel.login.observeAsState("")
    val password: String by viewmodel.password.observeAsState("")
    val confirmPassword: String by viewmodel.confirmPassword.observeAsState("")
    val githubUrl: String by viewmodel.githubUrl.observeAsState("")
    val rodoAgree: Boolean by viewmodel.rodoAgree.observeAsState(false)
    val regulationsAgree: Boolean by viewmodel.regulationsAgree.observeAsState(false)

    val formValid = remember { mutableStateOf(true) }
    val formValidChanged: (Boolean) -> Unit = {
        formValid.value = it
    }
    val formChecker: () -> Unit = {
        formValidChanged(viewmodel.validateForm())
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Logo()
        CustomSpacer()
        TitleText(text = stringResource(R.string.registration_title), modifier = Modifier)
        CustomSpacer()
        Text(text = stringResource(R.string.registration_subtitle))
        CustomSpacer()
        TitlesSpinner(title, viewmodel::onTitleChange)
        CustomSpacer()
        FirstNameInput(firstName, viewmodel, formChecker)
        CustomSpacer()
        LastNameInput(lastName, viewmodel, formChecker)
        CustomSpacer()
        EmailInput(email, viewmodel, formChecker)
        CustomSpacer()
        PhoneNumberInput(phoneNumber, viewmodel, formChecker)
        CustomSpacer()
        TechnologiesList(
            viewmodel.availableTechnologies,
            viewmodel::updateTechnologies,
            viewmodel::validateTechnologies,
            formChecker
        )
        CustomSpacer()
        CodeVerificationInput(login, viewmodel, formChecker)
        CustomSpacer()
        PasswordInput(password, viewmodel, formChecker)
        CustomSpacer()
        ConfirmPasswordInput(confirmPassword, viewmodel, formChecker)
        CustomSpacer()
        GithubInput(githubUrl, viewmodel, formChecker)
        CustomSpacer()
        AgreeCheckBox(
            rodoAgree,
            viewmodel::onRodoAgreeChange,
            stringResource(R.string.rodo_agree_text),
            formChecker
        )
        CustomSpacer()
        AgreeCheckBox(
            regulationsAgree,
            viewmodel::onRegulationsAgreeChange,
            stringResource(R.string.regulations_agree_text),
            formChecker
        )
        CustomSpacer()
        CustomButton(
            text = stringResource(R.string.create_account_button),
            onClick = {
                val action = RegistrationFragmentDirections.actionVerifyEmail(email)
                navController?.navigate(action)
            },
            enabled = formValid.value
        )
    }
}

@Composable
private fun Logo() {
    Image(
        painter = painterResource(R.drawable.logo),
        contentDescription = "Logo",
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun PhoneNumberInput(
    phoneNumber: String,
    viewmodel: RegistrationViewModel,
    formChecker: () -> Unit
) {
    InputText(
        phoneNumber,
        viewmodel::onPhoneNumberChange,
        stringResource(R.string.phone_number_hint),
        viewmodel::validatePhoneNumber,
        formChecker,
        KeyboardType.Phone
    )
}

@Composable
private fun GithubInput(
    githubUrl: String,
    viewmodel: RegistrationViewModel,
    formChecker: () -> Unit
) {
    InputText(
        githubUrl,
        viewmodel::onGithubUrlChange,
        stringResource(R.string.github_url_hint),
        viewmodel::validateGithubUrl,
        formChecker
    )
}

@Composable
private fun ConfirmPasswordInput(
    confirmPassword: String,
    viewmodel: RegistrationViewModel,
    formChecker: () -> Unit
) {
    InputText(
        confirmPassword,
        viewmodel::onConfirmPasswordChange,
        stringResource(R.string.confirm_password_hint),
        viewmodel::validateConfirmPassword,
        formChecker,
        KeyboardType.Password,
        PasswordVisualTransformation()
    )
}

@Composable
private fun PasswordInput(
    password: String,
    viewmodel: RegistrationViewModel,
    formChecker: () -> Unit
) {
    InputText(
        password,
        viewmodel::onPasswordChange,
        stringResource(R.string.password_hint),
        viewmodel::validatePassword,
        formChecker,
        KeyboardType.Password,
        PasswordVisualTransformation()
    )
}

@Composable
private fun CodeVerificationInput(
    login: String,
    viewmodel: RegistrationViewModel,
    formChecker: () -> Unit
) {
    InputText(
        login,
        viewmodel::onLoginChange,
        stringResource(R.string.login_hint),
        viewmodel::validateLogin,
        formChecker
    )
}

@Composable
private fun EmailInput(
    email: String,
    viewmodel: RegistrationViewModel,
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

@Composable
private fun LastNameInput(
    lastName: String,
    viewmodel: RegistrationViewModel,
    formChecker: () -> Unit
) {
    InputText(
        lastName,
        viewmodel::onLastNameChange,
        stringResource(R.string.last_name_hint),
        viewmodel::validateLastName,
        formChecker
    )
}

@Composable
private fun FirstNameInput(
    firstName: String,
    viewmodel: RegistrationViewModel,
    formChecker: () -> Unit
) {
    InputText(
        firstName,
        viewmodel::onFirstNameChange,
        stringResource(R.string.first_name_hint),
        viewmodel::validateFirstName,
        formChecker
    )
}
