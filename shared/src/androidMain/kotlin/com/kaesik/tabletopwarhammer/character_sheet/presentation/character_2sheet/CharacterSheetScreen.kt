package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.CharacterSheetAttributesTab
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.CharacterSheetCombatTab
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.CharacterSheetGeneralTab
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.CharacterSheetInventoryTab
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.CharacterSheetNotesTab
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.CharacterSheetPartyTab
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.CharacterSheetPointsTab
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.CharacterSheetSkillsTab
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.CharacterSheetSpellsAndPrayersTab
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.CharacterSheetTab
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.CharacterSheetTalentsTab
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.presentation.MainScaffold
import com.kaesik.tabletopwarhammer.core.presentation.components.SnackbarType
import com.kaesik.tabletopwarhammer.core.presentation.components.WarhammerButton
import com.kaesik.tabletopwarhammer.core.presentation.components.showWarhammerSnackbar
import org.koin.androidx.compose.koinViewModel

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
        }
        viewModel.onEvent(CharacterSheetEvent.ClearMessage)
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
        character = character,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun CharacterSheetScreen(
    state: CharacterSheetState,
    character: CharacterItem,
    onEvent: (CharacterSheetEvent) -> Unit
) {
    var currentTab by rememberSaveable { mutableStateOf(CharacterSheetTab.General) }
    val tabs = CharacterSheetTab.entries

    var editableCharacter by remember(character.id) {
        mutableStateOf(character)
    }

    MainScaffold(
        title = editableCharacter.name,
        isLoading = state.isLoading,
        isError = state.isError,
        error = state.error,
        content = {
            Column(Modifier.fillMaxSize()) {

                // Pasek z przyciskiem "Save"
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    WarhammerButton(
                        onClick = {
                            onEvent(CharacterSheetEvent.SaveCharacter(editableCharacter))
                        },
                        text = if (state.isSaving) "Saving..." else "Save",
                        isLoading = state.isSaving,  // <-- TU ważna zmiana
                        enabled = !state.isSaving    // opcjonalnie blokujemy podczas zapisu
                    )
                }

                TabRow(selectedTabIndex = tabs.indexOf(currentTab)) {
                    tabs.forEach { tab ->
                        Tab(
                            selected = tab == currentTab,
                            onClick = { currentTab = tab },
                            text = { Text(tab.title) }
                        )
                    }
                }

                when (currentTab) {
                    CharacterSheetTab.General -> {
                        CharacterSheetGeneralTab(
                            character = editableCharacter,
                            onCharacterChange = { editableCharacter = it }
                        )
                    }

                    CharacterSheetTab.Points -> {
                        CharacterSheetPointsTab(
                            character = editableCharacter,
                            onCharacterChange = { editableCharacter = it }
                        )
                    }

                    CharacterSheetTab.Attributes -> {
                        CharacterSheetAttributesTab(
                            character = editableCharacter,
                            onCharacterChange = { editableCharacter = it }
                        )
                    }

                    CharacterSheetTab.Skills -> {
                        CharacterSheetSkillsTab(
                            character = editableCharacter,
                            onCharacterChange = { editableCharacter = it }
                        )
                    }

                    CharacterSheetTab.Talents -> {
                        CharacterSheetTalentsTab(
                            character = editableCharacter,
                            onCharacterChange = { editableCharacter = it }
                        )
                    }

                    CharacterSheetTab.Inventory -> {
                        CharacterSheetInventoryTab(
                            character = editableCharacter,
                            onCharacterChange = { editableCharacter = it }
                        )
                    }

                    CharacterSheetTab.SpellsAndPrayers -> {
                        CharacterSheetSpellsAndPrayersTab(
                            character = editableCharacter,
                            onCharacterChange = { editableCharacter = it }
                        )
                    }

                    CharacterSheetTab.Party -> {
                        CharacterSheetPartyTab(
                            character = editableCharacter,
                            onCharacterChange = { editableCharacter = it }
                        )
                    }

                    CharacterSheetTab.Combat -> {
                        CharacterSheetCombatTab(
                            character = editableCharacter,
                            onCharacterChange = { editableCharacter = it }
                        )
                    }

                    CharacterSheetTab.Notes -> {
                        CharacterSheetNotesTab(
                            character = editableCharacter,
                            onCharacterChange = { editableCharacter = it }
                        )
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun CharacterSheetScreenPreview() {
    CharacterSheetScreen(
        state = CharacterSheetState(
            isLoading = false,
            isError = false,
            error = null,
            character = null
        ),
        onEvent = {},
        character = CharacterItem(
            id = 1,
            name = "Test",
            species = "Human",
            cLass = "Warrior",
            career = "Soldier",
            careerLevel = "Recruit",
            careerPath = "Soldier",
            status = "Brass 2",
            age = "25",
            height = "175 cm",
            hair = "Brown",
            eyes = "Blue",
            weaponSkill = listOf(30),
            ballisticSkill = listOf(25),
            strength = listOf(32),
            toughness = listOf(34),
            initiative = listOf(28),
            agility = listOf(29),
            dexterity = listOf(30),
            intelligence = listOf(31),
            willPower = listOf(33),
            fellowship = listOf(35),
            fate = 2,
            fortune = 2,
            resilience = 1,
            resolve = 1,
            motivation = "Honor",
            experience = listOf(0),
            movement = 4,
            walk = 8,
            run = 12,
            basicSkills = emptyList(),
            advancedSkills = emptyList(),
            talents = emptyList(),
            ambitionShortTerm = "",
            ambitionLongTerm = "",
            partyName = "",
            partyAmbitionShortTerm = "",
            partyAmbitionLongTerm = "",
            partyMembers = emptyList(),
            armour = emptyList(),
            weapons = emptyList(),
            trappings = emptyList(),
            psychology = emptyList(),
            mutations = emptyList(),
            wealth = listOf(10, 0, 0),
            encumbrance = emptyList(),
            wounds = emptyList(),
            spells = emptyList(),
            prayers = emptyList(),
            sin = 0,
            createdAt = "",
            updatedAt = "",
            deletedAt = ""
        )
    )
}
