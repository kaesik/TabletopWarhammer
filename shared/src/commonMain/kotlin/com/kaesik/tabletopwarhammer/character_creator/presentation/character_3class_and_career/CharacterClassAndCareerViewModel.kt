package com.kaesik.tabletopwarhammer.character_creator.presentation.character_3class_and_career

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.core.domain.library.LibraryDataSource
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerPathItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ClassItem
import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.core.domain.util.DataException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterClassAndCareerViewModel(
    private val characterCreatorClient: CharacterCreatorClient,
    private val libraryDataSource: LibraryDataSource,
) : ViewModel() {
    private val _state = MutableStateFlow(CharacterClassAndCareerState())
    val state = _state.asStateFlow()

    private var classJob: Job? = null
    private var careerJob: Job? = null

    fun onEvent(event: CharacterClassAndCareerEvent) {
        when (event) {
            // Initialize the class and career list when the view model is created
            is CharacterClassAndCareerEvent.InitClassList -> viewModelScope.launch {
                _state.update { it.copy(isLoading = true, error = null) }

                val classList = fetchClassList()
                _state.update { it.copy(classList = classList, isLoading = false) }
            }

            is CharacterClassAndCareerEvent.InitCareerList -> viewModelScope.launch {
                _state.update { it.copy(isLoading = true, error = null) }

                val careerList = fetchCareerList(event.speciesName, event.className)
                _state.update { it.copy(careerList = careerList, isLoading = false) }
            }

            // Set the selected class and career
            is CharacterClassAndCareerEvent.SetSelectedClass -> _state.update {
                it.copy(selectedClass = event.classItem)
            }

            is CharacterClassAndCareerEvent.SetSelectedCareer -> _state.update {
                it.copy(selectedCareer = event.careerItem)
            }

            is CharacterClassAndCareerEvent.SetSelectedPath -> _state.update {
                it.copy(selectedPath = event.pathItem)
            }

            // Select a class and career from the class list
            is CharacterClassAndCareerEvent.OnClassSelect -> onClassSelect(event.id)
            is CharacterClassAndCareerEvent.OnCareerSelect -> onCareerSelect(event.id)

            // Set the state to indicate whether class or career selection is allowed
            is CharacterClassAndCareerEvent.SetSelectingClass -> _state.update {
                it.copy(
                    canSelectClass = event.canSelectClass
                )
            }

            is CharacterClassAndCareerEvent.SetSelectingCareer -> _state.update {
                it.copy(
                    canSelectCareer = event.canSelectCareer
                )
            }

            // Roll a random class and career from the list and update the state with the rolled class and career
            is CharacterClassAndCareerEvent.OnClassAndCareerRoll -> onClassAndCareerRoll(event.speciesName)
            
            else -> Unit
        }
    }

    private suspend fun fetchClassList(): List<ClassItem> {
        return try {
            // PLACEHOLDER: Replace with actual condition to determine data source
            if (true) libraryDataSource.getAllClasses()
            else characterCreatorClient.getAllClasses()

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

    private suspend fun fetchCareerList(speciesName: String, className: String): List<CareerItem> {
        return try {
            // PLACEHOLDER: Replace with actual condition to determine data source
            if (true) libraryDataSource
                .getFilteredCareers(speciesName = speciesName, className = className)
            else characterCreatorClient
                .getFilteredCareers(speciesName = speciesName, className = className)

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

    private suspend fun fetchCareerPath(career: CareerItem): CareerPathItem? {
        val firstCareerPathName = career.careerPath?.substringBefore(',')?.trim().orEmpty()
        if (firstCareerPathName.isEmpty()) return null

        return try {
            // PLACEHOLDER: Replace with actual condition to determine data source
            if (true) libraryDataSource.getCareerPath(firstCareerPathName)
            else characterCreatorClient.getCareerPath(firstCareerPathName)
        } catch (e: Exception) {
            null
        }
    }

    private fun onClassSelect(id: String) {
        val selected = state.value.classList.find { it.id == id } ?: return
        if (selected.id == state.value.selectedClass?.id) return

        classJob?.cancel()
        classJob = viewModelScope.launch {
            _state.update {
                it.copy(
                    selectedClass = selected,
                    selectedCareer = null,
                    selectedPath = null,
                    careerList = emptyList(),
                    canSelectCareer = true
                )
            }
        }
    }

    private fun onCareerSelect(id: String) {
        val selected = state.value.careerList.find { it.id == id } ?: return
        if (selected.id == state.value.selectedCareer?.id) return

        careerJob?.cancel()
        careerJob = viewModelScope.launch {
            val careerPathItem = fetchCareerPath(selected)
            _state.update {
                it.copy(
                    selectedCareer = selected,
                    selectedPath = careerPathItem,
                )
            }
        }
    }

    private fun onClassAndCareerRoll(speciesName: String) {
        val classes = _state.value.classList
        if (classes.isEmpty()) return

        viewModelScope.launch {
            // PLACEHOLDER: Replace with actual condition to randomly select a class
            val randomClass = classes.random()

            _state.update {
                it.copy(
                    selectedClass = randomClass,
                    selectedCareer = null,
                    selectedPath = null,
                    careerList = emptyList(),
                    canSelectClass = false,
                    canSelectCareer = false,
                    isLoading = true,
                    message = null
                )
            }

            val careers = fetchCareerList(speciesName, randomClass.name)
            _state.update { it.copy(careerList = careers, isLoading = false) }
            if (careers.isEmpty()) return@launch

            // PLACEHOLDER: Replace with actual condition to randomly select a career
            val randomCareer = careers.random()
            val careerPathItem = fetchCareerPath(randomCareer)
            _state.update {
                it.copy(
                    selectedCareer = randomCareer,
                    selectedPath = careerPathItem,
                )
            }
        }
    }
}
