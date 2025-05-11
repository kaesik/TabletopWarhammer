package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RandomTalentDiceButton(
    onRoll: () -> Unit,
    rolledTalentName: String?
) {
    if (rolledTalentName == null) {
        IconButton(onClick = onRoll) {
            Icon(
                imageVector = Icons.Default.Casino,
                contentDescription = "Roll Random Talent"
            )
        }
    } else {
        Text(text = rolledTalentName)
    }
}
