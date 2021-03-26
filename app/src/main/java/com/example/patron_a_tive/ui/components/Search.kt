package com.example.patron_a_tive.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.patron_a_tive.ui.utils.darkBlue
import com.example.patron_a_tive.ui.utils.lightGray

@Composable
fun Search(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
            .border(2.dp, lightGray),
        value = query,
        onValueChange = { onQueryChanged(it) },
        label = { Text(text = "Szukaj użytkownika") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onExecuteSearch()
//                    keyboardController?.hideSoftwareKeyboard()
            },
        ),
        trailingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon", tint = darkBlue, modifier = Modifier.size(36.dp))  },
        textStyle = TextStyle(color = darkBlue),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            textColor = Color.Black,
            focusedLabelColor = darkBlue,
            focusedIndicatorColor = darkBlue,
            cursorColor = darkBlue,
        ),
    )
}