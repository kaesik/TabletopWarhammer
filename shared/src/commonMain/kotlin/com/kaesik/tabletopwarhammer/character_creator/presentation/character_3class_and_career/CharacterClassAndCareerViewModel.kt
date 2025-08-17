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

            // Select a class and career from the class list
            is CharacterClassAndCareerEvent.OnClassSelect -> onClassSelect(event)
            is CharacterClassAndCareerEvent.OnCareerSelect -> onCareerSelect(event)

            // Set the selected class and career
            is CharacterClassAndCareerEvent.SetSelectedClass -> _state.update {
                it.copy(
                    selectedClass = event.classItem
                )
            }

            is CharacterClassAndCareerEvent.SetSelectedCareer -> _state.update {
                it.copy(
                    selectedCareer = event.careerItem
                )
            }

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
            is CharacterClassAndCareerEvent.OnClassAndCareerRoll -> onClassAndCareerRoll(event)


            // Consume the pending class and career selections and update the state
            CharacterClassAndCareerEvent.OnClassSelectionConsumed -> _state.update {
                it.copy(pendingClassSelection = null)
            }

            CharacterClassAndCareerEvent.OnCareerSelectionConsumed -> _state.update {
                it.copy(pendingCareerSelection = null)
            }

            CharacterClassAndCareerEvent.OnRandomSelectionConsumed -> _state.update {
                it.copy(pendingRandomSelection = null)
            }


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

    private fun onClassSelect(event: CharacterClassAndCareerEvent.OnClassSelect) {
        val selected = state.value.classList.find { it.id == event.id } ?: return
        if (selected.id == event.currentClassId) return

        val afterRoll = state.value.hasRolledClassAndCareer
        val exp = if (afterRoll) -35 else 0
        val msg = if (afterRoll) "Changed class to: ${selected.name} (-35 XP)"
        else "Selected class: ${selected.name}"

        classJob?.cancel()
        classJob = viewModelScope.launch {
            _state.update {
                it.copy(
                    selectedClass = selected,
                    selectedCareer = null,
                    pendingClassSelection = ClassCareerSelection(
                        classItem = selected,
                        careerItem = null,
                        careerPathItem = null,
                        exp = exp,
                        message = msg
                    ),
                    pendingCareerSelection = null,
                    careerList = emptyList(),
                    canSelectCareer = true,
                )
            }
        }
    }

    private fun onCareerSelect(event: CharacterClassAndCareerEvent.OnCareerSelect) {
        val selected = state.value.careerList.find { it.id == event.id } ?: return
        if (selected.id == event.currentCareerId) return

        careerJob?.cancel()
        careerJob = viewModelScope.launch {
            val careerPathItem = fetchCareerPath(selected)
            val currentClass = state.value.selectedClass ?: return@launch
            _state.update {
                it.copy(
                    selectedCareer = selected,
                    pendingCareerSelection = ClassCareerSelection(
                        classItem = currentClass,
                        careerItem = selected,
                        careerPathItem = careerPathItem,
                        exp = 0,
                        message = "Selected career: ${selected.name}"
                    )
                )
            }
        }
    }

    private fun onClassAndCareerRoll(event: CharacterClassAndCareerEvent.OnClassAndCareerRoll) {
        val classes = _state.value.classList
        if (classes.isEmpty()) return

        viewModelScope.launch {
            // PLACEHOLDER: Replace with actual condition to randomly select a class
            val randomClass = classes.random()

            _state.update {
                it.copy(
                    selectedClass = randomClass,
                    hasRolledClassAndCareer = true,
                    isLoading = true,
                    message = null,
                    selectedCareer = null,
                    canSelectClass = false,
                    canSelectCareer = false
                )
            }

            val careers = fetchCareerList(event.speciesName, randomClass.name)
            _state.update { it.copy(careerList = careers, isLoading = false) }
            if (careers.isEmpty()) return@launch

            // PLACEHOLDER: Replace with actual condition to randomly select a career
            val randomCareer = careers.random()
            val careerPathItem = fetchCareerPath(randomCareer)

            val exp = 35
            val msg =
                "Randomly selected class & career: ${randomClass.name} / ${randomCareer.name} (+$exp XP)"

            _state.update {
                it.copy(
                    selectedCareer = randomCareer,
                    pendingRandomSelection = ClassCareerSelection(
                        classItem = randomClass,
                        careerItem = randomCareer,
                        careerPathItem = careerPathItem,
                        exp = exp,
                        message = msg
                    )
                )
            }
        }
    }

    private fun <T> List<T>.firstDifferentOrSelf(predicate: (T) -> Boolean): T =
        (this.firstOrNull(predicate) ?: this.first())

}
