package com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details.components.generateElfForename
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details.components.generateRandomName
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CharacterDetailsViewModel(
    private val characterCreatorClient: CharacterCreatorClient
) : ViewModel() {
    private val _state = MutableStateFlow(CharacterDetailsState())
    val state = _state.asStateFlow()

    private var detailsJob: Job? = null

    fun onEvent(event: CharacterDetailsEvent) {
        when (event) {
            is CharacterDetailsEvent.OnNextClick -> Unit

            is CharacterDetailsEvent.OnNameChanged ->
                _state.update { it.copy(name = event.value) }

            is CharacterDetailsEvent.OnForenameChanged ->
                _state.update { it.copy(forename = event.value) }

            is CharacterDetailsEvent.OnSurnameChanged ->
                _state.update { it.copy(surname = event.value) }

            is CharacterDetailsEvent.OnClanChanged ->
                _state.update { it.copy(clan = event.value) }

            is CharacterDetailsEvent.OnEpithetChanged ->
                _state.update { it.copy(epithet = event.value) }

            is CharacterDetailsEvent.OnAgeChanged ->
                _state.update { it.copy(age = event.value) }

            is CharacterDetailsEvent.OnHeightChanged ->
                _state.update { it.copy(height = event.value) }

            is CharacterDetailsEvent.OnHairColorChanged ->
                _state.update { it.copy(hairColor = event.value) }

            is CharacterDetailsEvent.OnEyeColorChanged ->
                _state.update { it.copy(eyeColor = event.value) }

            is CharacterDetailsEvent.RollName -> {
                val name = generateRandomName(event.species).name
                _state.update { it.copy(name = name) }
            }

            is CharacterDetailsEvent.RollForename -> {
                val forename = when (event.species.uppercase()) {
                    "HIGH ELF", "WOOD ELF" -> generateElfForename(event.species)
                    "DWARF" -> generateRandomName(event.species).forename
                    else -> ""
                }
                _state.update { it.copy(forename = forename) }
            }

            is CharacterDetailsEvent.RollSurname -> {
                val surname = generateRandomName(event.species).surname
                _state.update { it.copy(surname = surname) }
            }

            is CharacterDetailsEvent.RollClan -> {
                val clan = generateRandomName(event.species).clan
                _state.update { it.copy(clan = clan) }
            }

            is CharacterDetailsEvent.RollEpithet -> {
                val epithet = generateRandomName(event.species).epithet
                _state.update { it.copy(epithet = epithet) }
            }

            is CharacterDetailsEvent.GenerateRandomName -> {
                val random = generateRandomName(event.species.uppercase())

                _state.update {
                    it.copy(
                        name = random.name,
                        forename = random.forename,
                        surname = random.surname,
                        clan = random.clan,
                        epithet = random.epithet
                    )
                }
            }

            else -> Unit
        }
    }
}
