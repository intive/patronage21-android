package com.example.patron_a_tive.calendar_module.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HeaderLarge(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.h5,
        color = MaterialTheme.colors.secondary,
        modifier = Modifier.padding(bottom = 24.dp)
    )
}


@Composable
fun HeaderMedium(text: String, modifier: Modifier) {
    Text(
        text,
        style = MaterialTheme.typography.h6,
        color = Color.Black,
        modifier = modifier
    )
}