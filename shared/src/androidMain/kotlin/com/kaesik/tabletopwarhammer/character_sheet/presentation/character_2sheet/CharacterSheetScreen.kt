package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.kaesik.tabletopwarhammer.core.domain.character.getAttributeValue
import com.kaesik.tabletopwarhammer.core.presentation.MainScaffold
import com.kaesik.tabletopwarhammer.core.presentation.components.InfoText
import com.kaesik.tabletopwarhammer.core.presentation.components.SectionTitle
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterSheetScreenRoot(
    viewModel: AndroidCharacterSheetViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    characterId: Int
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(CharacterSheetEvent.LoadCharacterById(characterId))
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        state.character?.let {
            CharacterSheetScreen(
                state = state,
                character = it,
                onEvent = { event ->
                    when (event) {
                        is CharacterSheetEvent.OnBackClick -> onBackClick()
                        else -> Unit
                    }

                    viewModel.onEvent(event)
                }
            )
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Nie znaleziono postaci", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun CharacterSheetScreen(
    state: CharacterSheetState,
    character: CharacterItem,
    onEvent: (CharacterSheetEvent) -> Unit
) {
    MainScaffold(
        onBackClick = { onEvent(CharacterSheetEvent.OnBackClick) },
        title = character.name,
        isLoading = state.isLoading,
        isError = state.isError,
        error = state.error,
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // General Info
                item { SectionTitle("General Information") }
                item { InfoText("Name", character.name) }
                item { InfoText("Species", character.species) }
                item { InfoText("Class", character.cLass) }
                item { InfoText("Career", character.career) }
                item { InfoText("Career Level", character.careerLevel) }
                item { InfoText("Career Path", character.careerPath) }
                item { InfoText("Status", character.status) }
                item { InfoText("Age", character.age) }
                item { InfoText("Height", character.height) }
                item { InfoText("Hair", character.hair) }
                item { InfoText("Eyes", character.eyes) }

                // Attributes
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

                // Fate and Resolve
                item { SectionTitle("Points and Motivation") }
                item { InfoText("Fate", character.fate.toString()) }
                item { InfoText("Fortune", character.fortune.toString()) }
                item { InfoText("Resilience", character.resilience.toString()) }
                item { InfoText("Resolve", character.resolve.toString()) }
                item { InfoText("Motivation", character.motivation) }

                // Experience
                item { SectionTitle("Experience") }
                item {
                    InfoText(
                        "Current / Spent / Total",
                        character.experience.joinToString(" / ")
                    )
                }

                // Movement
                item { SectionTitle("Movement") }
                item { InfoText("Base", character.movement.toString()) }
                item { InfoText("Walk", character.walk.toString()) }
                item { InfoText("Run", character.run.toString()) }

                // Skills
                item { SectionTitle("Basic Skills") }
                character.basicSkills.groupBy { it[0] }.forEach { (name, entries) ->
                    val bonus = entries.sumOf { it[2].toIntOrNull() ?: 0 }
                    val attr = entries.firstOrNull()?.get(1) ?: ""
                    val base = getAttributeValue(character, attr)
                    item { InfoText("$name ($attr)", "$bonus / $base / ${bonus + base}") }
                }

                item { SectionTitle("Advanced Skills") }
                character.advancedSkills.groupBy { it[0] }.forEach { (name, entries) ->
                    val bonus = entries.sumOf { it[2].toIntOrNull() ?: 0 }
                    val attr = entries.firstOrNull()?.get(1) ?: ""
                    val base = getAttributeValue(character, attr)
                    item { InfoText("$name ($attr)", "$bonus / $base / ${bonus + base}") }
                }

                // Talents
                item { SectionTitle("Talents") }
                character.talents.groupBy { it[0] }.forEach { (name, entries) ->
                    val count = entries.size
                    item { InfoText(name, "$count") }
                }

                // Trappings
                if (character.trappings.isNotEmpty()) {
                    item { SectionTitle("Trappings") }
                    character.trappings.forEach { trap ->
                        item { InfoText("-", trap) }
                    }
                }

                // Wealth
                item { SectionTitle("Wealth") }
                item { InfoText("Brass", character.wealth.getOrNull(0)?.toString() ?: "0") }
                item { InfoText("Silver", character.wealth.getOrNull(1)?.toString() ?: "0") }
                item { InfoText("Gold", character.wealth.getOrNull(2)?.toString() ?: "0") }

                // Party Info
                if (character.partyName.isNotBlank()) {
                    item { SectionTitle("Party") }
                    item { InfoText("Name", character.partyName) }
                    item { InfoText("Short-Term Ambition", character.partyAmbitionShortTerm) }
                    item { InfoText("Long-Term Ambition", character.partyAmbitionLongTerm) }
                    character.partyMembers.forEachIndexed { index, member ->
                        item { InfoText("Member ${index + 1}", member) }
                    }
                }

                // Psychology & Mutations
                if (character.psychology.isNotEmpty()) {
                    item { SectionTitle("Psychology") }
                    character.psychology.forEach { item { InfoText("-", it) } }
                }
                if (character.mutations.isNotEmpty()) {
                    item { SectionTitle("Mutations") }
                    character.mutations.forEach { item { InfoText("-", it) } }
                }

                // Spells
                if (character.spells.isNotEmpty()) {
                    item { SectionTitle("Spells") }
                    character.spells.forEach { spell ->
                        val name = spell.getOrNull(0) ?: "?"
                        val range = spell.getOrNull(2) ?: ""
                        val effect = spell.getOrNull(4) ?: ""
                        item { InfoText(name, "Range: $range | Effect: $effect") }
                    }
                }

                // Prayers
                if (character.prayers.isNotEmpty()) {
                    item { SectionTitle("Prayers") }
                    character.prayers.forEach { prayer ->
                        val name = prayer.getOrNull(0) ?: "?"
                        val range = prayer.getOrNull(2) ?: ""
                        val effect = prayer.getOrNull(4) ?: ""
                        item { InfoText(name, "Range: $range | Effect: $effect") }
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
            sin = 0
        )
    )
}
