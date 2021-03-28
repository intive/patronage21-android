package com.example.patron_a_tive.users_module.components


import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.patron_a_tive.ui.utils.darkBlue

@Composable
fun ScreenInfo() {
    Text(
        "Użytkownicy",
        fontSize = 20.sp,
        color = darkBlue,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 16.dp)
    )
    Text(
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus ac dolor et dui dictum viverra vel eu erat."
    )
}