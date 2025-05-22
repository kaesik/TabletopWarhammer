package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
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
import com.kaesik.tabletopwarhammer.core.domain.character.getAttributeValue
import com.kaesik.tabletopwarhammer.core.presentation.InfoText
import com.kaesik.tabletopwarhammer.core.presentation.SectionTitle
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterSheetScreenRoot(
    viewModel: AndroidCharacterSheetViewModel = koinViewModel(),
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
            CharacterSheetScreen(character = it)
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Nie znaleziono postaci", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun CharacterSheetScreen(character: CharacterItem) {
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // General Info
            item { SectionTitle("Dane ogólne") }
            item { InfoText("Imię", character.name) }
            item { InfoText("Gatunek", character.species) }
            item { InfoText("Klasa", character.cLass) }
            item { InfoText("Kariera", character.career) }
            item { InfoText("Poziom kariery", character.careerLevel) }
            item { InfoText("Ścieżka kariery", character.careerPath) }
            item { InfoText("Status", character.status) }
            item { InfoText("Wiek", character.age) }
            item { InfoText("Wzrost", character.height) }
            item { InfoText("Włosy", character.hair) }
            item { InfoText("Oczy", character.eyes) }

            // Attributes
            item { SectionTitle("Atrybuty") }
            item { InfoText("WW", character.weaponSkill.joinToString("/")) }
            item { InfoText("US", character.ballisticSkill.joinToString("/")) }
            item { InfoText("S", character.strength.joinToString("/")) }
            item { InfoText("Wt", character.toughness.joinToString("/")) }
            item { InfoText("I", character.initiative.joinToString("/")) }
            item { InfoText("Zw", character.agility.joinToString("/")) }
            item { InfoText("Zr", character.dexterity.joinToString("/")) }
            item { InfoText("Int", character.intelligence.joinToString("/")) }
            item { InfoText("SW", character.willPower.joinToString("/")) }
            item { InfoText("Ogd", character.fellowship.joinToString("/")) }

            // Fate and Resolve
            item { SectionTitle("Punkty i motywacja") }
            item { InfoText("Los", character.fate.toString()) }
            item { InfoText("Szczęście", character.fortune.toString()) }
            item { InfoText("Odporność", character.resilience.toString()) }
            item { InfoText("Zdecydowanie", character.resolve.toString()) }
            item { InfoText("Motywacja", character.motivation) }

            // Experience
            item { SectionTitle("Doświadczenie") }
            item { InfoText("Obecne / Wydane / Łącznie", character.experience.joinToString(" / ")) }

            // Movement
            item { SectionTitle("Ruch") }
            item { InfoText("Ruch podstawowy", character.movement.toString()) }
            item { InfoText("Marsz", character.walk.toString()) }
            item { InfoText("Bieg", character.run.toString()) }

            // Skills
            item { SectionTitle("Umiejętności podstawowe") }
            character.basicSkills.groupBy { it[0] }.forEach { (name, entries) ->
                val bonus = entries.sumOf { it[2].toIntOrNull() ?: 0 }
                val attr = entries.firstOrNull()?.get(1) ?: ""
                val base = getAttributeValue(character, attr)
                item { InfoText("$name ($attr)", "$bonus / $base / ${bonus + base}") }
            }

            item { SectionTitle("Umiejętności zaawansowane") }
            character.advancedSkills.groupBy { it[0] }.forEach { (name, entries) ->
                val bonus = entries.sumOf { it[2].toIntOrNull() ?: 0 }
                val attr = entries.firstOrNull()?.get(1) ?: ""
                val base = getAttributeValue(character, attr)
                item { InfoText("$name ($attr)", "$bonus / $base / ${bonus + base}") }
            }

            // Talents
            item { SectionTitle("Talenty") }
            character.talents.groupBy { it[0] }.forEach { (name, entries) ->
                val count = entries.size
                item { InfoText(name, "$count") }
            }

            // Trappings
            if (character.trappings.isNotEmpty()) {
                item { SectionTitle("Wyposażenie") }
                character.trappings.forEach { trap ->
                    item { InfoText("-", trap) }
                }
            }

            // Wealth
            item { SectionTitle("Majątek") }
            item { InfoText("Miedziaki", character.wealth.getOrNull(0)?.toString() ?: "0") }
            item { InfoText("Srebrniki", character.wealth.getOrNull(1)?.toString() ?: "0") }
            item { InfoText("Złoto", character.wealth.getOrNull(2)?.toString() ?: "0") }

            // Party Info
            if (character.partyName.isNotBlank()) {
                item { SectionTitle("Drużyna") }
                item { InfoText("Nazwa", character.partyName) }
                item { InfoText("Ambicja (krótkoterminowa)", character.partyAmbitionShortTerm) }
                item { InfoText("Ambicja (długoterminowa)", character.partyAmbitionLongTerm) }
                character.partyMembers.forEachIndexed { index, member ->
                    item { InfoText("Członek ${index + 1}", member) }
                }
            }

            // Psychology & Mutations
            if (character.psychology.isNotEmpty()) {
                item { SectionTitle("Psychika") }
                character.psychology.forEach { item { InfoText("-", it) } }
            }
            if (character.mutations.isNotEmpty()) {
                item { SectionTitle("Mutacje") }
                character.mutations.forEach { item { InfoText("-", it) } }
            }

            // Spells
            if (character.spells.isNotEmpty()) {
                item { SectionTitle("Zaklęcia") }
                character.spells.forEach { spell ->
                    val name = spell.getOrNull(0) ?: "?"
                    val range = spell.getOrNull(2) ?: ""
                    val effect = spell.getOrNull(4) ?: ""
                    item { InfoText(name, "Zasięg: $range | Efekt: $effect") }
                }
            }

            // Prayers
            if (character.prayers.isNotEmpty()) {
                item { SectionTitle("Modlitwy") }
                character.prayers.forEach { prayer ->
                    val name = prayer.getOrNull(0) ?: "?"
                    val range = prayer.getOrNull(2) ?: ""
                    val effect = prayer.getOrNull(4) ?: ""
                    item { InfoText(name, "Zasięg: $range | Efekt: $effect") }
                }
            }
        }
    }
}

@Preview
@Composable
fun CharacterSheetScreenPreview() {
    CharacterSheetScreen(
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
