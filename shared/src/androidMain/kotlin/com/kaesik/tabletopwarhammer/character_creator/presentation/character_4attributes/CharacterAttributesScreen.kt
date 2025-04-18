package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes

import androidx.compose.foundation.layout.Arrangement
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
    onNextClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        viewModel.onEvent(CharacterAttributesEvent.InitAttributesList)
    }
    val attributes = state.attributeList
    CharacterAttributesScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is CharacterAttributesEvent.OnNextClick -> {
                    onNextClick()
                }

                else -> Unit
            }

            viewModel.onEvent(event)
        },
        attributes = attributes,
    )
}

@Composable
fun CharacterAttributesScreen(
    state: CharacterAttributesState,
    onEvent: (CharacterAttributesEvent) -> Unit,
    attributes: List<AttributeItem>,
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
                DiceThrow(
                    onClick = {}
                )
            }
            item {
                Card {
                    AttributesTable(
                        attributes = attributes,
                        diceThrow = List(attributes.size) { "1" },
                        baseAttributeValue = List(attributes.size) { "10" },
                        totalAttributeValue = List(attributes.size) { "11" },
                    )
                }
            }
            item {
                FateResilienceCard(
                    fatePoints = 10,
                    resiliencePoints = 10,
                    onFatePointsChange = {

                    },
                    onResiliencePointsChange = {

                    },
                    onNextClick = {
                        onEvent(CharacterAttributesEvent.OnNextClick)
                    }
                )
            }
            item {
                CharacterCreatorButton(
                    text = "distribute fate points",
                    onClick = {
                        println("CharacterClassAndCareerScreen")
                    }
                )
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
        attributes = listOf()
    )
}
