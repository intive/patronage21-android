package com.intive.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun PrimaryButton(text: String, onClick: () -> Unit, enabled: Boolean = true) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
        modifier = Modifier
            .fillMaxWidth(),
        enabled = enabled
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.subtitle1,
            color = Color.White
        )
    }
}

@Composable
fun SecondaryButton(text: String, onClick: () -> Unit, enabled: Boolean = true) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
        modifier = Modifier
            .fillMaxWidth(),
        enabled = enabled
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.subtitle1,
            color = Color.White
        )
    }
}