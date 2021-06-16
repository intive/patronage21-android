package com.intive.users.presentation.composables.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.shared.decodeBase64IntoBitmap
import com.intive.users.R
import com.intive.ui.components.LayoutContainer
import com.intive.ui.components.PrimaryButton
import com.intive.ui.components.SecondaryButton
import com.intive.ui.components.Spinner
import com.intive.users.presentation.composables.ImageEdit
import com.intive.users.presentation.user.UserViewModel

const val MAX_TEXT_FIELD_LENGTH = 35

@ExperimentalComposeUiApi
@Composable
fun EditUserScreen(
    navController: NavController,
    viewModel: UserViewModel,
) {
    val userCopy = viewModel.user.value.data!!.copy()

    val firstName = mutableStateOf(userCopy.firstName)
    val lastName = mutableStateOf(userCopy.lastName)
    val email = mutableStateOf(userCopy.email)
    val phoneNumber = mutableStateOf(userCopy.phoneNumber)
    val github = mutableStateOf(userCopy.github)
    val bio = mutableStateOf(userCopy.bio)

    val scrollState = rememberScrollState()

    val keyboardController = LocalSoftwareKeyboardController.current

    LayoutContainer {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ImageEdit(
                profilePhoto = userCopy.image?.decodeBase64IntoBitmap()?.asImageBitmap(),
                onClick = { /*TODO: goto Image Chooser*/ }
            )
            Spacer(modifier = Modifier.size(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                value = firstName.value,
                onValueChange = {
                    if (willTextFit(it)) {
                        firstName.value = it
                        userCopy.firstName = it
                    }
                },
                isError = !viewModel.isFirstNameValid(firstName.value),
                label = { Text(text = stringResource(R.string.first_name)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        keyboardController?.hideSoftwareKeyboard()
                    }
                )
            )
            Spacer(modifier = Modifier.size(10.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = 1,
                value = lastName.value,
                onValueChange = {
                    if (willTextFit(it)) {
                        lastName.value = it
                        userCopy.lastName = it
                    }
                },
                isError = !viewModel.isLastNameValid(lastName.value),
                label = { Text(text = stringResource(R.string.last_name)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        keyboardController?.hideSoftwareKeyboard()
                    }
                )
            )
            Spacer(modifier = Modifier.size(10.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = 1,
                value = email.value,
                onValueChange = {
                    if (willTextFit(it)) {
                        email.value = it
                        userCopy.email = it
                    }
                },
                isError = !viewModel.isEmailValid(email.value),
                label = { Text(text = stringResource(R.string.email_address)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        keyboardController?.hideSoftwareKeyboard()
                    }
                )
            )
            Spacer(modifier = Modifier.size(10.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = 1,
                value = phoneNumber.value,
                onValueChange = {
                    if (willTextFit(it)) {
                        phoneNumber.value = it
                        userCopy.phoneNumber = it
                    }
                },
                isError = !viewModel.isPhoneNumberValid(phoneNumber.value),
                label = { Text(text = stringResource(R.string.phone_number)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        keyboardController?.hideSoftwareKeyboard()
                    }
                )
            )
            Spacer(modifier = Modifier.size(10.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = 1,
                value = github.value,
                onValueChange = {
                    if (willTextFit(it)) {
                        github.value = it
                        userCopy.github = it
                    }
                },
                isError = !viewModel.isGithubUrlValid(github.value),
                label = { Text(text = stringResource(R.string.github)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        keyboardController?.hideSoftwareKeyboard()
                    }
                )
            )
            Spacer(modifier = Modifier.size(10.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = bio.value ?: "",
                onValueChange = {
                    bio.value = it
                    userCopy.bio = it
                },
                isError = !viewModel.isBioValid(bio.value),
                label = { Text(text = stringResource(R.string.bio)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        keyboardController?.hideSoftwareKeyboard()
                    }
                )
            )
            Spacer(modifier = Modifier.size(30.dp))
            PrimaryButton(
                text = stringResource(R.string.save),
                enabled = viewModel.isFormValid(userCopy),
                onClick = {
                    viewModel.onEditUserButtonPressed(userCopy)
                }
            )
            Spacer(modifier = Modifier.size(10.dp))
            SecondaryButton(
                text = stringResource(R.string.cancel),
                onClick = {
                    navController.popBackStack()
                }
            )
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}

fun willTextFit(text: String): Boolean {
    return text.length <= MAX_TEXT_FIELD_LENGTH
}