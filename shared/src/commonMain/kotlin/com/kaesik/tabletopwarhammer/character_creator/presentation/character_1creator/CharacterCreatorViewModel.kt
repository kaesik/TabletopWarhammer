package com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CharacterCreatorViewModel : ViewModel() {
    private val _state = MutableStateFlow(CharacterCreatorState())
    val state = _state.asStateFlow()

    fun onEvent(event: CharacterCreatorEvent) {
        when (event) {
            is CharacterCreatorEvent.SetSpecies -> {
                _state.update { current ->
                    val updatedCharacter = current.character.copy(
                        species = event.speciesItem.name
                    )
                    val updated = current.copy(
                        selectedSpecies = event.speciesItem,
                        character = updatedCharacter
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
                        character = updatedCharacter
                    )
                    println("Updated CharacterItem: $updatedCharacter")
                    updated
                }
            }

            is CharacterCreatorEvent.SetCareer -> {
                _state.update { current ->
                    val updatedCharacter = current.character.copy(
                        career = event.careerItem.name,
                        careerPath = event.careerPathItem.name,
                        careerLevel = event.careerPathItem.name
                    )
                    val updated = current.copy(
                        selectedCareer = event.careerItem,
                        character = updatedCharacter
                    )
                    println("Updated CharacterItem: $updatedCharacter")
                    updated
                }
            }

            else -> Unit
        }
    }


}
