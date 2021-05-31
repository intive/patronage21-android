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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.intive.repository.domain.model.GroupEntity
import com.intive.ui.R

@Composable
fun GroupSpinner(
    items: List<GroupEntity>,
    label: String? = null,
    onTitleSelected: (GroupEntity) -> Unit
) {
    val allItems = listOf(
        GroupEntity(stringResource(R.string.all_groups), null)
    ) + items
    val text = rememberSaveable { mutableStateOf(allItems[0].printableName) }
    val isOpen = rememberSaveable { mutableStateOf(false) }
    val openCloseOfDropDownList: (Boolean) -> Unit = {
        isOpen.value = it
    }

    Box {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                list = allItems,
                openCloseOfDropDownList,
                selectedString = {
                    onTitleSelected(it)
                    text.value = it.printableName
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
    list: List<GroupEntity>,
    request: (Boolean) -> Unit,
    selectedString: (GroupEntity) -> Unit
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
                    item.printableName,
                    modifier = Modifier.wrapContentWidth()
                )
            }
        }
    }
}