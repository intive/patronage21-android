package com.intive.patronative.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Dehaze
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.intive.patronative.R
import com.intive.ui.PatronativeTheme

@Composable
fun PatronativeAppBar(
    modifier: Modifier = Modifier,
    title: @Composable RowScope.() -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    val backgroundColor = MaterialTheme.colors.background
    Surface(
        modifier = Modifier.shadow(dimensionResource(id = R.dimen.appbar_elevation)),
        elevation = dimensionResource(id = R.dimen.appbar_elevation)
    ) {
        Column(
            Modifier.background(backgroundColor.copy(alpha = 0.95f))
        ) {
            TopAppBar(
                modifier = modifier,
                title = { Row { title() } },
                backgroundColor = MaterialTheme.colors.background,
                elevation = dimensionResource(id = R.dimen.appbar_elevation),
                actions = actions
            )
        }
    }
}


@Preview
@Composable
fun JetchatAppBarPreview() {
    PatronativeAppBar(title = { Text("Patron-a-tive") })
}

@Preview
@Composable
fun JetchatAppBarWithActionsPreview() {
    PatronativeTheme {
        PatronativeAppBar(
            title = {
                Text(
                    text = "Patron-a-tive",
                    color = MaterialTheme.colors.primary
                )
            },
            actions = {
                IconButton(onClick = { }) {
                    Icon(Icons.Outlined.Search, contentDescription = null)
                }
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.appbar_icons_spacer_size)))
                IconButton(onClick = { }) {
                    Icon(Icons.Outlined.Person, contentDescription = null)
                }
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.appbar_icons_spacer_size)))
                IconButton(onClick = { }) {
                    Icon(Icons.Outlined.Dehaze, contentDescription = null)
                }
            }
        )
    }
}