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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.intive.calendar.R


@Composable
fun ResponseButton(
    text: String,
    onSelectedColor: Color,
    selected: MutableState<Boolean>,
    onClick: () -> Unit
) {

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
            text = text,
            style = MaterialTheme.typography.subtitle1,
            color = if (selected.value) Color.White else Color.Black
        )
    }
}