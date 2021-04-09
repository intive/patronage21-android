package com.intive.registration.screens

import androidx.compose.foundation.Image
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
    MaterialTheme {
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Logo()
        Spacer(modifier = Modifier.height(8.dp))
        TitleText(text = stringResource(R.string.registration_title), modifier = Modifier)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = stringResource(R.string.registration_subtitle))
        Spacer(modifier = Modifier.height(8.dp))
        TitlesSpinner(title, viewmodel::onTitleChange)
        Spacer(modifier = Modifier.height(8.dp))
        FirstNameInput(firstName, viewmodel, formChecker)
        Spacer(modifier = Modifier.height(8.dp))
        LastNameInput(lastName, viewmodel, formChecker)
        Spacer(modifier = Modifier.height(8.dp))
        EmailInput(email, viewmodel, formChecker)
        Spacer(modifier = Modifier.height(8.dp))
        PhoneNumberInput(phoneNumber, viewmodel, formChecker)
        Spacer(modifier = Modifier.height(8.dp))
        TechnologiesList(
            viewmodel.availableTechnologies,
            viewmodel::updateTechnologies,
            viewmodel::validateTechnologies,
            formChecker
        )
        Spacer(modifier = Modifier.height(8.dp))
        CodeVerificationInput(login, viewmodel, formChecker)
        Spacer(modifier = Modifier.height(8.dp))
        PasswordInput(password, viewmodel, formChecker)
        Spacer(modifier = Modifier.height(8.dp))
        ConfirmPasswordInput(confirmPassword, viewmodel, formChecker)
        Spacer(modifier = Modifier.height(8.dp))
        GithubInput(githubUrl, viewmodel, formChecker)
        Spacer(modifier = Modifier.height(8.dp))
        AgreeCheckBox(
            rodoAgree,
            viewmodel::onRodoAgreeChange,
            stringResource(R.string.rodo_agree_text),
            formChecker
        )
        Spacer(modifier = Modifier.height(8.dp))
        AgreeCheckBox(
            regulationsAgree,
            viewmodel::onRegulationsAgreeChange,
            stringResource(R.string.regulations_agree_text),
            formChecker
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomButton(
            text = stringResource(R.string.create_account_button),
            {
                val action = RegistrationFragmentDirections.actionVerifyEmail(email)
                navController?.navigate(action)
            },
            formValid.value
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
