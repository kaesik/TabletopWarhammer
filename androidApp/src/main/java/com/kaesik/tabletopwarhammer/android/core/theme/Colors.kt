package com.kaesik.tabletopwarhammer.android.core.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.kaesik.tabletopwarhammer.core.presentation.Colors

val AccentViolet = Color(Colors.ACCENT_VIOLET)
val LightBlue = Color(Colors.LIGHT_BLUE)
val LightBlueGrey = Color(Colors.LIGHT_BLUE_GREY)
val LightGrey = Color(Colors.LIGHT_GREY)
val DarkGrey = Color(Colors.DARK_GREY)
val TextBlack = Color(Colors.TEXT_BLACK)

val lightColors = lightColorScheme(
    primary = AccentViolet,
    background = LightBlueGrey,
    onPrimary = LightGrey,
    onBackground = TextBlack,
    surface = LightGrey,
    onSurface = TextBlack,
)

val darkColors = lightColorScheme(
    primary = AccentViolet,
    background = DarkGrey,
    onPrimary = LightGrey,
    onBackground = LightGrey,
    surface = DarkGrey,
    onSurface = LightGrey,
)
