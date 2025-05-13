package com.kaesik.tabletopwarhammer.character_creator.presentation.character_10final

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterItem
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.AndroidCharacterCreatorViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.CharacterCreatorEvent
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorButton
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.SnackbarType
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.showCharacterCreatorSnackbar
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin

@Composable
fun CharacterFinalScreenRoot(
    viewModel: AndroidCharacterFinalViewModel = koinViewModel(),
    creatorViewModel: AndroidCharacterCreatorViewModel = getKoin().get(),
    onSaveClick: () -> Unit,

    ) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val creatorState by creatorViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val character = creatorViewModel.state.value.character

    LaunchedEffect(creatorState.message, creatorState.isError) {
        creatorState.message?.let { message ->
            snackbarHostState.showCharacterCreatorSnackbar(
                message = message,
                type = if (creatorState.isError == true) SnackbarType.Error else SnackbarType.Success
            )
            creatorViewModel.onEvent(CharacterCreatorEvent.ClearMessage)
        }
    }

    CharacterFinalScreen(
        character = character,
        onEvent = { event ->
            when (event) {
                is CharacterFinalEvent.OnSaveClick -> {
                    onSaveClick()
                }

                else -> Unit
            }
        }
    )
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun InfoText(label: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun CharacterFinalScreen(
    character: CharacterItem,
    onEvent: (CharacterFinalEvent) -> Unit,
) {
    Scaffold { padding ->
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

            item { SectionTitle("Skills - Basic") }
            items(character.basicSkills.size) { index ->
                val skill = character.basicSkills[index]
                InfoText(skill[0], "+${skill[2]} (${skill[1]}: ${skill[3]})")
            }

            item { SectionTitle("Skills - Advanced") }
            items(character.advancedSkills.size) { index ->
                val skill = character.advancedSkills[index]
                InfoText(skill[0], "+${skill[2]} (${skill[1]}: ${skill[3]})")
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
                    onClick = { onEvent(CharacterFinalEvent.OnSaveClick) }
                )
            }
        }
    }
}
