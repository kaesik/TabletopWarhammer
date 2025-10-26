package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@Composable
fun SeparatorOr() {
    Text(
        text = "OR",
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .alpha(0.7f)
    )
}
