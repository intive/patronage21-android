package com.intive.users.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = text,
                color = textColor,
                fontSize = 18.sp,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth(0.9f)
            )
            if (count != null && showCount) {
                Text(
                    text = count.toString(),
                    color = textColor,
                    fontSize = 16.sp,
                )
            }
        }
    }
}