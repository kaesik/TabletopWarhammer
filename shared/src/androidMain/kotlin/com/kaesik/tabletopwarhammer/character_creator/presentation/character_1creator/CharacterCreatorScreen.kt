package com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.Button1
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterCreatorScreenRoot(
    viewModel: AndroidCharacterCreatorViewModel = koinViewModel(),
    onCreateCharacterSelect: () -> Unit,
    onRandomCharacterSelect: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CharacterCreatorScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is CharacterCreatorEvent.OnCreateCharacterSelect -> {
                    onCreateCharacterSelect()
                }

                is CharacterCreatorEvent.OnRandomCharacterSelect -> {
                    onRandomCharacterSelect()
                }

                else -> Unit
            }

            viewModel.onEvent(event)
        }
    )
}

@Composable
fun CharacterCreatorScreen(
    state: CharacterCreatorState,
    onEvent: (CharacterCreatorEvent) -> Unit
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
                Text("Character Creator Screen")
            }
            item {
                Text("Lorem ipsum dolor sit amet")
            }
            item {
                Text("Lorem ipsum dolor sit amet, impedit volumus vis te, cu eam ipsum saperet disputando. Eum nonumy corpora commune ne, graecis sententiae vix ad. Ei vel aperiam invidunt, ludus persius vix et. Dolorem complectitur mel in. In mel vide habeo, mei malorum indoctum intellegebat at, pri cibo minim ex. No pri case facer, cu vim dolorum impedit prodesset, ut justo phaedrum complectitur mel.")
            }
            item {
                Row {
                    Button1(
                        text = "Losuj Postać",
                        onClick = {
                            println("CharacterCreatorScreen")
                        }
                    )
                    Button1(
                        text = "Stwórz Postać",
                        onClick = {
                            println("CharacterCreatorScreen")
                            onEvent(
                                CharacterCreatorEvent.OnCreateCharacterSelect
                            )
                        }
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
        state = CharacterCreatorState(),
        onEvent = {}
    )
}
