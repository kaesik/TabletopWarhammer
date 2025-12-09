package com.kaesik.tabletopwarhammer.android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.kaesik.tabletopwarhammer.android.core.theme.darkColors
import com.kaesik.tabletopwarhammer.android.core.theme.lightColors
import com.kaesik.tabletopwarhammer.android.core.theme.shapes
import com.kaesik.tabletopwarhammer.android.core.theme.typography

@Composable
fun MainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors
    } else {
        lightColors
    }

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
