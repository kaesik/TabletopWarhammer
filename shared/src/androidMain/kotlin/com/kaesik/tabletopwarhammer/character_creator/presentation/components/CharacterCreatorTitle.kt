package com.kaesik.tabletopwarhammer.character_creator.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CharacterCreatorTitle(
    title: String,
) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineMedium,
    )
}

@Composable
@Preview
fun CharacterCreatorTitlePreview() {
    CharacterCreatorTitle(
        title = "Character Creator",
    )
}
