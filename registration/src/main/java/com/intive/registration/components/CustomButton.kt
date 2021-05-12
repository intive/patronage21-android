package com.intive.registration.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
    enabled: Boolean = true)
{
    Button(
        onClick =  onClick,
        colors = colors,
        shape = RoundedCornerShape(percent = 50),
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled
    ) {
        Text(text)
    }
}