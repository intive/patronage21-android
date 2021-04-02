package com.example.patron_a_tive.calendar_module.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
// TODO: add onClick parameter, onClick: () -> Unit
fun OKButton(text: String){
    Button(
        onClick = {
            //navController?.popBackStack()
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        Text(
            text,
            style = MaterialTheme.typography.subtitle1,
            color = Color.White
        )
    }
}

@Composable
// TODO: add onClick parameter
fun CancelButton(text: String) {
    Button(
        onClick = {
           //navController?.popBackStack()
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        Text(
            text,
            style = MaterialTheme.typography.subtitle1,
            color = Color.White
        )
    }
}
