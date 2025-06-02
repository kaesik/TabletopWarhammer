package com.kaesik.tabletopwarhammer.character_creator.presentation.character_3class_and_career

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
                    className = event.className
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
            }

            is CharacterClassAndCareerEvent.OnCareerSelect -> {
                val selected = state.value.careerList.find { it.id == event.id }
                _state.value = state.value.copy(selectedCareer = selected)
            }

            is CharacterClassAndCareerEvent.SetSelectedClass -> {
                _state.update { it.copy(selectedClass = event.classItem) }
            }

            is CharacterClassAndCareerEvent.SetSelectedCareer -> {
                _state.update { it.copy(selectedCareer = event.careerItem) }
            }

            is CharacterClassAndCareerEvent.SetHasRolledClassAndCareer -> {
                _state.update { it.copy(hasRolledClassAndCareer = true) }
            }

            else -> Unit
        }
    }

    private fun loadClassesList() {
        classJob?.cancel()
        classJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val classList = characterCreatorClient.getClasses()

                _state.update {
                    it.copy(
                        classList = classList,
                        isLoading = false
                    )
                }

            } catch (e: CancellationException) {
                println("Classes fetch cancelled normally")
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

    private fun loadCareersList(
        speciesName: String,
        className: String,
    ) {
        careerJob?.cancel()
        careerJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val careerList = characterCreatorClient.getCareers(
                    speciesName = speciesName,
                    className = className,
                )

                _state.update {
                    it.copy(
                        careerList = careerList,
                        isLoading = false
                    )
                }

            } catch (e: CancellationException) {
                println("Careers fetch cancelled normally")
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
