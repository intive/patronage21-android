package com.intive.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.intive.ui.R

@Composable
fun Spinner(
    items: List<String>,
    label: String? = null,
    dropDownIconShow: Boolean = false,
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
                dropDownIconShow = dropDownIconShow,
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
                .padding(top = 8.dp)
                .clickable(
                    onClick = { isOpen.value = true }
                )
        )
    }
}

@Composable
fun DropDownList(
    requestToOpen: Boolean = false,
    dropDownIconShow: Boolean = false,
    list: List<String>,
    request: (Boolean) -> Unit,
    selectedString: (String) -> Unit
) {
    var selectedIndex by remember { mutableStateOf(0) }
    DropdownMenu(
        modifier = Modifier.fillMaxWidth(),
        expanded = requestToOpen,
        onDismissRequest = { request(false) },
    ) {
        list.forEachIndexed { index, item ->
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    selectedIndex = index
                    request(false)
                    selectedString(item)
                }
            ) {
                Text(
                    item,
                    modifier = Modifier.wrapContentWidth()
                )
                if(dropDownIconShow){
                    if (index == selectedIndex) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.End),
                        ) {
                            Icon(
                                Icons.Outlined.Done,
                                contentDescription = stringResource(R.string.arrow_upward_icon_desc)
                            )
                        }
                    }
                }
            }
        }
    }
}