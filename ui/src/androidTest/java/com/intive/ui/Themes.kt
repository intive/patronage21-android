package com.intive.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val pink300 = Color(R.color.pink_300)
private val pink700 = Color(R.color.pink700)

private val light_blue400 = Color(R.color.light_blue400)
private val light_blue700 = Color(R.color.light_blue700)

private val deep_purple300 = Color(R.color.deep_purple_300)
private val deep_purple700 = Color(R.color.deep_purple_700)

private val red800 = Color(R.color.red800)
private val red900 = Color(R.color.red900)

private val PatronativeDarkPalette = darkColors(
    primary = pink300,
    primaryVariant = pink700,
    onPrimary = Color.White,
    secondary = light_blue400,
    secondaryVariant = light_blue700,
    onSecondary = Color.Black,
    onSurface = Color.White,
    onBackground = Color.White,
    error = red800,
    onError = Color.Black
)

private val PatronativeLightPalette = lightColors(
    primary = deep_purple300,
    primaryVariant = deep_purple700,
    onPrimary = Color.White,
    secondary = light_blue400,
    secondaryVariant = light_blue700,
    onSecondary = Color.Black,
    onSurface = Color.White,
    onBackground = Color.White,
    error = red900,
    onError = Color.White
)

@Composable
fun PatronativeTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    colors: Colors? = null,
    content: @Composable () -> Unit
) {
    val myColors = colors ?: if (isDarkTheme) PatronativeDarkPalette else PatronativeLightPalette

    MaterialTheme(
        colors = myColors,
        content = content,
        typography = PatronageTypography
    )
}