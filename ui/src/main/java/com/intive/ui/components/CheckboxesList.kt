package com.intive.registration.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


//onErrorText - if you need for example min 1 item selected,
// you can put function, which will check this rule, in isValid parameter
// and then if user doesn't check any option onErrorText appear

@Composable
fun CheckBoxesList(
    title: String,
    onErrorText: String,
    items: List<String>,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    isValid: () -> Boolean = { true },
    onCheckedChange: () -> Unit = {}
) {

    val isListValid = remember { mutableStateOf(isValid()) }
    val selectedItemsChange: (Boolean) -> Unit = {
        isListValid.value = it
    }
    Text(
        text = title,
        modifier = modifier
    )
    if (!isListValid.value) {
        Text(text = onErrorText, color = Color.Red)
    }
    Spacer(modifier = Modifier.height(4.dp))
    for (item in items) {
        Column {
            Row {
                val checkedState = remember { mutableStateOf(false) }
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = {
                        checkedState.value = it
                        onItemSelected(item)
                        selectedItemsChange(isValid())
                        onCheckedChange()
                    }
                )
                Text(
                    text = item,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}