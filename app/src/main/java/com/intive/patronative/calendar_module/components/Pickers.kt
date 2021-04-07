package com.intive.patronative.calendar_module.components


import android.app.AlertDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PickerRow(label: String, placeholder: String, pickerDialog: AlertDialog) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Row {
                Text(
                    label, modifier = Modifier
                        .padding(end = 8.dp), style = TextStyle(fontWeight = FontWeight.Bold)
                )
                Text(placeholder)
            }
        }
        IconButton(onClick = { pickerDialog.show() }) {
            Icon(
                Icons.Default.Edit,
                contentDescription = "Edit button",
                tint = MaterialTheme.colors.secondary,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}