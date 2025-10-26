package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.talents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SourceLabelBadge
import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.core.domain.info.InspectRef
import com.kaesik.tabletopwarhammer.features.info.InspectInfoIcon
import com.kaesik.tabletopwarhammer.features.info.LocalOpenInfo

@Composable
fun TalentTableRowRandom(
    rolledTalentName: String?,
    onRoll: () -> Unit
) {
    val openInfo = LocalOpenInfo.current

    Surface(shadowElevation = 4.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
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
                InspectInfoIcon(
                    onClick = {
                        openInfo(
                            InspectRef(
                                type = LibraryEnum.TALENT,
                                key = rolledTalentName
                            )
                        )
                    }
                )
                SourceLabelBadge("Rolled")
            }
        }
    }
}

@Composable
@Preview
fun TalentTableRowRandomPreview() {
    TalentTableRowRandom(
        rolledTalentName = null,
        onRoll = {}
    )
}
