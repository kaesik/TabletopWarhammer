package com.kaesik.tabletopwarhammer.android.core.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.kaesik.tabletopwarhammer.android.R

val CaslonAntiqueText = FontFamily(
    Font(
        resId = R.font.caslon_antique_normal,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.caslon_antique_italic,
        weight = FontWeight.Light
    ),
    Font(
        resId = R.font.caslon_antique_bold,
        weight = FontWeight.Bold
    ),
)
