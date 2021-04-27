package com.intive.calendar.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.intive.calendar.R


@Composable
fun OKButton(text: String, onClick: () -> Unit) {
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
fun ClearButton(onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.padding(12.dp)
    ) {
        IconButton(onClick = onClick) {
            Icon(
                Icons.Default.Clear,
                contentDescription = stringResource(R.string.exit_dialog_btn_desc),
                tint = Color.Black
            )
        }
    }
}


@Composable
fun ResponseButton(text: String, onSelectedColor: Color, selected: MutableState<Boolean>, onClick: () -> Unit){

    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(horizontal = 6.dp)
            .fillMaxWidth()
            .border(0.5.dp, color = Color.Gray, shape = RoundedCornerShape(20))
            .shadow(0.dp, clip = true),
        colors = ButtonDefaults.buttonColors(backgroundColor = if (selected.value) onSelectedColor else Color.White),

        ) {
        Text(
            text,
            style = MaterialTheme.typography.subtitle1,
            color = if (selected.value) Color.White else Color.Black
        )
    }
}