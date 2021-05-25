package com.intive.audit.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.intive.ui.components.DropDownList

@Composable
fun SpinnerFilter(
    items: List<String>,
    expanded: Boolean,
    updateExpand: (Boolean) -> Unit,
    onTitleSelected: (String) -> Unit,
) {
    val text = rememberSaveable { mutableStateOf(items[0]) }
    val isOpen = rememberSaveable { mutableStateOf(false) }
    Box {
        Column {
            DropDownList(
                requestToOpen = expanded,
                dropDownIconShow = true,
                list = items,
                updateExpand,
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