package com.example.patron_a_tive.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val light_blue = Color(0xFF52BCFF)
private val light_blue600 = Color(0xFF3FB2FA)
private val light_blue700 = Color(0xFF29A8F8)
private val light_blue800 = Color(0xFF11A3FF)

private val Blue200 = Color(0xFF9DA3FA)
private val Blue400 = Color(0xFF4860F7)
private val moderate_pink = Color(0xFFCC4C80)
private val moderate_dark_pink = Color(0xFFC22D6A)

private val Red300 = Color(0xFFEA6D7E)
private val Red800 = Color(0xFFD00036)

private val PatronativeDarkPalette = darkColors(
    primary = Blue200,
    primaryVariant = Blue400,
    onPrimary = Color.Black,
    secondary = light_blue,
    onSecondary = Color.Black,
    onSurface = Color.White,
    onBackground = Color.White,
    error = Red300,
    onError = Color.Black
)

private val PatronativeLightPalette = lightColors(
    primary = moderate_pink,
    primaryVariant = moderate_dark_pink,
    onPrimary = Color.White,
    secondary = light_blue700,
    secondaryVariant = light_blue800,
    onSecondary = Color.Black,
    onSurface = Color.Black,
    onBackground = Color.Black,
    error = Red800,
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