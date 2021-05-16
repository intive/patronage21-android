package com.intive.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.intive.ui.R


@Composable
fun LayoutContainer(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = dimensionResource(id = R.dimen.screen_padding),
                end = dimensionResource(id = R.dimen.screen_padding)
            )
    ) {
        content()
    }
}