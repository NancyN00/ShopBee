package com.nancy.shopbee.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

val DarkColorScheme =
    darkColorScheme(
        primary = OrangeDark,
        // text/icons on primary
        onPrimary = Black,
        background = Black,
        surface = Black,
        onBackground = White,
        onSurface = White,
        secondary = OrangeLight,
    )

val LightColorScheme =
    lightColorScheme(
        primary = OrangePrimary,
        // text/icons on primary
        onPrimary = White,
        background = White,
        surface = White,
        onBackground = Black,
        onSurface = Black,
        secondary = OrangeLight,
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
     */
    )

@Composable
fun ShopBeeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content,
    )
}
