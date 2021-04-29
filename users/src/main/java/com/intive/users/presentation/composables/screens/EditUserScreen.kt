package com.intive.users.presentation.composables.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.intive.users.R
import com.intive.repository.domain.model.User
import com.intive.ui.components.Spinner
import com.intive.users.presentation.composables.ImageEdit
import com.intive.users.presentation.edit_user.EditUserViewModel

const val MAX_TEXT_FIELD_LENGTH = 35

@ExperimentalComposeUiApi
@Composable
fun EditUserScreen(
    navController: NavController,
    user: User,
) {
    val firstName = mutableStateOf(user.firstName)
    val lastName = mutableStateOf(user.lastName)
    val email = mutableStateOf(user.email)
    val phoneNumber = mutableStateOf(user.phoneNumber)
    val github = mutableStateOf(user.github)
    val bio = mutableStateOf(user.bio)

    val scrollState = rememberScrollState()

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ImageEdit(
            onClick = { /*TODO: goto Image Chooser*/ }
        )
        Spacer(modifier = Modifier.size(10.dp))
        Spinner(
            label = stringResource(R.string.gender),
            items = listOf(
                stringResource(R.string.male),
                stringResource(R.string.female),
                stringResource(R.string.different)
            ),
            onTitleSelected = { /*TODO: onTitleSelect*/ }
        )
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            value = firstName.value,
            onValueChange = {
                if (it.length <= MAX_TEXT_FIELD_LENGTH
                    &&
                    it.all { char -> char.isLetter() }
                ) {
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
                if (it.length <= MAX_TEXT_FIELD_LENGTH
                    &&
                    it.all { char -> char.isLetter() }
                ) {
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
                if (it.length <= MAX_TEXT_FIELD_LENGTH) {
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
                if (it.length <= MAX_TEXT_FIELD_LENGTH) {
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
                if (it.length <= MAX_TEXT_FIELD_LENGTH) {
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
        Spacer(modifier = Modifier.size(10.dp))
        Button(
            onClick = {
                //TODO: save changes here
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = stringResource(R.string.save),
                color = Color.White,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Button(
            onClick = {
                //TODO: cancel changes here
                navController.popBackStack()
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondaryVariant),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = stringResource(R.string.cancel),
                color = Color.White,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
    }
}