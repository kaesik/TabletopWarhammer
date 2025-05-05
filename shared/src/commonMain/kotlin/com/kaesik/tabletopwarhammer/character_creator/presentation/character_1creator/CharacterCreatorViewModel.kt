package com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CharacterCreatorViewModel : ViewModel() {
    private val _state = MutableStateFlow(CharacterCreatorState())
    val state = _state.asStateFlow()

    init {
        println("CharacterCreatorViewModel initialized!")
    }

    fun onEvent(event: CharacterCreatorEvent) {
        when (event) {
            is CharacterCreatorEvent.ShowMessage -> {
                _state.update { it.copy(message = event.message) }
            }

            is CharacterCreatorEvent.ClearMessage -> {
                _state.update { it.copy(message = null) }
            }

            is CharacterCreatorEvent.SetSpecies -> {
                _state.update { current ->
                    val newSpecies = event.speciesItem.name
                    val resetClassAndCareer = current.character.species != newSpecies
                    val updatedCharacter = current.character.copy(
                        species = newSpecies,
                        cLass = if (resetClassAndCareer) "" else current.character.cLass,
                        career = if (resetClassAndCareer) "" else current.character.career,
                        careerLevel = if (resetClassAndCareer) "" else current.character.careerLevel,
                        careerPath = if (resetClassAndCareer) "" else current.character.careerPath,
                        status = if (resetClassAndCareer) "" else current.character.status
                    )
                    val updated = current.copy(
                        selectedSpecies = event.speciesItem,
                        selectedClass = if (resetClassAndCareer) null else current.selectedClass,
                        selectedCareer = if (resetClassAndCareer) null else current.selectedCareer,
                        character = updatedCharacter,
                        message = "Selected species: $newSpecies"
                    )
                    println("Updated CharacterItem: $updatedCharacter")
                    updated
                }
            }

            is CharacterCreatorEvent.SetClass -> {
                _state.update { current ->
                    val updatedCharacter = current.character.copy(
                        cLass = event.classItem.name
                    )
                    val updated = current.copy(
                        selectedClass = event.classItem,
                        character = updatedCharacter,
                        message = "Selected class: ${event.classItem.name}"
                    )
                    println("Updated CharacterItem: $updatedCharacter")
                    updated
                }
            }

            is CharacterCreatorEvent.SetCareer -> {
                _state.update { current ->
                    val updatedCharacter = current.character.copy(
                        career = event.careerItem?.name ?: "",
                        careerPath = event.careerPathItem?.name ?: "",
                        careerLevel = event.careerPathItem?.name ?: "",
                        status = event.careerPathItem?.status ?: "",
                    )
                    val updated = current.copy(
                        selectedCareer = event.careerItem,
                        character = updatedCharacter,
                        message = "Selected career: ${event.careerItem?.name ?: ""}"
                    )
                    println("Updated CharacterItem: $updatedCharacter")
                    updated
                }
            }

            is CharacterCreatorEvent.SetAttributes -> {
                _state.update { current ->
                    val character = current.character

                    val updatedCharacter = character.copy(
                        weaponSkill = listOf(
                            event.totalAttributes.getOrNull(0) ?: character.weaponSkill[0],
                            0,
                            event.totalAttributes.getOrNull(0) ?: character.weaponSkill[0]
                        ),
                        ballisticSkill = listOf(
                            event.totalAttributes.getOrNull(1) ?: character.ballisticSkill[0],
                            0,
                            event.totalAttributes.getOrNull(1) ?: character.ballisticSkill[0]
                        ),
                        strength = listOf(
                            event.totalAttributes.getOrNull(2) ?: character.strength[0],
                            0,
                            event.totalAttributes.getOrNull(2) ?: character.strength[0]
                        ),
                        toughness = listOf(
                            event.totalAttributes.getOrNull(3) ?: character.toughness[0],
                            0,
                            event.totalAttributes.getOrNull(3) ?: character.toughness[0]
                        ),
                        initiative = listOf(
                            event.totalAttributes.getOrNull(4) ?: character.initiative[0],
                            0,
                            event.totalAttributes.getOrNull(4) ?: character.initiative[0]
                        ),
                        agility = listOf(
                            event.totalAttributes.getOrNull(5) ?: character.agility[0],
                            0,
                            event.totalAttributes.getOrNull(5) ?: character.agility[0]
                        ),
                        dexterity = listOf(
                            event.totalAttributes.getOrNull(6) ?: character.dexterity[0],
                            0,
                            event.totalAttributes.getOrNull(6) ?: character.dexterity[0]
                        ),
                        intelligence = listOf(
                            event.totalAttributes.getOrNull(7) ?: character.intelligence[0],
                            0,
                            event.totalAttributes.getOrNull(7) ?: character.intelligence[0]
                        ),
                        willPower = listOf(
                            event.totalAttributes.getOrNull(8) ?: character.willPower[0],
                            0,
                            event.totalAttributes.getOrNull(8) ?: character.willPower[0]
                        ),
                        fellowship = listOf(
                            event.totalAttributes.getOrNull(9) ?: character.fellowship[0],
                            0,
                            event.totalAttributes.getOrNull(9) ?: character.fellowship[0]
                        ),
                        fate = event.fatePoints,
                        fortune = event.fatePoints,
                        resilience = event.resiliencePoints,
                        resolve = 0
                    )
                    val updated = current.copy(
                        character = updatedCharacter,
                        message = "Attributes and fate points set!"
                    )
                    println("Updated CharacterItem: $updatedCharacter")
                    updated
                }
            }

            is CharacterCreatorEvent.SetSkillsAndTalents -> {
                _state.update { current ->
                    val updatedCharacter = current.character.copy(
                        basicSkills = event.basicSkills,
                        advancedSkills = event.advancedSkills,
                        talents = event.talents,
                    )
                    val updated = current.copy(
                        character = updatedCharacter,
                        message = "Skills and talents selected!"
                    )
                    println("Updated CharacterItem: $updatedCharacter")
                    updated
                }
            }

            is CharacterCreatorEvent.SetTrappings -> {
                _state.update { current ->
                    val updatedCharacter = current.character.copy(
                        trappings = event.trappings
                    )
                    val updated = current.copy(
                        character = updatedCharacter,
                        message = "Trappings selected!"
                    )
                    println("Updated CharacterItem: $updatedCharacter")
                    updated
                }
            }

            is CharacterCreatorEvent.SetWealth -> {
                _state.update { current ->
                    val updatedCharacter = current.character.copy(
                        wealth = event.wealth
                    )
                    val updated = current.copy(
                        character = updatedCharacter,
                        message = "Wealth selected!"
                    )
                    println("Updated CharacterItem: $updatedCharacter")
                    updated
                }
            }

            is CharacterCreatorEvent.SetCharacterDetails -> {
                _state.update { current ->
                    val updatedCharacter = current.character.copy(
                        name = event.name,
                        age = event.age,
                        height = event.height,
                        hair = event.hairColor,
                        eyes = event.eyeColor,
                    )
                    val updated = current.copy(
                        character = updatedCharacter,
                        message = "Character details set!"
                    )
                    println("Updated CharacterItem: $updatedCharacter")
                    updated
                }
            }

            else -> Unit
        }
    }
}
