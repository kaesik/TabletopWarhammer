package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TalentTableRandomItem(
    rolledTalentName: String?,
    onRoll: () -> Unit
) {
    Surface(shadowElevation = 4.dp) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = rolledTalentName ?: "Random Talent",
                modifier = Modifier.weight(2f)
            )

            if (rolledTalentName == null) {
                IconButton(onClick = onRoll) {
                    Icon(
                        imageVector = Icons.Default.Casino,
                        contentDescription = "Roll Random Talent"
                    )
                }
            } else {
                SourceLabelBadge("Rolled")
            }
        }
    }
}
