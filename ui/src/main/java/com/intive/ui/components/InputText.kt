package com.intive.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.text.input.ImeAction

@ExperimentalComposeUiApi
@Composable
fun InputText(textState: String, label: String, setInputValue: (String) -> Unit, focusManager: FocusManager, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = textState,
        onValueChange = { setInputValue(it) },
        label = { Text(label) },
        maxLines = 1,
        modifier = modifier.fillMaxWidth(),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
    )
}