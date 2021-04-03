package com.intive.users.composables


import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intive.users.R

@Composable
fun ScreenInfo(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 20.sp,
    color: Color = MaterialTheme.colors.secondaryVariant,
    fontWeight: FontWeight = FontWeight.Bold,
) {
    Text(
        stringResource(R.string.users),
        fontSize = fontSize,
        color = color,
        fontWeight = fontWeight,
        modifier = modifier.padding(bottom = 16.dp)
    )
    Text(
        stringResource(R.string.lorem_placeholder)
    )
}