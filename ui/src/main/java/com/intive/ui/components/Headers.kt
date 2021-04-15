package com.intive.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.intive.ui.PatronageTypography

@Composable
fun TitleText(
    text: String,
    modifier: Modifier,
    style: TextStyle = MaterialTheme.typography.h5,
    color: Color = MaterialTheme.colors.secondary
) {
    Text(
        text = text,
        style = style,
        color = color,
        modifier = modifier
    )
}

@Composable
fun SectionHeader(
    title: @Composable RowScope.() -> Unit,
    action: @Composable RowScope.() -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    Column(modifier = Modifier.background(MaterialTheme.colors.surface)) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(52.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                Modifier
                    .fillMaxHeight()
                    .padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                content = title
            )
            Row(
                Modifier.fillMaxHeight(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                content = actions
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            content = action
        )
    }
}

@Composable
fun SectionHeaderText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        style = PatronageTypography.h6,
        color = MaterialTheme.colors.secondary
    )
}