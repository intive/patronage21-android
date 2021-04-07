package com.intive.patronative.calendar_module.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.intive.patronative.calendar_module.viewmodels.CalendarHomeViewModel


@Composable
fun OKButton(text: String, onClick: () -> Unit){
    Button(
        onClick = onClick,
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
fun CancelButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
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

@Composable
fun ClearButton(calendarViewModel: CalendarHomeViewModel = viewModel()) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.padding(12.dp)
    ) {
        IconButton(onClick = { calendarViewModel.hideDialog() }) {
            Icon(
                Icons.Default.Clear,
                contentDescription = "Exit dialog button",
                tint = Color.Black
            )
        }
    }
}
