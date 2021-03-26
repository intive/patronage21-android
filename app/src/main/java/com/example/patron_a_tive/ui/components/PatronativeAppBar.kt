package com.example.patron_a_tive.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Dehaze
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.patron_a_tive.R

@Composable
fun PatronativeAppBar(
    modifier: Modifier = Modifier,
    title: @Composable RowScope.() -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    val backgroundColor = Color.White
    Column(
        Modifier.background(backgroundColor.copy(alpha = 0.95f))
    ) {
        TopAppBar(
            modifier = modifier,
            title = { Row { title() } },
            backgroundColor = Color.White,
            elevation = 50.dp,
            actions = actions
        )
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
    PatronativeAppBar(
        title = { Text(text = "Patron-a-tive", color = Color(R.color.design_default_color_primary)) },
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