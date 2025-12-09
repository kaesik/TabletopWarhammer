package com.kaesik.tabletopwarhammer.android.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
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


val typography = Typography(
    bodySmall = TextStyle(
        fontFamily = CaslonAntiqueText,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = CaslonAntiqueText,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = CaslonAntiqueText,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    )
)
