package com.example.patron_a_tive.calendar_module.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CheckboxComponent(label: String) {
    val state = remember { mutableStateOf(false) }

    Row {
        Checkbox(
            checked = state.value,
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
            onCheckedChange = { state.value = it },
        )
        Text(
            text = label, modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp)
                .align(Alignment.CenterVertically)
        )
    }
}