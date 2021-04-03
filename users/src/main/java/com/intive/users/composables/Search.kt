package com.intive.users.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.intive.users.R

@Composable
fun Search(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
) {

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.DarkGray),
        value = query,
        onValueChange = { onQueryChanged(it) },
        label = { Text(text = stringResource(R.string.search_user)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onExecuteSearch()
            },
        ),
        trailingIcon = {
            Icon(
                Icons.Filled.Search,
                contentDescription = stringResource(R.string.search_icon_content_description),
                tint = MaterialTheme.colors.secondaryVariant,
                modifier = Modifier.size(36.dp)
            )
        },
        textStyle = TextStyle(color = Color.Black),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            focusedLabelColor = MaterialTheme.colors.secondaryVariant,
            focusedIndicatorColor = MaterialTheme.colors.secondaryVariant,
            cursorColor = MaterialTheme.colors.secondaryVariant,
        ),
    )
}