package com.intive.calendar.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.intive.calendar.R
import com.intive.calendar.viewmodels.CalendarHomeViewModel


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
                contentDescription = stringResource(R.string.exit_dialog_btn_desc),
                tint = Color.Black
            )
        }
    }
}
