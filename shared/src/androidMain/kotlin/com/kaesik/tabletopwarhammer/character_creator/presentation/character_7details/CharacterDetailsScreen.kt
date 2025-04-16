package com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details.components.DetailInput
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorButton
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorTitle
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterDetailsScreenRoot(
    viewModel: AndroidCharacterDetailsViewModel = koinViewModel(),
    onNextClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CharacterDetailsScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is CharacterDetailsEvent.OnNextClick -> {
                    onNextClick()
                }

                else -> Unit
            }

            viewModel.onEvent(event)
        }
    )
}

@Composable
fun CharacterDetailsScreen(
    state: CharacterDetailsState,
    onEvent: (CharacterDetailsEvent) -> Unit
) {
    Scaffold(

    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                CharacterCreatorTitle("Character Creator Screen")
            }
            item {
                DetailInput(
                    label = "Name",
                    value = "",
                    onValueChange = { },
                    onRandomClick = { },
                )
            }
            item {
                DetailInput(
                    label = "Surname",
                    value = "",
                    onValueChange = { },
                    onRandomClick = { },
                )
            }
            item {
                DetailInput(
                    label = "Age",
                    value = "",
                    onValueChange = { },
                    onRandomClick = { },
                )
            }
            item {
                DetailInput(
                    label = "Eyes Color",
                    value = "",
                    onValueChange = { },
                    onRandomClick = { },
                )
            }
            item {
                DetailInput(
                    label = "Hair Color",
                    value = "",
                    onValueChange = { },
                    onRandomClick = { },
                )
            }
            item {
                DetailInput(
                    label = "Height",
                    value = "",
                    onValueChange = { },
                    onRandomClick = { },
                )
            }
            item {
                CharacterCreatorButton(
                    text = "Next",
                    onClick = {
                        println("CharacterTrappingsScreen")
                        onEvent(CharacterDetailsEvent.OnNextClick)
                    }
                )
            }
        }

    }
}

@Preview
@Composable
fun CharacterDetailsScreenPreview() {
    CharacterDetailsScreen(
        state = CharacterDetailsState(),
        onEvent = {}
    )
}
