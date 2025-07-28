package com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.core.domain.util.DataException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterSpeciesViewModel(
    private val characterCreatorClient: CharacterCreatorClient
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterSpeciesState())
    val state = _state.asStateFlow()

    private var speciesJob: Job? = null

    fun onEvent(event: CharacterSpeciesEvent) {
        when (event) {
            // Initialize the species list when the view model is created
            is CharacterSpeciesEvent.InitSpeciesList -> {
                loadSpeciesList()
            }

            // When the species is selected, update the state with the selected species
            is CharacterSpeciesEvent.OnSpeciesSelect -> {
                val selected = _state.value.speciesList.find { it.id == event.id }
                _state.value = _state.value.copy(
                    selectedSpecies = selected
                )
            }

            // Set the selected species directly from the event
            is CharacterSpeciesEvent.SetSelectedSpecies -> {
                _state.update { it.copy(selectedSpecies = event.speciesItem) }
            }

            // Roll a random species from the list and update the state with the rolled species
            is CharacterSpeciesEvent.RollRandomSpecies -> {
                val list = _state.value.speciesList
                if (list.isNotEmpty()) {
                    val random = list.random()
                    _state.value = _state.value.copy(
                        selectedSpecies = random,
                        hasRolledSpecies = true,
                    )
                }
            }

            else -> Unit
        }
    }

    private fun loadSpeciesList() {
        speciesJob?.cancel()
        speciesJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val speciesList = characterCreatorClient.getSpecies()

                    _state.update {
                        it.copy(
                        speciesList = speciesList,
                        isLoading = false
                    )
                }

            } catch (e: CancellationException) {
                _state.update { it.copy(isLoading = false) }

            } catch (e: DataException) {
                _state.update {
                    it.copy(
                        error = e.error,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _state.update {
                    it.copy(
                        error = DataError.Local.UNKNOWN,
                        isLoading = false
                    )
                }
            }
        }
    }
}
