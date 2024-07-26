package com.dmm.bootcamp.yatter2024.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val darkColorScheme = darkColors(
  primary = Purple80,
  secondary = PurpleGrey80,
)

private val lightColorScheme = lightColors(
  primary = Purple40,
  secondary = PurpleGrey40,
)

private val darkColorPalette = darkColors(
  primary = Blue200,
  primaryVariant = Blue700,
  secondary = Blue200
)

private val lightColorPalette = lightColors(
  primary = Blue500,
  primaryVariant = Blue700,
  secondary = Blue200
)

private val darkGrayColorPalette = darkColors(
  primary = Gray200,
  primaryVariant = Gray700,
  secondary = Gray200
)

private val lightGrayColorPalette = lightColors(
  primary = Gray500,
  primaryVariant = Gray700,
  secondary = Gray200
)

@Composable
fun Yatter2024Theme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorScheme =
    if (darkTheme) darkColorScheme else lightColorScheme

  MaterialTheme(
    colors = colorScheme,
    typography = Typography,
    content = content
  )
}

@Composable
fun Yatter2024ThemeRegister(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorScheme =
    if (darkTheme) darkColorPalette else lightColorPalette

  MaterialTheme(
    colors = colorScheme,
    typography = Typography,
    content = content
  )
}

@Composable
fun Yatter2024ThemeProfile(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorScheme =
    if (darkTheme) darkGrayColorPalette else lightGrayColorPalette

  MaterialTheme(
    colors = colorScheme,
    typography = Typography,
    content = content
  )
}