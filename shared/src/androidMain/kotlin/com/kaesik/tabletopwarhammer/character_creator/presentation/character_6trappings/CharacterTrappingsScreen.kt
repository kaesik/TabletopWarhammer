package com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.AndroidCharacterCreatorViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.CharacterCreatorEvent
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings.components.TrappingsTable
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorButton
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorTitle
import com.kaesik.tabletopwarhammer.core.presentation.components.SnackbarType
import com.kaesik.tabletopwarhammer.core.presentation.components.showWarhammerSnackbar
import com.kaesik.tabletopwarhammer.core.presentation.MainScaffold
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin

@Composable
fun CharacterTrappingsScreenRoot(
    viewModel: AndroidCharacterTrappingsViewModel = koinViewModel(),
    creatorViewModel: AndroidCharacterCreatorViewModel = getKoin().get(),
    onNextClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val creatorState by creatorViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val character = creatorViewModel.state.value.character

    // Handle messages from the creatorViewModel
    LaunchedEffect(creatorState.message, creatorState.isError) {
        creatorState.message?.let { message ->
            snackbarHostState.showWarhammerSnackbar(
                message = message,
                type = if (creatorState.isError) SnackbarType.Error else SnackbarType.Success
            )
            creatorViewModel.onEvent(CharacterCreatorEvent.ClearMessage)
        }
    }

    // Initialize trappings list
    LaunchedEffect(Unit) {
        viewModel.onEvent(
            CharacterTrappingsEvent.InitTrappingsList(
                className = character.cLass,
                careerPathName = character.careerPath
            )
        )
    }

    // Initialize wealth
    LaunchedEffect(Unit) {
        // If wealth was already generated in the creator, use that
        if (creatorState.hasGeneratedWealth) {
            viewModel.onEvent(CharacterTrappingsEvent.SetWealthCached(character.wealth))
        } else {
            viewModel.onEvent(CharacterTrappingsEvent.InitWealth(careerPathName = character.careerPath))
        }
    }

    // Autosave wealth to creatorViewModel when it's loaded and character wealth is empty
    LaunchedEffect(state.wealth) {
        if (state.wealth.isNotEmpty() && !creatorState.hasGeneratedWealth) {
            creatorViewModel.onEvent(CharacterCreatorEvent.SetWealth(state.wealth))
        }
    }

    CharacterTrappingsScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is CharacterTrappingsEvent.OnNextClick -> {
                    // Merge class and career trappings
                    val mergedTrappings = (state.classTrappings + state.careerTrappings)
                        .map { it.name }

                    // Set trappings in the creator view model
                    creatorViewModel.onEvent(
                        CharacterCreatorEvent.SetTrappings(
                            trappings = mergedTrappings
                        )
                    )

                    // Set wealth in the creator view model
                    creatorViewModel.onEvent(
                        CharacterCreatorEvent.SetWealth(
                            wealth = state.wealth
                        )
                    )

                    onNextClick()
                }

                else -> Unit
            }
            viewModel.onEvent(event)
        }
    )
}

@Composable
fun CharacterTrappingsScreen(
    state: CharacterTrappingsState,
    onEvent: (CharacterTrappingsEvent) -> Unit,
) {
    MainScaffold(
        title = "Trappings",
        isLoading = state.isLoading,
        isError = state.isError,
        error = state.error,
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // Class Trappings
                item {
                    CharacterCreatorTitle("Class Trappings")
                }
                item {
                    TrappingsTable(trappings = state.classTrappings)
                }

                // Career Trappings
                item {
                    CharacterCreatorTitle("Career Trappings")
                }
                item {
                    TrappingsTable(trappings = state.careerTrappings)
                }

                // Starting Wealth
                item {
                    CharacterCreatorTitle("Starting Wealth")
                }
                item {
                    Text(
                        text = formatWealth(state.wealth),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Next Button
                item {
                    CharacterCreatorButton(
                        text = "Next",
                        onClick = {
                            onEvent(CharacterTrappingsEvent.OnNextClick)
                        }
                    )
                }
            }
        }
    )
}

// Format wealth list [brass, silver, gold] into a readable string
fun formatWealth(wealth: List<Int>): String {
    val (brass, silver, gold) = wealth + List(3 - wealth.size) { 0 }
    return buildList {
        if (brass > 0) add("$brass Brass pennies")
        if (silver > 0) add("$silver Silver shillings")
        if (gold > 0) add("$gold Gold crowns")
    }.ifEmpty { listOf("No starting wealth") }.joinToString(", ")
}

private fun List<Int>.isWealthSet(): Boolean = size >= 3 && any { it > 0 }

@Preview
@Composable
fun CharacterTrappingsScreenPreview() {
    CharacterTrappingsScreen(
        state = CharacterTrappingsState(),
        onEvent = {}
    )
}
