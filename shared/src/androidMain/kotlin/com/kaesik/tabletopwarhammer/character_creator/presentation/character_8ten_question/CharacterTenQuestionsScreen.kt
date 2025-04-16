package com.kaesik.tabletopwarhammer.character_creator.presentation.character_8ten_question

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterCreatorScreenRoot(
    viewModel: AndroidCharacterTenQuestionsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CharacterCreatorScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                else -> Unit
            }

            viewModel.onEvent(event)
        }
    )
}

@Composable
fun CharacterCreatorScreen(
    state: CharacterTenQuestionsState,
    onEvent: (CharacterTenQuestionsEvent) -> Unit
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
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("Character Creator Screen")
                    CharacterCreatorButton(
                        text = "Button 1",
                        onClick = { }
                    )
                    CharacterCreatorButton(
                        text = "Button 2",
                        onClick = { }
                    )
                    CharacterCreatorButton(
                        text = "Button 3",
                        onClick = { }
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun CharacterCreatorScreenPreview() {
    CharacterCreatorScreen(
        state = CharacterTenQuestionsState(),
        onEvent = {}
    )
}
