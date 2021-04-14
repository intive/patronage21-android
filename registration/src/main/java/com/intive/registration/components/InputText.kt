package com.intive.registration.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.isFocused
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.intive.registration.R
import com.intive.registration.components.InputTextState.*

@Composable
fun InputText(
    text: String,
    onTextChange: (String) -> Unit,
    label: String,
    isValid: () -> Boolean = { true },
    formChecker: () -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None

) {

    val inputState = remember { mutableStateOf(NOT_TOUCHED) }
    val inputStateChanged: (InputTextState) -> Unit = {
        inputState.value = it
    }
    val hasFirstFocus = remember { mutableStateOf(false) }
    val hasFirstFocusChanged: (Boolean) -> Unit = {
        hasFirstFocus.value = it
    }
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        label = {
            Text(
                when (inputState.value) {
                    INVALID -> stringResource(R.string.field_required)
                    else -> label
                }
            )
        },
        placeholder = { Text(label) },
        modifier = Modifier
            .onFocusChanged {
                if (it.isFocused) {
                    hasFirstFocusChanged(true)
                }
                formChecker()
                inputStateChanged(
                    if (!hasFirstFocus.value) NOT_TOUCHED
                    else if (isValid()) VALID else INVALID
                )
            }
            .fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = when (inputState.value) {
                INVALID -> Color.Red
                VALID -> Color.Green
                else -> Color.Gray
            }
        ),
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = keyboardType
        )
    )
}


enum class InputTextState {
    INVALID,
    NOT_TOUCHED,
    VALID
}