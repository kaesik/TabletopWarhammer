package com.kaesik.tabletopwarhammer.character_creator.presentation.character_3class_and_career

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterClassAndCareerViewModel(
    private val characterCreatorClient: CharacterCreatorClient
) : ViewModel() {
    private val _state = MutableStateFlow(CharacterClassAndCareerState())
    val state = _state.asStateFlow()

    private var classJob: Job? = null
    private var careerJob: Job? = null

    fun onEvent(event: CharacterClassAndCareerEvent) {
        when (event) {
            is CharacterClassAndCareerEvent.InitCareerList -> {
                loadCareersList(
                    speciesName = event.speciesName,
                    className = ""
                )
            }

            is CharacterClassAndCareerEvent.InitClassList -> {
                loadClassesList()
            }

            is CharacterClassAndCareerEvent.OnClassSelect -> {
                val selected = state.value.classList.find { it.id == event.id }
                _state.value = state.value.copy(
                    selectedClass = selected,
                    selectedCareer = null
                )

                selected?.let {
                    loadCareersList(speciesName = "Human", className = it.name)
                }
            }

            is CharacterClassAndCareerEvent.OnCareerSelect -> {
                val selected = state.value.careerList.find { it.id == event.id }
                _state.value = state.value.copy(selectedCareer = selected)
            }

            else -> Unit
        }
    }

    private fun loadClassesList() {
        classJob?.cancel()
        classJob = viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            try {
                val classList = characterCreatorClient.getClasses()
                _state.value = state.value.copy(
                    classList = classList,
                    error = null,
                    isLoading = false
                )
            } catch (e: CancellationException) {
                println("Classes fetch cancelled")
                _state.value = state.value.copy(isLoading = false)
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = state.value.copy(
                    error = "Nie udało się załadować klas: ${e.message}",
                    isLoading = false
                )
            }
        }
    }


    private fun loadCareersList(
        speciesName: String,
        className: String,
    ) {
        careerJob?.cancel()
        careerJob = viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            try {
                val careerList = characterCreatorClient.getCareers(
                    speciesName = speciesName,
                    className = className,
                )
                _state.value = state.value.copy(
                    careerList = careerList,
                    error = null,
                    isLoading = false
                )
            } catch (e: CancellationException) {
                println("Careers fetch cancelled")
                _state.value = state.value.copy(isLoading = false)
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = state.value.copy(
                    error = "Nie udało się załadować karier: ${e.message}",
                    isLoading = false
                )
            }
        }
    }
}
