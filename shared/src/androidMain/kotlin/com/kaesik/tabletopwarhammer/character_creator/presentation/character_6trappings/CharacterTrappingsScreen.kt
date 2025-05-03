package com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.AndroidCharacterCreatorViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.CharacterCreatorEvent
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings.components.ClassOrCareer
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings.components.TrappingsTable
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorButton
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorTitle
import com.kaesik.tabletopwarhammer.core.domain.library.items.ItemItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterTrappingsScreenRoot(
    viewModel: AndroidCharacterTrappingsViewModel = koinViewModel(),
    creatorViewModel: AndroidCharacterCreatorViewModel,
    onNextClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val character = creatorViewModel.state.value.character

    LaunchedEffect(true) {
//        debugCheckTrappingsConsistency()
        viewModel.onEvent(
            CharacterTrappingsEvent.InitTrappingsList(
                from = ClassOrCareer.CLASS,
                className = character.cLass,
                careerPathName = character.careerPath
            )
        )
    }

    val trappings = state.trappingList
    val classTrappings = trappings.getOrNull(0) ?: emptyList()
    val careerTrappings = trappings.getOrNull(1) ?: emptyList()

    CharacterTrappingsScreen(
        state = state,
        classTrappings = classTrappings,
        careerTrappings = careerTrappings,
        onEvent = { event ->
            if (event is CharacterTrappingsEvent.OnNextClick) {
                val mergedTrappings = (classTrappings + careerTrappings).map { it.name }

                creatorViewModel.onEvent(
                    CharacterCreatorEvent.SetTrappings(
                        trappings = mergedTrappings
                    )
                )

                onNextClick()
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
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
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
                CharacterCreatorButton(
                    text = "Next",
                    onClick = {
                        onEvent(CharacterTrappingsEvent.OnNextClick)
                    }
                )
            }
        }
    }
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
