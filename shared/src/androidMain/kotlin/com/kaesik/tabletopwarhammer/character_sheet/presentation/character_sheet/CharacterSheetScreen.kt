package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "Imię: ${character.name}",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    "Gatunek: ${character.species}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    "Klasa: ${character.cLass}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    "Kariera: ${character.career}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    "Poziom kariery: ${character.careerLevel}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    "Ścieżka kariery: ${character.careerPath}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    "Status: ${character.status}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    "Wiek: ${character.age}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    "Wzrost: ${character.height}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    "Włosy: ${character.hair}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    "Oczy: ${character.eyes}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Atrybuty", style = MaterialTheme.typography.titleMedium)
            }

            item { Text("WW: ${character.weaponSkill.joinToString("/")}") }
            item { Text("US: ${character.ballisticSkill.joinToString("/")}") }
            item { Text("S: ${character.strength.joinToString("/")}") }
            item { Text("Wt: ${character.toughness.joinToString("/")}") }
            item { Text("I: ${character.initiative.joinToString("/")}") }
            item { Text("Zw: ${character.agility.joinToString("/")}") }
            item { Text("Zr: ${character.dexterity.joinToString("/")}") }
            item { Text("Int: ${character.intelligence.joinToString("/")}") }
            item { Text("SW: ${character.willPower.joinToString("/")}") }
            item { Text("Ogd: ${character.fellowship.joinToString("/")}") }
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
