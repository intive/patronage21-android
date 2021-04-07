package com.intive.patronative.calendar_module.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.intive.patronative.calendar_module.viewmodels.AddEventViewModel
import com.intive.patronative.R

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