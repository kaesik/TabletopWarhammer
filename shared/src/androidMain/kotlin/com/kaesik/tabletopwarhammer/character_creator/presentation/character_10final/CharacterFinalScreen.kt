package com.kaesik.tabletopwarhammer.character_creator.presentation.character_10final

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.AndroidCharacterCreatorViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.CharacterCreatorEvent
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorButton
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.SnackbarType
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.showCharacterCreatorSnackbar
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterDataSource
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.domain.character.getAttributeValue
import com.kaesik.tabletopwarhammer.core.presentation.components.InfoText
import com.kaesik.tabletopwarhammer.core.presentation.components.SectionTitle
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin

@Composable
fun CharacterFinalScreenRoot(
    viewModel: AndroidCharacterFinalViewModel = koinViewModel(),
    creatorViewModel: AndroidCharacterCreatorViewModel = getKoin().get(),
    characterDataSource: CharacterDataSource = getKoin().get(),
    onSaveClick: () -> Unit,
) {
    val creatorState by creatorViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val character = creatorViewModel.state.value.character

    var isSaving by remember { mutableStateOf(false) }

    LaunchedEffect(creatorState.message, creatorState.isError) {
        creatorState.message?.let { message ->
            snackbarHostState.showCharacterCreatorSnackbar(
                message = message,
                type = if (creatorState.isError == true) SnackbarType.Error else SnackbarType.Success
            )
            creatorViewModel.onEvent(CharacterCreatorEvent.ClearMessage)
        }
    }

    LaunchedEffect(isSaving) {
        if (isSaving) {
            try {
                characterDataSource.insertCharacter(character)

                snackbarHostState.showCharacterCreatorSnackbar(
                    message = "Character saved successfully! [${character.name}]",
                    type = SnackbarType.Success
                )

                onSaveClick()
                isSaving = false
            } catch (e: Exception) {
                snackbarHostState.showCharacterCreatorSnackbar(
                    message = "Error saving character: ${e.localizedMessage}",
                    type = SnackbarType.Error
                )
            } finally {
                isSaving = false
            }
        }
    }

    CharacterFinalScreen(
        character = character,
        snackbarHostState = snackbarHostState,
        isSaving = isSaving,
        onSaveClick = {
            if (!isSaving) {
                isSaving = true
            }
        }
    )
}

@Composable
fun CharacterFinalScreen(
    character: CharacterItem,
    snackbarHostState: SnackbarHostState,
    isSaving: Boolean,
    onSaveClick: () -> Unit,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = remember { snackbarHostState })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            item { SectionTitle("General Information") }
            item { InfoText("Name", character.name) }
            item { InfoText("Species", character.species) }
            item { InfoText("Class", character.cLass) }
            item { InfoText("Career", character.career) }
            item { InfoText("Career Level", character.careerLevel) }
            item { InfoText("Career Path", character.careerPath) }
            item { InfoText("Status", character.status) }
            item { InfoText("Age", character.age.toString()) }
            item { InfoText("Height", character.height) }
            item { InfoText("Hair", character.hair) }
            item { InfoText("Eyes", character.eyes) }

            item { SectionTitle("Attributes") }
            item { InfoText("Weapon Skill", character.weaponSkill.joinToString("/")) }
            item { InfoText("Ballistic Skill", character.ballisticSkill.joinToString("/")) }
            item { InfoText("Strength", character.strength.joinToString("/")) }
            item { InfoText("Toughness", character.toughness.joinToString("/")) }
            item { InfoText("Initiative", character.initiative.joinToString("/")) }
            item { InfoText("Agility", character.agility.joinToString("/")) }
            item { InfoText("Dexterity", character.dexterity.joinToString("/")) }
            item { InfoText("Intelligence", character.intelligence.joinToString("/")) }
            item { InfoText("Willpower", character.willPower.joinToString("/")) }
            item { InfoText("Fellowship", character.fellowship.joinToString("/")) }

            item { SectionTitle("Points") }
            item { InfoText("Fate", character.fate.toString()) }
            item { InfoText("Fortune", character.fortune.toString()) }
            item { InfoText("Resilience", character.resilience.toString()) }
            item { InfoText("Resolve", character.resolve.toString()) }

            // Basic Skills
            item { SectionTitle("Skills - Basic") }

            val groupedBasic = character.basicSkills.groupBy { it[0] }

            groupedBasic.forEach { (name, entries) ->
                val attribute = entries.firstOrNull()?.get(1).orEmpty()
                val base = getAttributeValue(character, attribute)
                val bonus = entries.sumOf { it[2].toIntOrNull() ?: 0 }
                val total = base + bonus

                item {
                    InfoText(name, "$bonus / $base / $total")
                }
            }

            // Advanced Skills
            item { SectionTitle("Skills - Advanced") }

            val groupedAdvanced = character.advancedSkills.groupBy { it[0] }

            groupedAdvanced.forEach { (name, entries) ->
                val attribute = entries.firstOrNull()?.get(1).orEmpty()
                val base = getAttributeValue(character, attribute)
                val bonus = entries.sumOf { it[2].toIntOrNull() ?: 0 }
                val total = base + bonus

                item {
                    InfoText(name, "$bonus / $base / $total")
                }
            }

            item { SectionTitle("Talents") }

            val groupedTalents = character.talents.groupBy { it[0] }

            groupedTalents.forEach { (name, entries) ->
                val count = entries.size
                item {
                    InfoText(name, "$count")
                }
            }

            item { SectionTitle("Trappings") }
            items(character.trappings.size) { index ->
                InfoText("-", character.trappings[index])
            }

            item { SectionTitle("Wealth") }
            item { InfoText("Brass", character.wealth.getOrNull(0)?.toString() ?: "0") }
            item { InfoText("Silver", character.wealth.getOrNull(1)?.toString() ?: "0") }
            item { InfoText("Gold", character.wealth.getOrNull(2)?.toString() ?: "0") }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                CharacterCreatorButton(
                    text = "Save Character",
                    isLoading = isSaving,
                    onClick = { onSaveClick() },
                    enabled = !isSaving
                )
            }
        }
    }
}
