package com.intive.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun ProvideDimens(
    calendarDimensions: CalendarDimensions,
    content: @Composable () -> Unit
) {
    val dimensionSet = remember { calendarDimensions }
    CompositionLocalProvider(LocalAppDimens provides dimensionSet, content = content)
}

private val LocalAppDimens = staticCompositionLocalOf {
    smallDimensions
}

class CalendarDimensions(
    val calendar_header_font: TextUnit,
)

val standardDimensions = CalendarDimensions(
    calendar_header_font = 20.sp,
)

val smallDimensions = CalendarDimensions(
    calendar_header_font = 16.sp,
)

object CalendarDimens {
    val dimens: CalendarDimensions
        @Composable
        get() = LocalAppDimens.current
}


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

    val configuration = LocalConfiguration.current
    val calendarDimensions =
        if (configuration.screenWidthDp <= 320) smallDimensions else standardDimensions

    ProvideDimens(calendarDimensions = calendarDimensions) {
        MaterialTheme(
            colors = myColors,
            typography = PatronageTypography
        ) {
            content()
        }
    }
}


