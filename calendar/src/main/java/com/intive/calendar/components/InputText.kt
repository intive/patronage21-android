package com.intive.calendar.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.intive.calendar.R

@ExperimentalComposeUiApi
@Composable
fun InputText(textState: String, setInputValue: (String) -> Unit) {
    TextField(
        value = textState,
        onValueChange = { setInputValue(it) },
        label = { Text(stringResource(R.string.add_event_hint)) },
        modifier = Modifier.fillMaxWidth()
    )
}