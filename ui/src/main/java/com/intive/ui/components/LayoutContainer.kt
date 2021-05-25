package com.intive.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.intive.ui.R

@Composable
fun LayoutContainer(
    bottomPadding: Dp = dimensionResource(id = R.dimen.screen_padding),
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = dimensionResource(id = R.dimen.screen_padding),
                end = dimensionResource(id = R.dimen.screen_padding),
                bottom = bottomPadding,
                top = dimensionResource(id = R.dimen.screen_padding_small)
            )
    ) {
        content()
    }
}