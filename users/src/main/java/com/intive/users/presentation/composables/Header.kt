package com.intive.users.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Header(
    text: String,
    textColor: Color = Color(0xFF52BCFF),
    backgroundColor: Color = Color(0xFFEFF9FF),
    count: Int? = null,
    showCount: Boolean = false,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

            Text(
                text = text,
                color = textColor,
                fontSize = 18.sp,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(start = 16.dp)
            )
            if (count != null && showCount) {
                Text(
                    text = count.toString(),
                    color = textColor,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }

    }
}

@Preview
@Composable
fun HeaderPreview() {
    Header(text = "Liderzy", count = 10, showCount = true)
}