package com.intive.registration.components

import androidx.compose.foundation.layout.*
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
fun TechnologiesList(
    avalaibleTechnologies: List<String>,
    onItemSelected: (String) -> Unit,
    isValid: () -> Boolean,
    formChecker: () -> Unit,
) {

    val valid = remember { mutableStateOf(false) }
    val selectedListChange: (Boolean) -> Unit = {
        valid.value = it
    }
    Text(text = stringResource(R.string.technologies_text))
    if(!valid.value) {
        Text(text = stringResource(R.string.select_technologies_error), color = Color.Red)
    }
    Spacer(modifier = Modifier.height(4.dp))
    for(item in avalaibleTechnologies) {
        Column {
            Row {
                val checkedState = remember { mutableStateOf(false) }
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = {
                        checkedState.value = it
                        onItemSelected(item)
                        selectedListChange(isValid())
                        formChecker()
                    }
                )
                Text(text = item)
            }
        }
    }
}