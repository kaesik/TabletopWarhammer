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
        if (state.rolledDiceResults.isNotEmpty()) {
            creatorViewModel.onEvent(
                CharacterCreatorEvent.SetAttributes(
                    rolledAttributes = state.rolledDiceResults,
                    totalAttributes = state.totalAttributeValues
                )
            )
        }
    }

    // Save fate and resilience when they change
    LaunchedEffect(state.fatePoints, state.resiliencePoints) {
        creatorViewModel.onEvent(
            CharacterCreatorEvent.SetAttributes(
                fatePoints = state.fatePoints,
                resiliencePoints = state.resiliencePoints
            )
        )
    }

    // Initialize from creator flags when rolledFromAllocation changes
    LaunchedEffect(creatorState.rolledFromAllocation) {
        viewModel.onEvent(
            CharacterAttributesEvent.InitFromCreatorFlags(
                rolledFromAllocation = creatorState.rolledFromAllocation
            )
        )
    }

    CharacterAttributesScreen(
        state = state,
        hasRolledAttributes = creatorState.hasRolledAttributes,
        hasReorderedAttributes = creatorState.hasReorderedAttributes,
        hasAllocatedAttributes = creatorState.hasAllocatedAttributes,
        onEvent = { event ->
            when (event) {

                // Add XP after rolling all dices
                CharacterAttributesEvent.RollAllDice -> {
                    if (creatorState.rollLocked) return@CharacterAttributesScreen
                    if (creatorState.hasRolledAttributes) {
                        if (creatorState.hasReorderedAttributes) {
                            creatorViewModel.onEvent(
                                CharacterCreatorEvent.SetHasReorderAttributes(
                                    false
                                )
                            )
                        }
                        if (creatorState.hasAllocatedAttributes) {
                            creatorViewModel.onEvent(
                                CharacterCreatorEvent.SetHasAllocateAttributes(
                                    false
                                )
                            )
                        }
                    } else {
                        creatorViewModel.onEvent(CharacterCreatorEvent.AddExperience(50))
                        creatorViewModel.onEvent(CharacterCreatorEvent.SetHasRolledAttributes(true))
                        creatorViewModel.onEvent(CharacterCreatorEvent.SetRolledFromAllocation(false))
                        creatorViewModel.onEvent(CharacterCreatorEvent.ShowMessage("Rolled attributes (+50 XP)"))
                    }
                    viewModel.onEvent(event)
                }

                // Add XP after rolling a single dice
                CharacterAttributesEvent.RollOneDice -> {
                    if (creatorState.rollLocked) return@CharacterAttributesScreen
                    if (creatorState.hasRolledAttributes) {
                        if (creatorState.hasReorderedAttributes) {
                            creatorViewModel.onEvent(
                                CharacterCreatorEvent.SetHasReorderAttributes(
                                    false
                                )
                            )
                        }
                        if (creatorState.hasAllocatedAttributes) {
                            creatorViewModel.onEvent(
                                CharacterCreatorEvent.SetHasAllocateAttributes(
                                    false
                                )
                            )
                        }
                    } else {
                        val zeros = state.rolledDiceResults.count { it == 0 }
                        if (zeros == 1) {
                            creatorViewModel.onEvent(CharacterCreatorEvent.AddExperience(50))
                            creatorViewModel.onEvent(
                                CharacterCreatorEvent.SetHasRolledAttributes(
                                    true
                                )
                            )
                            creatorViewModel.onEvent(CharacterCreatorEvent.ShowMessage("Rolled attributes (+50 XP)"))
                        }
                    }
                    creatorViewModel.onEvent(CharacterCreatorEvent.SetRolledFromAllocation(false))
                    viewModel.onEvent(event)
                }

                // Toggle allocation mode and remove XP
                CharacterAttributesEvent.ToggleReorderMode -> {
                    val entering = !state.canReorderAttributes
                    if (entering && creatorState.hasRolledAttributes && !creatorState.hasReorderedAttributes) {
                        creatorViewModel.onEvent(CharacterCreatorEvent.RemoveExperience(25))
                        creatorViewModel.onEvent(CharacterCreatorEvent.SetHasReorderAttributes(true))
                        creatorViewModel.onEvent(CharacterCreatorEvent.ShowMessage("Reordered stats (−25 XP)"))
                    }
                    viewModel.onEvent(event)
                }

                // Toggle allocation mode and remove XP based on previous actions
                CharacterAttributesEvent.ConfirmAllocation -> {
                    if (!creatorState.hasAllocatedAttributes &&
                        state.canAllocateAttributes && state.allocatePointsLeft == 0
                    ) {
                        // Determine the penalty based on previous actions
                        val penalty = when {
                            // If attributes were not rolled, no penalty
                            !creatorState.hasRolledAttributes -> 0
                            // If attributes were rolled and reordered, apply a penalty
                            creatorState.hasReorderedAttributes -> 25
                            // If attributes were rolled but not reordered, apply a penalty
                            else -> 50
                        }

                        // Apply the penalty to the creatorViewModel
                        if (penalty > 0) creatorViewModel.onEvent(
                            CharacterCreatorEvent.RemoveExperience(
                                penalty
                            )
                        )
                        creatorViewModel.onEvent(CharacterCreatorEvent.SetHasAllocateAttributes(true))
                        val msg = when (penalty) {
                            50 -> "Allocated 100 points after roll (−50 XP)"
                            25 -> "Allocated 100 points after reorder (−25 XP)"
                            else -> "Allocated 100 points (+0 XP)"
                        }
                        creatorViewModel.onEvent(CharacterCreatorEvent.ShowMessage(msg))
                        creatorViewModel.onEvent(CharacterCreatorEvent.SetHasAllocateAttributes(true))
                    }
                    creatorViewModel.onEvent(CharacterCreatorEvent.SetRolledFromAllocation(true))
                    if (creatorState.hasRolledAttributes) {
                        creatorViewModel.onEvent(CharacterCreatorEvent.SetRollLocked(true))
                    }
                    viewModel.onEvent(event)
                }

                // Save the attributes and fate/resilience points
                CharacterAttributesEvent.OnNextClick -> {
                    creatorViewModel.onEvent(
                        CharacterCreatorEvent.SetAttributes(
                            totalAttributes = state.totalAttributeValues,
                            fatePoints = state.fatePoints,
                            resiliencePoints = state.resiliencePoints
                        )
                    )
                    onNextClick()
                }

                else -> viewModel.onEvent(event)
            }
        },
        isRollLocked = creatorState.rollLocked,
        attributes = state.attributeList,
        diceThrows = state.diceThrows,
        baseAttributeValues = state.baseAttributeValues.map { it.toString() },
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun CharacterAttributesScreen(
    state: CharacterAttributesState,
    hasRolledAttributes: Boolean,
    hasReorderedAttributes: Boolean,
    hasAllocatedAttributes: Boolean,
    onEvent: (CharacterAttributesEvent) -> Unit,
    isRollLocked: Boolean,
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
                        onMax = { index -> onEvent(CharacterAttributesEvent.AllocateMax(index)) },
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

                    // Button to roll fate and resilience points
                    Row {
                        CharacterCreatorButton(
                            text = "Roll",
                            onClick = { onEvent(CharacterAttributesEvent.RollFateAndResilience) }
                        )
                    }

                    // Button to go back or next
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

                    // Button to roll a single dice or all dice
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        // If rolled all dice and  rolled, show reorder button
                        val showReorder = !state.rolledFromAllocation && state.hasRolledAllDice

                        // Check if the roll button can be enabled
                        val canRoll = !state.isLoading && !isRollLocked &&
                                (state.rolledFromAllocation || state.rolledDiceResults.any { it == 0 })

                        // Buttons for rolling attributes or reordering
                        Column {
                            when {
                                // If attributes are rolled and can be reordered, show reorder button
                                showReorder -> {
                                    val entering = !state.canReorderAttributes
                                    val reorderLocked = hasReorderedAttributes
                                            && hasAllocatedAttributes
                                    CharacterCreatorButton(
                                        text = if (entering) "Reorder -25XP" else "Finish reordering",
                                        onClick = { onEvent(CharacterAttributesEvent.ToggleReorderMode) },
                                        enabled = !state.isLoading && (!entering || !reorderLocked)
                                    )
                                }

                                // If roll locked there is no button
                                isRollLocked -> {}

                                // If attributes are not rolled, show roll buttons
                                else -> {
                                    CharacterCreatorButton(
                                        text = "Roll a Dice +50XP",
                                        onClick = { onEvent(CharacterAttributesEvent.RollOneDice) },
                                        enabled = canRoll
                                    )
                                    CharacterCreatorButton(
                                        text = "Roll All Dices +50XP",
                                        onClick = { onEvent(CharacterAttributesEvent.RollAllDice) },
                                        enabled = canRoll
                                    )
                                }
                            }
                        }

                        val allocateLabel = when {
                            hasAllocatedAttributes -> "Reallocate"
                            !hasRolledAttributes -> "Allocate 100 points"
                            hasReorderedAttributes -> "Allocate 100 points −25XP"
                            else -> "Allocate 100 points −50XP"
                        }

                        CharacterCreatorButton(
                            text = allocateLabel,
                            onClick = { onEvent(CharacterAttributesEvent.ToggleAllocationMode) },
                            enabled = !state.isLoading
                        )
                    }

                    // Show the attributes table if there are attributes, dice throws, and base values
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
        hasRolledAttributes = false,
        hasReorderedAttributes = false,
        hasAllocatedAttributes = false,
        onEvent = {},
        isRollLocked = false,
        attributes = listOf(),
        diceThrows = listOf(),
        baseAttributeValues = listOf(),
        snackbarHostState = remember { SnackbarHostState() }
    )
}
