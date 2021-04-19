package com.intive.audit.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.intive.audit.R
import com.intive.ui.components.SectionHeader
import com.intive.ui.components.SectionHeaderText

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun AuditListHeader(
    query: String,
    onQueryChanged: (String) -> Unit,
    showSearchField: Boolean,
    showFilterField: Boolean,
    onSearchIconClick: (Boolean) -> Unit,
    onFilterIconClick: (Boolean) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    SectionHeader(
        title = {
            SectionHeaderText(text = stringResource(R.string.logs_module))
        },
        action = {
            AnimatedVisibility(
                visible = showSearchField
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    value = query,
                    onValueChange = {
                        onQueryChanged(it)
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            keyboardController?.hideSoftwareKeyboard()
                        }
                    ),
                    label = { Text("Wyszukaj") },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Search,
                            contentDescription = stringResource(R.string.search_icon_desc)
                        )
                    }
                )
            }
        },
        actions = {
            IconButton(onClick = {
                onSearchIconClick(!showSearchField)
                if (showSearchField) onQueryChanged("")
            }) {
                Icon(
                    Icons.Outlined.Search,
                    contentDescription = stringResource(R.string.search_icon_desc)
                )
            }
            IconButton(onClick = { onFilterIconClick(!showFilterField) }) {
                Icon(
                    Icons.Outlined.FilterAlt,
                    contentDescription = stringResource(R.string.filter_icon_desc)
                )
                FilterDropdown(
                    expanded = showFilterField,
                    updateExpand = { newExpanded ->
                        onFilterIconClick(!showFilterField)
                    }
                )
            }
        }
    )
}