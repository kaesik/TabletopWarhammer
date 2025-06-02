package com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.AndroidCharacterCreatorViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.CharacterCreatorEvent
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings.components.ClassOrCareer
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings.components.TrappingsTable
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorButton
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorTitle
import com.kaesik.tabletopwarhammer.core.domain.library.items.ItemItem
import com.kaesik.tabletopwarhammer.core.presentation.MainScaffold
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin

@Composable
fun CharacterTrappingsScreenRoot(
    viewModel: AndroidCharacterTrappingsViewModel = koinViewModel(),
    creatorViewModel: AndroidCharacterCreatorViewModel = getKoin().get(),
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val character = creatorViewModel.state.value.character

    LaunchedEffect(true) {
        viewModel.onEvent(
            CharacterTrappingsEvent.InitTrappingsList(
                from = ClassOrCareer.CLASS,
                className = character.cLass,
                careerPathName = character.careerPath
            )
        )

        if (viewModel.state.value.wealth.isEmpty()) {
            viewModel.onEvent(
                CharacterTrappingsEvent.InitWealthList(
                    careerPathName = character.careerPath
                )
            )
        }
    }

    val trappings = state.trappingList
    val classTrappings = trappings.getOrNull(0) ?: emptyList()
    val careerTrappings = trappings.getOrNull(1) ?: emptyList()

    CharacterTrappingsScreen(
        state = state,
        classTrappings = classTrappings,
        careerTrappings = careerTrappings,
        onEvent = { event ->
            when (event) {
                is CharacterTrappingsEvent.OnNextClick -> {
                    val mergedTrappings = (classTrappings + careerTrappings).map { it.name }

                    creatorViewModel.onEvent(
                        CharacterCreatorEvent.SetTrappings(
                            trappings = mergedTrappings
                        )
                    )

                    creatorViewModel.onEvent(
                        CharacterCreatorEvent.SetWealth(
                            wealth = state.wealth
                        )
                    )

                    onNextClick()
                }

                is CharacterTrappingsEvent.OnBackClick -> onBackClick()

                else -> Unit
            }
            viewModel.onEvent(event)
        }
    )
}

@Composable
fun CharacterTrappingsScreen(
    state: CharacterTrappingsState,
    classTrappings: List<ItemItem>,
    careerTrappings: List<ItemItem>,
    onEvent: (CharacterTrappingsEvent) -> Unit,
) {
    MainScaffold(
        title = "Trappings",
        onBackClick = { onEvent(CharacterTrappingsEvent.OnBackClick) },
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
                item {
                    CharacterCreatorTitle("Character Trappings Screen")
                }
                item {
                    CharacterCreatorTitle("Class Trappings")
                }
                item {
                    TrappingsTable(trappings = classTrappings)
                }
                item {
                    CharacterCreatorTitle("Career Trappings")
                }
                item {
                    TrappingsTable(trappings = careerTrappings)
                }
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

fun formatWealth(wealth: List<Int>): String {
    val (brass, silver, gold) = wealth + List(3 - wealth.size) { 0 }
    return buildList {
        if (brass > 0) add("$brass Brass pennies")
        if (silver > 0) add("$silver Silver shillings")
        if (gold > 0) add("$gold Gold crowns")
    }.ifEmpty { listOf("No starting wealth") }.joinToString(", ")
}

@Preview
@Composable
fun CharacterTrappingsScreenPreview() {
    CharacterTrappingsScreen(
        state = CharacterTrappingsState(),
        classTrappings = TODO(),
        careerTrappings = TODO(),
        onEvent = {}
    )
}
