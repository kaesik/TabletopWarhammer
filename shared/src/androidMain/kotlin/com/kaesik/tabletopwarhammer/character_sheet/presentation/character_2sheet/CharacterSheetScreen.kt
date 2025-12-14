package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.WarhammerTabChip
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.CharacterSheetCombatTab
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.CharacterSheetInventoryTab
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.CharacterSheetNotesTab
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.CharacterSheetSpellsAndPrayersTab
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.CharacterSheetTab
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.attributes.CharacterSheetAttributesTab
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.general.CharacterSheetGeneralTab
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.party.CharacterSheetPartyTab
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.points.CharacterSheetPointsTab
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.skills.CharacterSheetSkillsTab
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.talents.CharacterSheetTalentsTab
import com.kaesik.tabletopwarhammer.core.presentation.MainScaffold
import com.kaesik.tabletopwarhammer.core.presentation.components.SnackbarType
import com.kaesik.tabletopwarhammer.core.presentation.components.WarhammerButton
import com.kaesik.tabletopwarhammer.core.presentation.components.showWarhammerSnackbar
import org.koin.androidx.compose.koinViewModel

// TODO: CLEAN THAT THING UP LATER
@Composable
fun CharacterSheetScreenRoot(
    viewModel: AndroidCharacterSheetViewModel = koinViewModel(),
    characterId: Int
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.message, state.isError) {
        state.message?.let { message ->
            snackbarHostState.showWarhammerSnackbar(
                message = message,
                type = if (state.isError) SnackbarType.Error else SnackbarType.Success
            )
            viewModel.onEvent(CharacterSheetEvent.ClearMessage)
        }
    }

    LaunchedEffect(characterId) {
        viewModel.onEvent(CharacterSheetEvent.LoadCharacterById(characterId))
    }

    if (state.isLoading) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator() }
        return
    }

    val character = state.character
    if (character == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No character", style = MaterialTheme.typography.bodyLarge)
        }
        return
    }

    CharacterSheetScreen(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun CharacterSheetScreen(
    state: CharacterSheetState,
    onEvent: (CharacterSheetEvent) -> Unit
) {
    val character = state.character ?: return

    var currentTab by rememberSaveable { mutableStateOf(CharacterSheetTab.General) }
    val tabs = CharacterSheetTab.entries

    MainScaffold(
        title = character.name,
        isLoading = state.isLoading,
        isError = state.isError,
        error = state.error,
        content = {
            Column(Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    WarhammerButton(
                        onClick = { onEvent(CharacterSheetEvent.SaveCharacter) },
                        text = if (state.isSaving) "Saving..." else "Save",
                        isLoading = state.isSaving,
                        enabled = !state.isSaving
                    )
                }

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(tabs.size) { index ->
                        val tab = tabs[index]
                        val selected = tab == currentTab

                        WarhammerTabChip(
                            text = tab.title,
                            selected = selected,
                            onClick = { currentTab = tab }
                        )
                    }
                }

                when (currentTab) {
                    CharacterSheetTab.General -> CharacterSheetGeneralTab(
                        character = character,
                        onEvent = onEvent
                    )

                    CharacterSheetTab.Points -> CharacterSheetPointsTab(
                        character = character,
                        onEvent = onEvent
                    )

                    CharacterSheetTab.Attributes -> CharacterSheetAttributesTab(
                        character = character,
                        onEvent = onEvent
                    )

                    CharacterSheetTab.Skills -> CharacterSheetSkillsTab(
                        character = character,
                        onEvent = onEvent
                    )

                    CharacterSheetTab.Talents -> CharacterSheetTalentsTab(
                        character = character,
                        onEvent = onEvent
                    )

                    CharacterSheetTab.Inventory -> CharacterSheetInventoryTab(
                        character = character,
                        onCharacterChange = { updated ->
                            onEvent(CharacterSheetEvent.UpdateCharacter(updated))
                        }
                    )

                    CharacterSheetTab.SpellsAndPrayers -> CharacterSheetSpellsAndPrayersTab(
                        character = character,
                        onCharacterChange = { updated ->
                            onEvent(CharacterSheetEvent.UpdateCharacter(updated))
                        }
                    )

                    CharacterSheetTab.Party -> CharacterSheetPartyTab(
                        character = character,
                        onEvent = onEvent
                    )

                    CharacterSheetTab.Combat -> CharacterSheetCombatTab(
                        character = character,
                        onCharacterChange = { updated ->
                            onEvent(CharacterSheetEvent.UpdateCharacter(updated))
                        }
                    )

                    CharacterSheetTab.Notes -> CharacterSheetNotesTab(
                        character = character,
                        onCharacterChange = { updated ->
                            onEvent(CharacterSheetEvent.UpdateCharacter(updated))
                        }
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun CharacterSheetScreenPreview() {
    CharacterSheetScreen(
        state = CharacterSheetState(),
        onEvent = {},
    )
}
