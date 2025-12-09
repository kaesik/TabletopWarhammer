package com.kaesik.tabletopwarhammer.android.core.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import com.kaesik.tabletopwarhammer.core.theme.Black1
import com.kaesik.tabletopwarhammer.core.theme.Black2
import com.kaesik.tabletopwarhammer.core.theme.Brown1
import com.kaesik.tabletopwarhammer.core.theme.Brown2
import com.kaesik.tabletopwarhammer.core.theme.Green
import com.kaesik.tabletopwarhammer.core.theme.Light1
import com.kaesik.tabletopwarhammer.core.theme.Light2
import com.kaesik.tabletopwarhammer.core.theme.Red

val darkColors = darkColorScheme(
    primary = Black2,
    onPrimary = Brown1,
    primaryContainer = Black1,
    onPrimaryContainer = Brown2,
    inversePrimary = Brown1,

    secondary = Brown1,
    onSecondary = Black1,
    secondaryContainer = Brown2,
    onSecondaryContainer = Light1,

    background = Black1,
    onBackground = Brown2,
    surface = Light1,
    onSurface = Light2,

    error = Red,
    onError = Green
)


val lightColors = lightColorScheme(
    background = Light1,
    primary = Light2,
    onPrimary = Brown1,
    onBackground = Brown2,
    surface = Black1,
    onSurface = Black2,

    )
