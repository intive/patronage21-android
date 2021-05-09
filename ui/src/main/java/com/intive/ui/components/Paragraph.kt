package com.intive.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Paragraph(text: String, modifier: Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.body1,
        color = Color.Black,
        modifier = modifier
    )
}