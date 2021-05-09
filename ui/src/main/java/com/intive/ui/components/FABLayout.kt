package com.intive.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FABLayout(onClick: () -> Unit, contentDescription: String, content: @Composable () -> Unit) {

    Box(contentAlignment = Alignment.BottomEnd) {
        Column(
            Modifier
                .fillMaxHeight()
        ) {
            content()
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            FloatingActionButton(
                onClick = onClick,
                backgroundColor = colors.primary
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = contentDescription
                )
            }
        }
    }
}