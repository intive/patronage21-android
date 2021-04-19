package com.intive.audit.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.intive.audit.R

@Composable
fun FilterDropdown(expanded: Boolean, updateExpand: (Boolean) -> Unit) {
    val items = listOf("Od najnowszych", "Od najstarszych")
    var selectedIndex by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopEnd)
            .padding(top = 50.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { updateExpand(false) },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.White
                )
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(
                    onClick = {
                        selectedIndex = index
                        updateExpand(false)
                    },
                )
                {
                    Text(
                        text = s,
                        modifier = Modifier.weight(1f)
                    )
                    if (index == selectedIndex) {
                        Icon(
                            Icons.Outlined.Done,
                            contentDescription = stringResource(R.string.arrow_upward_icon_desc)
                        )
                    }
                }
                if (index != items.size - 1)
                    Divider(color = Color.LightGray)
            }
        }
    }
}