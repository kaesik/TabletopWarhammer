package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FateResilienceCard(
    fatePoints: Int,
    resiliencePoints: Int,
    extraPoints: Int,
    baseFatePoints: Int,
    baseResiliencePoints: Int,
    onFatePointsIncrease: () -> Unit,
    onFatePointsDecrease: () -> Unit,
    onResiliencePointsIncrease: () -> Unit,
    onResiliencePointsDecrease: () -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PointPicker(
                    attributeName = "Fate Points",
                    value = fatePoints,
                    onIncrease = onFatePointsIncrease,
                    onDecrease = onFatePointsDecrease,
                    canIncrease = extraPoints > 0,
                    canDecrease = fatePoints > baseFatePoints
                )
                PointPicker(
                    attributeName = "Resilience Points",
                    value = resiliencePoints,
                    onIncrease = onResiliencePointsIncrease,
                    onDecrease = onResiliencePointsDecrease,
                    canIncrease = extraPoints > 0,
                    canDecrease = resiliencePoints > baseResiliencePoints
                )
                ElevatedCard(
                    modifier = Modifier.width(200.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Extra Points")
                        Text(text = extraPoints.toString())
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun FateResilienceCardPreview() {
    FateResilienceCard(
        baseFatePoints = 2,
        fatePoints = 3,
        baseResiliencePoints = 5,
        resiliencePoints = 5,
        extraPoints = 2,
        onFatePointsIncrease = {},
        onFatePointsDecrease = {},
        onResiliencePointsIncrease = {},
        onResiliencePointsDecrease = {},
    )
}
