package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorSnackbarHost
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorTitle
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.DiceThrow
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.SnackbarType
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.showCharacterCreatorSnackbar
import com.kaesik.tabletopwarhammer.core.domain.library.items.AttributeItem
import com.kaesik.tabletopwarhammer.core.presentation.MainScaffold
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin

@Composable
fun CharacterAttributesScreenRoot(
    viewModel: AndroidCharacterAttributesViewModel = koinViewModel(),
    creatorViewModel: AndroidCharacterCreatorViewModel = getKoin().get(),
    characterSpecies: String,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val creatorState by creatorViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(creatorState.message, creatorState.isError) {
        creatorState.message?.let { message ->
            snackbarHostState.showCharacterCreatorSnackbar(
                message = message,
                type = if (creatorState.isError == true) SnackbarType.Error else SnackbarType.Success
            )
            creatorViewModel.onEvent(CharacterCreatorEvent.ClearMessage)
        }
    }

    LaunchedEffect(true) {
        viewModel.onEvent(CharacterAttributesEvent.InitAttributesList(characterSpecies))
        viewModel.onEvent(CharacterAttributesEvent.InitFateAndResilience(characterSpecies))
    }

    val attributes = state.attributeList
    val baseAttributeValues = state.baseAttributeValues.map { it.toString() }
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

                is CharacterAttributesEvent.RollAllDice -> {
                    viewModel.onEvent(event)
                    creatorViewModel.onEvent(CharacterCreatorEvent.AddExperience(50))
                    creatorViewModel.onEvent(CharacterCreatorEvent.ShowMessage("You gained 50 XP for rolling attributes!"))
                }

                is CharacterAttributesEvent.OnBackClick -> onBackClick()

                else -> viewModel.onEvent(event)
            }
        },
        attributes = attributes,
        diceThrows = diceThrows,
        baseAttributeValues = baseAttributeValues,
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun CharacterAttributesScreen(
    state: CharacterAttributesState,
    onEvent: (CharacterAttributesEvent) -> Unit,
    attributes: List<AttributeItem>,
    diceThrows: List<String>,
    baseAttributeValues: List<String>,
    snackbarHostState: SnackbarHostState
) {
    MainScaffold(
        title = "Attributes",
        snackbarHost = { CharacterCreatorSnackbarHost(snackbarHostState) },
        onBackClick = { onEvent(CharacterAttributesEvent.OnBackClick) },
        isLoading = state.isLoading,
        isError = state.isError,
        error = state.error,
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    CharacterCreatorTitle("Character Attributes Screen")
                }
                if (!state.hasRolledDice) {
                    item {
                        DiceThrow(onClick = { onEvent(CharacterAttributesEvent.RollAllDice) })
                    }
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
                                    diceThrow = state.rolledDiceResults.mapIndexed { index, result ->
                                        if (result == 0) state.diceThrows.getOrNull(index) ?: "0d0"
                                        else result.toString()
                                    },
                                    baseAttributeValue = baseAttributeValues,
                                    totalAttributeValue = state.totalAttributeValues.map { it.toString() }
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
    )
}

@Preview
@Composable
fun CharacterAttributesScreenPreview() {
    CharacterAttributesScreen(
        state = CharacterAttributesState(),
        onEvent = {},
        attributes = listOf(),
        diceThrows = listOf(),
        baseAttributeValues = listOf(),
        snackbarHostState = remember { SnackbarHostState() }
    )
}
