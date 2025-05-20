package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_sheet_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterSheetListScreenRoot(
    viewModel: AndroidCharacterSheetListViewModel = koinViewModel(),
    onCharacterClick: (CharacterItem) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(CharacterSheetListEvent.LoadCharacters)
    }

    CharacterSheetListScreen(
        state = state,
        onCharacterClick = {
            viewModel.onEvent(CharacterSheetListEvent.OnCharacterClick(it))
            onCharacterClick(it)
        },
        onDeleteClick = {
            viewModel.onEvent(CharacterSheetListEvent.OnDeleteCharacter(it))
        }
    )
}

@Composable
fun CharacterSheetListScreen(
    state: CharacterSheetListState,
    onCharacterClick: (CharacterItem) -> Unit,
    onDeleteClick: (CharacterItem) -> Unit
) {
    Scaffold { padding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (state.characters.isEmpty()) {
                    item {
                        Text(
                            "Brak postaci.",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                } else {
                    items(state.characters, key = { it.id }) { character ->
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .clickable { onCharacterClick(character) }
                                            .weight(1f)
                                    ) {
                                        Text(
                                            text = character.name,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(
                                            "${character.species} - ${character.career}",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Usuń postać",
                                        modifier = Modifier
                                            .padding(start = 8.dp)
                                            .clickable { onDeleteClick(character) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CharacterSheetListScreenPreview() {
    CharacterSheetListScreen(
        state = CharacterSheetListState(),
        onCharacterClick = {},
        onDeleteClick = {}
    )
}
