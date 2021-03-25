package com.example.patron_a_tive.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Dehaze
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.patron_a_tive.R

@Composable
fun PatronativeAppBar(
    modifier: Modifier = Modifier,
    onNavIconPressed: () -> Unit = { },
    title: @Composable RowScope.() -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    // This bar is translucent but elevation overlays are not applied to translucent colors.
    // Instead we manually calculate the elevated surface color from the opaque color,
    // then apply our alpha.
    //
    // We set the background on the Column rather than the TopAppBar,
    // so that the background is drawn behind any padding set on the app bar (i.e. status bar).
    val backgroundColor = Color.White
    Column(
        Modifier.background(backgroundColor.copy(alpha = 0.95f))
    ) {
        TopAppBar(
            modifier = modifier,
            title = { Row { title() } },
            backgroundColor = Color.Transparent,
            elevation = 50.dp,
            actions = actions
        )
    }
}

@Preview
@Composable
fun JetchatAppBarPreview() {
    PatronativeAppBar(title = { Text("Patron-a-tive!") })
}

@Preview
@Composable
fun JetchatAppBarWithActionsPreview() {
    PatronativeAppBar(
        title = { Text(text = "Patron-a-tive!", color = Color(R.color.design_default_color_primary)) },
        actions = {
            IconButton(onClick = { }){
                Icon(Icons.Outlined.Search, contentDescription = null)
            }
            Spacer(modifier = Modifier.size(5.dp))
            IconButton(onClick = { }){
                Icon(Icons.Outlined.Person, contentDescription = null)
            }
            Spacer(modifier = Modifier.size(5.dp))
            IconButton(onClick = { }){
                Icon(Icons.Outlined.Dehaze, contentDescription = null)
            }
        }
    )
}