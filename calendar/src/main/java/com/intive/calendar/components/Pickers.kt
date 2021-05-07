package com.intive.calendar.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.intive.calendar.R

@Composable
fun PickerRow(label: String, value: String, pickerDialog: AlertDialog) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Row {
                Text(
                    text = label,
                    modifier = Modifier.padding(end = 8.dp),
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                Text(text = value)
            }
        }
        IconButton(onClick = { pickerDialog.show() }) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = stringResource(R.string.edit_btn_desc),
                tint = MaterialTheme.colors.secondary,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}