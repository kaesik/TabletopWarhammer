package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.AndroidCharacterCreatorViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.CharacterCreatorEvent
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.components.AttributesTable
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.components.FateResilienceCard
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorButton
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorTitle
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.DiceThrow
import com.kaesik.tabletopwarhammer.core.domain.library.items.AttributeItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterAttributesScreenRoot(
    viewModel: AndroidCharacterAttributesViewModel = koinViewModel(),
    creatorViewModel: AndroidCharacterCreatorViewModel,
    onNextClick: () -> Unit,
    characterSpecies: String,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.onEvent(CharacterAttributesEvent.InitAttributesList(characterSpecies))
        viewModel.onEvent(CharacterAttributesEvent.InitFateAndResilience(characterSpecies))
    }

    val attributes = state.attributeList
    val baseAttributeValues = state.totalAttributeValues.map { it.toString() }
    val diceThrows = state.diceThrows

    CharacterAttributesScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is CharacterAttributesEvent.OnNextClick -> {
                    if (state.extraPoints == 0) {
                        creatorViewModel.onEvent(
                            CharacterCreatorEvent.SetAttributes(
                                totalAttributes = state.totalAttributeValues,
                                fatePoints = state.fatePoints,
                                resiliencePoints = state.resiliencePoints
                            )
                        )
                        onNextClick()
                    }
                }

                else -> viewModel.onEvent(event)
            }
        },
        attributes = attributes,
        diceThrows = diceThrows,
        baseAttributeValues = baseAttributeValues
    )
}

@Composable
fun CharacterAttributesScreen(
    state: CharacterAttributesState,
    onEvent: (CharacterAttributesEvent) -> Unit,
    attributes: List<AttributeItem>,
    diceThrows: List<String>,
    baseAttributeValues: List<String>,
) {
    Scaffold(
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                CharacterCreatorTitle("Character Attributes Screen")
            }
            item {
                DiceThrow(onClick = {})
            }
            if (state.showFateResilienceCard) {
                item {
                    FateResilienceCard(
                        baseFatePoints = state.baseFatePoints,
                        fatePoints = state.fatePoints,
                        baseResiliencePoints = state.baseResiliencePoints,
                        resiliencePoints = state.resiliencePoints,
                        extraPoints = state.extraPoints,
                        onFatePointsIncrease = { onEvent(CharacterAttributesEvent.IncreaseFatePoints) },
                        onFatePointsDecrease = { onEvent(CharacterAttributesEvent.DecreaseFatePoints) },
                        onResiliencePointsIncrease = { onEvent(CharacterAttributesEvent.IncreaseResiliencePoints) },
                        onResiliencePointsDecrease = { onEvent(CharacterAttributesEvent.DecreaseResiliencePoints) },
                    )
                }
                item {
                    Row {
                        CharacterCreatorButton(
                            text = "Back",
                            onClick = {
                                onEvent(CharacterAttributesEvent.OnDistributeFatePointsClick)
                            }
                        )
                        CharacterCreatorButton(
                            text = "Next",
                            onClick = {
                                onEvent(CharacterAttributesEvent.OnNextClick)
                            },
                            enabled = !state.isLoading && state.extraPoints == 0
                        )
                    }
                }
            } else {
                item {
                    if (attributes.isNotEmpty() && diceThrows.isNotEmpty() && baseAttributeValues.isNotEmpty()) {
                        Card {
                            AttributesTable(
                                attributes = attributes,
                                diceThrow = diceThrows,
                                baseAttributeValue = baseAttributeValues,
                                totalAttributeValue = baseAttributeValues,
                            )
                        }
                    }
                }
                item {
                    CharacterCreatorButton(
                        text = "Distribute fate points",
                        onClick = {
                            onEvent(CharacterAttributesEvent.OnDistributeFatePointsClick)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CharacterAttributesScreenPreview() {
    CharacterAttributesScreen(
        state = CharacterAttributesState(),
        onEvent = {},
        attributes = listOf(),
        diceThrows = listOf(),
        baseAttributeValues = listOf()
    )
}
