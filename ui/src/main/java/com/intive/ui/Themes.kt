package com.intive.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource

@Composable
fun PatronativeTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    colors: Colors? = null,
    content: @Composable () -> Unit
) {
    val pink300 = colorResource(R.color.pink300)
    val pink700 = colorResource(R.color.pink700)

    val lightBlue0 = colorResource(R.color.light_blue0)
    val lightBlue400 = colorResource(R.color.light_blue400)
    val lightBlue700 = colorResource(R.color.light_blue700)

    val deepPurple300 = colorResource(R.color.deep_purple_300)
    val deepPurple700 = colorResource(R.color.deep_purple_700)

    val red800 = colorResource(R.color.red800)
    val red900 = colorResource(R.color.red900)

    val patronativeDarkPalette = darkColors(
        primary = deepPurple300,
        primaryVariant = deepPurple700,
        onPrimary = Color.White,
        secondary = lightBlue400,
        secondaryVariant = lightBlue700,
        onSecondary = Color.Black,
        onSurface = Color.White,
        onBackground = Color.White,
        error = red900,
        onError = Color.White
    )
    val patronativeLightPalette = lightColors(
        primary = pink300,
        primaryVariant = pink700,
        onPrimary = Color.White,
        secondary = lightBlue400,
        secondaryVariant = lightBlue700,
        onSecondary = Color.Black,
        surface = Color.White,
        onSurface = Color.Black,
        background = Color.White,
        onBackground = Color.Black,
        error = red800,
        onError = Color.Black
    )
    val myColors = colors ?: if (isDarkTheme) patronativeDarkPalette else patronativeLightPalette

    MaterialTheme(
        colors = myColors,
        typography = PatronageTypography
    ){
        content()
    }
}