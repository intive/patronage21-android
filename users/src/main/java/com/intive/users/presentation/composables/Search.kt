package com.intive.users.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.intive.users.R

const val MAX_QUERY_SIZE = 35
const val MAX_LINES = 1

@Composable
fun Search(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = query,
        singleLine = true,
        maxLines = MAX_LINES,
        onValueChange = { newValue ->
            if(isValueCorrect(newValue)) {
                onQueryChanged(newValue)
            }
        },
        label = { Text(text = stringResource(R.string.search_user)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onExecuteSearch()
            }
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = stringResource(R.string.search_icon_content_description),
                tint = MaterialTheme.colors.secondaryVariant,
                modifier = Modifier.clickable {
                    onExecuteSearch()
                }
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            focusedLabelColor = MaterialTheme.colors.secondaryVariant,
            focusedIndicatorColor = MaterialTheme.colors.secondaryVariant,
            cursorColor = MaterialTheme.colors.secondaryVariant,
        ),
    )
}

private fun isValueCorrect(value: String): Boolean {
    if (value.length <= MAX_QUERY_SIZE &&
            value.all { char -> char.isLetter() || char.isWhitespace() } ) {
        return true
    }
    return false
}