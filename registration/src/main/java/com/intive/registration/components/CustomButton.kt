package com.intive.registration.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun CustomButton(text: String, onClick: () -> Unit, enabled: Boolean = true) {
    Button(
        onClick =  onClick,
        shape = RoundedCornerShape(percent = 50),
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled
    ) {
        Text(text)
    }
}