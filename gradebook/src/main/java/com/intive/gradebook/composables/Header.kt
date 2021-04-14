package com.intive.gradebook.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
    text2: String?=null,
    text3: String?=null,
    textColor: Color = Color(0xFF52BCFF),
    backgroundColor: Color = Color(0xFFEFF9FF),
    count: Int? = null,
    showCount: Boolean = false,
    showText2: Boolean = false,
    showText3: Boolean = false,
    fraction: Float = 0.9f,
    fraction2: Float = 0.1f
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
                    .fillMaxWidth(fraction)
            )
            if(text3 != null && showText3) {
                Text(
                    text = text3,
                    color = textColor,
                    fontSize = 18.sp,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier=Modifier.fillMaxWidth(fraction2)
                )
            }
            if (count != null && showCount) {
                Text(
                    text = count.toString(),
                    color = textColor,
                    fontSize = 16.sp
                )
            }
            else if(text2 != null && showText2) {
                Text(
                    text = text2,
                    color = textColor,
                    fontSize = 18.sp,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}