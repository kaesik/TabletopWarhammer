package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.components.AttributesTable
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.components.FateResilienceCard
import com.kaesik.tabletopwarhammer.library.presentation.components.Button1
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
    attributes: List<String>,
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
                Text("Character Attributes Screen")
            }
            item {
                Card {
                    AttributesTable(
                        attributes = attributes,
                        diceThrow = listOf("1", "2", "3", "4", "5", "6", "7", "8"),
                        baseAttributeValue = listOf("10", "20", "30", "40", "50", "60", "70", "80"),
                        totalAttributeValue = listOf("11", "22", "33", "44", "55", "66", "77", "88"),
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
                Button1(
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
        attributes = listOf(
            "Weapon Skill",
            "Ballistic Skill",
            "Strength",
            "Toughness",
            "Agility",
            "Intelligence",
            "Willpower",
            "Fellowship"
        )
    )
}
