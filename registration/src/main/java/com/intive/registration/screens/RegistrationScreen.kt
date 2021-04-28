package com.intive.registration.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.registration.Constants.SPACER_HEIGHT
import com.intive.registration.viewmodels.RegistrationViewModel
import com.intive.registration.R
import com.intive.registration.components.*
import com.intive.registration.fragments.RegistrationFragmentDirections
import com.intive.registration.viewmodels.RegistrationFormState
import com.intive.registration.viewmodels.ResponseState
import com.intive.ui.components.Spinner
import com.intive.ui.components.TitleText
import kotlinx.coroutines.launch


@Composable
fun RegistrationScreen(viewmodel: RegistrationViewModel, navController: NavController) {
    val formState by viewmodel.registrationFormState.observeAsState()
    val response by viewmodel.responseState.observeAsState()
    val scrollState = rememberScrollState()
    val titles = stringArrayResource(R.array.titles_array).asList()
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
        formValidChanged(viewmodel.isFormValid())
    }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        if(formState is RegistrationFormState.Error) {
            val action = RegistrationFragmentDirections.actionError((formState as RegistrationFormState.Error).messageResourceId)
            navController.navigate(action)
        }
        if(response is ResponseState.Ok) {
            viewmodel.resetResponseState()
            val action = RegistrationFragmentDirections.actionVerifyEmail(email)
            navController.navigate(action)
        }
        else if(response is ResponseState.Error) {
            viewmodel.resetFormState()
            Text(
                text = (response as ResponseState.Error).message,
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(SPACER_HEIGHT))
            coroutineScope.launch {
                scrollState.scrollTo(0)
            }
        }
        Logo()
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        TitleText(text = stringResource(R.string.registration_title), modifier = Modifier)
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        Text(text = stringResource(R.string.registration_subtitle))
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        Spinner(items = titles, onTitleSelected = viewmodel::onTitleChange)
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        FirstNameInput(firstName, viewmodel, formChecker)
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        LastNameInput(lastName, viewmodel, formChecker)
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        EmailInput(email, viewmodel, formChecker)
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        PhoneNumberInput(phoneNumber, viewmodel, formChecker)
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        when (formState) {
            is RegistrationFormState.Downloading -> {
                Text(stringResource(R.string.downloading_available_tech_groups))
                CircularProgressIndicator()
            }
            is RegistrationFormState.Ok -> {
                TechnologiesList(
                    availableTechnologies = viewmodel.availableTechnologies,
                    onItemSelected = viewmodel::updateTechnologies,
                    isValid = viewmodel::isTechnologiesListValid,
                    formChecker = formChecker
                )
            }
        }
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        CodeVerificationInput(login, viewmodel, formChecker)
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        PasswordInput(password, viewmodel, formChecker)
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        ConfirmPasswordInput(confirmPassword, viewmodel, formChecker)
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        GithubInput(githubUrl, viewmodel, formChecker)
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        AgreeCheckBox(
            agree = rodoAgree,
            onAgreeChange = viewmodel::onRodoAgreeChange,
            label = stringResource(R.string.rodo_agree_text),
            formChecker = formChecker
        )
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        AgreeCheckBox(
            agree = regulationsAgree,
            onAgreeChange = viewmodel::onRegulationsAgreeChange,
            label = stringResource(R.string.regulations_agree_text),
            formChecker = formChecker
        )
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        CustomButton(
            text = if(formState !is RegistrationFormState.Sending) stringResource(R.string.create_account_button) else "Przetwarzanie...",
            onClick = {
                viewmodel.resetResponseState()
                viewmodel.sendDataToServer()
            },
            enabled = formValid.value
        )
    }
}

@Composable
private fun Logo() {
    Image(
        painter = painterResource(R.drawable.logo),
        contentDescription = stringResource(R.string.logo_image_content_description),
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
        text = phoneNumber,
        onTextChange = viewmodel::onPhoneNumberChange,
        label = stringResource(R.string.phone_number_hint),
        isValid = viewmodel::isPhoneNumberValid,
        formChecker = formChecker,
        keyboardType = KeyboardType.Phone
    )
}

@Composable
private fun GithubInput(
    githubUrl: String,
    viewmodel: RegistrationViewModel,
    formChecker: () -> Unit
) {
    InputText(
        text = githubUrl,
        onTextChange = viewmodel::onGithubUrlChange,
        label = stringResource(R.string.github_url_hint),
        isValid = viewmodel::isGithubUrlValid,
        formChecker = formChecker
    )
}

@Composable
private fun ConfirmPasswordInput(
    confirmPassword: String,
    viewmodel: RegistrationViewModel,
    formChecker: () -> Unit
) {
    InputText(
        text = confirmPassword,
        onTextChange = viewmodel::onConfirmPasswordChange,
        label = stringResource(R.string.confirm_password_hint),
        isValid = viewmodel::isConfirmPasswordValid,
        formChecker = formChecker,
        keyboardType = KeyboardType.Password,
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
private fun PasswordInput(
    password: String,
    viewmodel: RegistrationViewModel,
    formChecker: () -> Unit
) {
    InputText(
        text = password,
        onTextChange = viewmodel::onPasswordChange,
        label = stringResource(R.string.password_hint),
        isValid = viewmodel::isPasswordValid,
        formChecker = formChecker,
        keyboardType = KeyboardType.Password,
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
private fun CodeVerificationInput(
    login: String,
    viewmodel: RegistrationViewModel,
    formChecker: () -> Unit
) {
    InputText(
        text = login,
        onTextChange = viewmodel::onLoginChange,
        label = stringResource(R.string.login_hint),
        isValid = viewmodel::isLoginValid,
        formChecker = formChecker
    )
}

@Composable
private fun EmailInput(
    email: String,
    viewmodel: RegistrationViewModel,
    formChecker: () -> Unit
) {
    InputText(
        text = email,
        onTextChange = viewmodel::onEmailChange,
        label = stringResource(R.string.email_hint),
        isValid = viewmodel::isEmailValid,
        formChecker = formChecker,
        keyboardType = KeyboardType.Email
    )
}

@Composable
private fun LastNameInput(
    lastName: String,
    viewmodel: RegistrationViewModel,
    formChecker: () -> Unit
) {
    InputText(
        text = lastName,
        onTextChange = viewmodel::onLastNameChange,
        label = stringResource(R.string.last_name_hint),
        isValid = viewmodel::isLastNameValid,
        formChecker = formChecker
    )
}

@Composable
private fun FirstNameInput(
    firstName: String,
    viewmodel: RegistrationViewModel,
    formChecker: () -> Unit
) {
    InputText(
        text = firstName,
        onTextChange = viewmodel::onFirstNameChange,
        label = stringResource(R.string.first_name_hint),
        isValid = viewmodel::isFirstNameValid,
        formChecker = formChecker
    )
}
