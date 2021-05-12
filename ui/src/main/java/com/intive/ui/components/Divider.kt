package com.intive.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Divider(){
    androidx.compose.material.Divider(
        color = Color.LightGray,
        thickness = 0.5.dp
    )
}