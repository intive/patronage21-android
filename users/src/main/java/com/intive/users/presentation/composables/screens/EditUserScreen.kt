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
    val user = viewModel.user.value.data!!

    val firstName = mutableStateOf(user.firstName)
    val lastName = mutableStateOf(user.lastName)
    val email = mutableStateOf(user.email)
    val phoneNumber = mutableStateOf(user.phoneNumber)
    val github = mutableStateOf(user.github)
    val bio = mutableStateOf(user.bio)

    val scrollState = rememberScrollState()

    val keyboardController = LocalSoftwareKeyboardController.current

    LayoutContainer(
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ImageEdit(
                profilePhoto = user.image?.decodeBase64IntoBitmap()?.asImageBitmap(),
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
                        user.firstName = it
                    }
                },
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
                        user.lastName = it
                    }
                },
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
                        user.email = it
                    }
                },
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
                        user.phoneNumber = it
                    }
                },
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
                        user.github = it
                    }
                },
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
                value = bio.value,
                onValueChange = {
                    bio.value = it
                    user.bio = it
                },
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
                onClick = {
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