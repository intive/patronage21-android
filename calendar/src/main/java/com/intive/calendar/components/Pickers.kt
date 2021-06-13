package com.intive.calendar.components

import android.app.AlertDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.intive.calendar.R


@Composable
fun PickerRow(
    label: String,
    date: String,
    time: String,
    datePickerDialog: AlertDialog,
    timePickerDialog: AlertDialog
) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Row {
                Text(
                    text = label,
                    modifier = Modifier.padding(end = 8.dp).align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.h6
                )
                Column {
                    Button(
                        onClick = { datePickerDialog.show() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.very_pale_blue)),
                        modifier = Modifier.padding(4.dp).shadow(0.dp, clip = true)
                    ) {
                        Text(
                            text = date,
                            style = MaterialTheme.typography.subtitle1,
                            color = Color.Black
                        )
                    }
                }

                Column {
                    Button(
                        onClick = { timePickerDialog.show() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.very_pale_blue)),
                        modifier = Modifier.padding(4.dp).shadow(0.dp, clip = true)
                    ) {
                        Text(
                            text = time,
                            style = MaterialTheme.typography.subtitle1,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}