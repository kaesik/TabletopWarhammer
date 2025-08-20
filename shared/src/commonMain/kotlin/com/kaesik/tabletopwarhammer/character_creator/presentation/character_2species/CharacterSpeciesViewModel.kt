package com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.core.domain.library.LibraryDataSource
import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem
import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.core.domain.util.DataException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterSpeciesViewModel(
    private val characterCreatorClient: CharacterCreatorClient,
    private val libraryDataSource: LibraryDataSource,
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterSpeciesState())
    val state = _state.asStateFlow()

    private var speciesJob: Job? = null

    fun onEvent(event: CharacterSpeciesEvent) {
        when (event) {
            // Initialize the species list when the view model is created
            is CharacterSpeciesEvent.InitSpeciesList -> viewModelScope.launch {
                _state.update { it.copy(isLoading = true, error = null) }

                val speciesList = fetchSpeciesList()
                _state.update { it.copy(speciesList = speciesList, isLoading = false) }
            }

            // Set the selected species directly from the event
            is CharacterSpeciesEvent.SetSelectedSpecies -> _state.update {
                it.copy(
                    selectedSpecies = event.speciesItem
                )
            }

            // Set the state to indicate whether species selection is allowed
            is CharacterSpeciesEvent.SetSelectingSpecies -> _state.update {
                it.copy(canSelectSpecies = event.canSelectSpecies)
            }
            
            // When the species is selected, update the state with the selected species
            is CharacterSpeciesEvent.OnSpeciesSelect -> onSpeciesSelect(event.id)

            // Roll a random species from the list and update the state with the rolled species
            is CharacterSpeciesEvent.OnSpeciesRoll -> onSpeciesRoll()

            else -> Unit
        }
    }

    private suspend fun fetchSpeciesList(): List<SpeciesItem> {
        return try {
            // PLACEHOLDER: Replace with actual condition to determine data source
            if (true) libraryDataSource.getAllSpecies()
            else characterCreatorClient.getAllSpecies()

        } catch (e: DataException) {
            _state.update { it.copy(error = e.error) }
            emptyList()
        } catch (_: CancellationException) {
            emptyList()
        } catch (_: Exception) {
            _state.update { it.copy(error = DataError.Local.UNKNOWN) }
            emptyList()
        }
    }

    private fun onSpeciesSelect(id: String) {
        val selected = state.value.speciesList.find { it.id == id } ?: return
        if (selected.id == state.value.selectedSpecies?.id) return

        speciesJob?.cancel()
        speciesJob = viewModelScope.launch {
            _state.update { it.copy(selectedSpecies = selected) }
        }
    }

    private fun onSpeciesRoll() {
        val species = state.value.speciesList
        if (species.isEmpty()) return

        // PLACEHOLDER: Replace with actual condition to randomly select a species
        val random = species.random()

        _state.update {
            it.copy(
                selectedSpecies = random,
                canSelectSpecies = false
            )
        }
    }
}
