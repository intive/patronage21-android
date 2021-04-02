package com.intive.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun TitleText(
    text: String,
    modifier: Modifier,
    style: TextStyle = MaterialTheme.typography.h5,
    color: Color = MaterialTheme.colors.secondary
){
    Text(
        text = text,
        style = style,
        color = color,
        modifier = modifier
    )
}