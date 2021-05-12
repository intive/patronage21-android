package com.intive.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Spinner(
    items: List<String>,
    label: String? = null,
    onTitleSelected: (String) -> Unit
) {
    val text = rememberSaveable { mutableStateOf(items[0]) }
    val isOpen = rememberSaveable { mutableStateOf(false) }
    val openCloseOfDropDownList: (Boolean) -> Unit = {
        isOpen.value = it
    }
    Box {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                OutlinedTextField(
                    value = text.value,
                    onValueChange = { text.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        if (label != null) {
                            Text(text = label)
                        }
                    }
                )
            }
            DropDownList(
                requestToOpen = isOpen.value,
                list = items,
                openCloseOfDropDownList,
                selectedString = {
                    onTitleSelected(it)
                    text.value = it
                }
            )
        }
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(top = 10.dp)
                .clickable(
                    onClick = { isOpen.value = true }
                )
        )
    }
}

@Composable
fun DropDownList(
    requestToOpen: Boolean = false,
    list: List<String>,
    request: (Boolean) -> Unit,
    selectedString: (String) -> Unit
) {
    DropdownMenu(
        modifier = Modifier.fillMaxWidth(),
        expanded = requestToOpen,
        onDismissRequest = { request(false) },
    ) {
        list.forEach { item ->
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    request(false)
                    selectedString(item)
                }
            ) {
                Text(
                    item,
                    modifier = Modifier.wrapContentWidth()
                )
            }
        }
    }
}