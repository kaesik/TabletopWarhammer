package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorButton

@Composable
fun FateResilienceCard(
    fatePoints: Int,
    resiliencePoints: Int,
    onFatePointsChange: () -> Unit,
    onResiliencePointsChange: () -> Unit,
    onNextClick: () -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .width(400.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(400.dp)
                .width(400.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PointPicker(
                    name = "Fate Points",
                    value = fatePoints,
                    onValueChange = onFatePointsChange
                )
                PointPicker(
                    name = "Resilience Points",
                    value = resiliencePoints,
                    onValueChange = onResiliencePointsChange
                )
                CharacterCreatorButton(
                    text = "Next",
                    onClick = {
                        onNextClick()
                    }
                )
            }
        }
    }
}

@Composable
@Preview
fun FateResilienceCardPreview() {
    FateResilienceCard(
        fatePoints = 3,
        resiliencePoints = 5,
        onFatePointsChange = {},
        onResiliencePointsChange = {},
        onNextClick = {}
    )
}
