package com.intive.registration.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.intive.registration.R

@Composable
fun AgreeCheckBox(
    agree: Boolean,
    onAgreeChange: (Boolean) -> Unit,
    label: String,
    formChecker: () -> Unit
) {
    Row {
        val checkedState = remember { mutableStateOf(agree) }
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = it
                onAgreeChange(it)
                formChecker()
            }
        )
        Column {
            if (!checkedState.value) {
                Text(
                    text = stringResource(R.string.agree_required),
                    color = Color.Red
                    )
                Spacer(modifier = Modifier.height(4.dp))
            }
            Text(text = label)
        }
    }
}