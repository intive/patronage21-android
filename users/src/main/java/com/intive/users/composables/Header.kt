package com.intive.users.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intive.users.ui.utils.darkBlue

@Composable
fun Header(
    modifier: Modifier = Modifier,
    text: String,
    count: Int? = null,
    showCount: Boolean = false,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = text,
                color = darkBlue,
                fontSize = 18.sp,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth(0.9f)
            )
            if (count != null && showCount) {
                Text(
                    text = count.toString(),
                    color = darkBlue,
                    fontSize = 16.sp,
                )
            }
        }
    }
}