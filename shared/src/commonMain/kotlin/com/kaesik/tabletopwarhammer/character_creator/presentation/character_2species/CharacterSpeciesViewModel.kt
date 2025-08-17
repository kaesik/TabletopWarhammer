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

                val list = fetchSpeciesList()
                _state.update { it.copy(speciesList = list, isLoading = false) }
            }

            // When the species is selected, update the state with the selected species
            is CharacterSpeciesEvent.OnSpeciesSelect -> onSpeciesSelect(event)

            // Set the selected species directly from the event
            is CharacterSpeciesEvent.SetSelectedSpecies -> _state.update {
                it.copy(
                    selectedSpecies = event.speciesItem
                )
            }


            // Roll a random species from the list and update the state with the rolled species
            is CharacterSpeciesEvent.OnSpeciesRoll -> onRoll(event)

            // Set the state to indicate whether species selection is allowed
            is CharacterSpeciesEvent.SetSelectingSpecies -> {
                _state.update { it.copy(canSelectSpecies = event.canSelectSpecies) }
            }

            else -> Unit
        }
    }

    private suspend fun fetchSpeciesList(): List<SpeciesItem> {
        return try {
            if (true) libraryDataSource.getAllSpecies()
            else characterCreatorClient.getSpecies()

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

    private fun onSpeciesSelect(event: CharacterSpeciesEvent.OnSpeciesSelect) {
        val selected = state.value.speciesList.find { it.id == event.id } ?: return
        if (selected.id == event.currentSpeciesId) return

        val afterRoll = state.value.hasRolledSpecies
        val exp = if (afterRoll) -25 else 0
        val msg = if (afterRoll)
            "Changed species to: ${selected.name} (-25 XP)"
        else
            "Selected species: ${selected.name}"

        speciesJob?.cancel()
        speciesJob = viewModelScope.launch {
            _state.update {
                it.copy(
                    selectedSpecies = selected,
                    pendingSpeciesSelection = SpeciesSelection(
                        speciesItem = selected,
                        exp = exp,
                        message = msg
                    )
                )
            }
        }
    }

    private fun onRoll(event: CharacterSpeciesEvent.OnSpeciesRoll) {
        val species = _state.value.speciesList
        if (species.isEmpty()) return

        val random = species.firstDifferentOrSelf { it.id != event.currentSpeciesId }
        val exp = 35
        val msg = "Randomly selected species: ${random.name} (+$exp XP)"

        _state.update {
            it.copy(
                selectedSpecies = random,
                hasRolledSpecies = true,
                canSelectSpecies = false,
                pendingRandomSpecies = SpeciesSelection(
                    speciesItem = random,
                    exp = exp,
                    message = msg
                )
            )
        }
    }

    private fun <T> List<T>.firstDifferentOrSelf(predicate: (T) -> Boolean): T =
        this.firstOrNull(predicate) ?: this.first()

}
