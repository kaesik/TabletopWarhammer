package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.components.AllocationCard
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.components.AttributesTable
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.components.FateResilienceCard
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorButton
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorSnackbarHost
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
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val creatorState by creatorViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    // Handle messages from the creatorViewModel
    LaunchedEffect(creatorState.message, creatorState.isError) {
        creatorState.message?.let { message ->
            snackbarHostState.showCharacterCreatorSnackbar(
                message = message,
                type = if (creatorState.isError) SnackbarType.Error else SnackbarType.Success
            )
            creatorViewModel.onEvent(CharacterCreatorEvent.ClearMessage)
        }
    }

    // Initialize attributes and fate/resilience points
    LaunchedEffect(Unit) {
        val savedRolled = creatorState.rolledAttributes.takeIf { it.isNotEmpty() }
        val savedTotal = creatorState.totalAttributes.takeIf { it.isNotEmpty() }

        viewModel.onEvent(
            CharacterAttributesEvent.InitAttributesList(
                speciesName = characterSpecies,
                rolled = savedRolled,
                total = savedTotal
            )
        )

        val savedFate = creatorState.character.fate.takeIf { it > 0 }
        val savedRes = creatorState.character.resilience.takeIf { it > 0 }
        viewModel.onEvent(
            CharacterAttributesEvent.InitFateAndResilience(
                speciesName = characterSpecies,
                fate = savedFate,
                resilience = savedRes
            )
        )
    }

    // Save rolled attributes and total values when they change
    LaunchedEffect(state.rolledDiceResults, state.totalAttributeValues) {
        creatorViewModel.onEvent(
            CharacterCreatorEvent.SaveRolledAttributes(
                rolled = state.rolledDiceResults,
                total = state.totalAttributeValues
            )
        )
    }

    // Get the attributes selection from the view model
    LaunchedEffect(state.pendingAttributesSelection) {
        val selection = state.pendingAttributesSelection ?: return@LaunchedEffect

        // Set attributes in the creatorViewModel
        creatorViewModel.onEvent(
            CharacterCreatorEvent.SetAttributes(
                totalAttributes = selection.totalAttributes ?: return@LaunchedEffect,
                fatePoints = selection.fatePoints ?: return@LaunchedEffect,
                resiliencePoints = selection.resiliencePoints ?: return@LaunchedEffect
            )
        )

        // Consume the attributes selection
        viewModel.onEvent(CharacterAttributesEvent.OnAttributesSelectionConsumed)
    }

    // Get the fate and resilience selection from the view model
    LaunchedEffect(state.pendingFateResilienceSelection) {
        val selection = state.pendingFateResilienceSelection ?: return@LaunchedEffect

        // Set fate and resilience points in the creatorViewModel
        val totals = if (creatorState.totalAttributes.isNotEmpty()) creatorState.totalAttributes
        else state.totalAttributeValues
        creatorViewModel.onEvent(
            CharacterCreatorEvent.SetAttributes(
                totalAttributes = totals,
                fatePoints = selection.fatePoints ?: 0,
                resiliencePoints = selection.resiliencePoints ?: 0
            )
        )

        // Consume the fate and resilience selection
        viewModel.onEvent(CharacterAttributesEvent.OnFateResilienceSelectionConsumed)
    }

    // Get the random selection from the view model
    LaunchedEffect(state.pendingRandomSelection) {
        val selection = state.pendingRandomSelection ?: return@LaunchedEffect
        if (selection.exp != 0) {
            creatorViewModel.onEvent(CharacterCreatorEvent.AddExperience(selection.exp))
        }
        if (selection.message.isNotBlank()) {
            creatorViewModel.onEvent(CharacterCreatorEvent.ShowMessage(selection.message))
        }
        viewModel.onEvent(CharacterAttributesEvent.OnRandomSelectionConsumed)
    }

    CharacterAttributesScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is CharacterAttributesEvent.OnNextClick -> onNextClick()

                else -> viewModel.onEvent(event)
            }
        },
        attributes = state.attributeList,
        diceThrows = state.diceThrows,
        baseAttributeValues = state.baseAttributeValues.map { it.toString() },
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
        isLoading = state.isLoading,
        isError = state.isError,
        error = state.error,
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Button to roll a single dice or all dice
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Column {
                        if (!state.hasRolledAttributes) {
                            CharacterCreatorButton(
                                text = "Roll a Dice +50XP",
                                onClick = { onEvent(CharacterAttributesEvent.RollOneDice) },
                                enabled = !state.isLoading && state.rolledDiceResults.any { it == 0 }
                            )
                            CharacterCreatorButton(
                                text = "Roll All Dices +50XP",
                                onClick = { onEvent(CharacterAttributesEvent.RollAllDice) },
                                enabled = !state.isLoading && state.rolledDiceResults.any { it == 0 }
                            )
                        } else {
                            CharacterCreatorButton(
                                text = "Reorder -25XP",
                                onClick = { onEvent(CharacterAttributesEvent.ToggleReorderMode) },
                                enabled = !state.isLoading && !state.reorderXpApplied
                            )
                        }
                    }
                    val allocateLabel = when {
                        !state.hasRolledAttributes -> "Allocate 100 points +0XP"
                        state.hasRolledAttributes && !state.hasReorderedAttributes -> "Allocate 100 points −50XP"
                        else -> "Allocate 100 points −25XP"
                    }

                    CharacterCreatorButton(
                        text = allocateLabel,
                        onClick = { onEvent(CharacterAttributesEvent.ToggleAllocationMode) },
                        enabled = !state.isLoading
                    )
                }

                if (state.canAllocateAttributes) {
                    AllocationCard(
                        attributes = attributes,
                        values = state.allocatedDiceResults,
                        pointsLeft = state.allocatePointsLeft,
                        onPlus = { index -> onEvent(CharacterAttributesEvent.IncrementAllocate(index)) },
                        onMinus = { index ->
                            onEvent(
                                CharacterAttributesEvent.DecrementAllocate(
                                    index
                                )
                            )
                        },
                        onConfirm = { onEvent(CharacterAttributesEvent.ConfirmAllocation) },
                        onCancel = { onEvent(CharacterAttributesEvent.CancelAllocation) }
                    )
                } else if (state.showFateResilienceCard) {
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
                } else {
                    if (attributes.isNotEmpty() && diceThrows.isNotEmpty() && baseAttributeValues.isNotEmpty()) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            AttributesTable(
                                attributes = attributes,
                                diceThrows = state.rolledDiceResults.map { it.toString() },
                                baseAttributeValues = baseAttributeValues,
                                totalAttributeValues = state.totalAttributeValues.map { it.toString() },
                                onOrderChange = { reordered ->
                                    val rolled = reordered.map { it.toIntOrNull() ?: 0 }
                                    onEvent(CharacterAttributesEvent.UpdateDiceThrowOrder(rolled))
                                },
                                canReorderAttributes = state.canReorderAttributes
                            )
                        }
                    }

                    Row {
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
