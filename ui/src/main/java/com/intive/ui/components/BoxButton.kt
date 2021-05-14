package com.intive.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.intive.ui.PatronageTypography

@Composable
fun BoxButton(
    text: String,
    borderColor: Color = MaterialTheme.colors.primary,
    onClick: (() -> Unit) = {},
    contentOnTop: Boolean = true,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f)
        .border(
            border = BorderStroke(1.dp, color = borderColor),
            shape = RoundedCornerShape(20.dp)
        )
        .clip(shape = RoundedCornerShape(20.dp))
        .clickable { onClick() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .aspectRatio(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if(contentOnTop) {
                content()
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    style = PatronageTypography.body2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            else {
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    style = PatronageTypography.body2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(8.dp))
                content()
            }
        }
    }
}