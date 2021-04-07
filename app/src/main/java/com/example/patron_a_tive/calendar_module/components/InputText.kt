package com.example.patron_a_tive.calendar_module.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.patron_a_tive.R
import com.example.patron_a_tive.calendar_module.viewmodels.AddEventViewModel

@ExperimentalComposeUiApi
@Composable
fun InputText(textState: String, addEventViewModel: AddEventViewModel = viewModel()) {
        TextField(
            value = textState,
            onValueChange = { addEventViewModel.setInputValue(it) },
            label = { Text(stringResource(R.string.add_event_hint)) },
            modifier = Modifier.fillMaxWidth()
        )
}