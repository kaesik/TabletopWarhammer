package com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.core.domain.library.LibraryDataSource
import com.kaesik.tabletopwarhammer.core.domain.library.items.ItemItem
import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.core.domain.util.DataException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterTrappingsViewModel(
    private val characterCreatorClient: CharacterCreatorClient,
    private val libraryDataSource: LibraryDataSource,
) : ViewModel() {
    private val _state = MutableStateFlow(CharacterTrappingsState())
    val state = _state.asStateFlow()

    private var trappingsJob: Job? = null
    private var wealthJob: Job? = null

    fun onEvent(event: CharacterTrappingsEvent) {
        when (event) {
            // Initialize the trappings list when the view model is created
            is CharacterTrappingsEvent.InitTrappingsList -> loadTrappingsList(
                event.className, event.careerPathName
            )

            // Initialize the wealth when the view model is created
            is CharacterTrappingsEvent.InitWealth -> loadWealthList(event.careerPathName)

            // Set the cached wealth directly
            is CharacterTrappingsEvent.SetWealthCached -> {
                _state.update { it.copy(wealth = event.wealth) }
            }

            else -> Unit
        }
    }

    private suspend fun fetchTrappingsList(
        className: String,
        careerPathName: String
    ): Pair<List<ItemItem>, List<ItemItem>> {
        return try {
            // PLACEHOLDER: Replace with actual condition to determine data source
            val result = if (true) {
                libraryDataSource.getTrappings(
                    className = className,
                    careerPathName = careerPathName
                )
            } else {
                characterCreatorClient.getTrappings(
                    className = className,
                    careerPathName = careerPathName
                )
            }

            val classList = result.getOrNull(0) ?: emptyList()
            val careerList = result.getOrNull(1) ?: emptyList()
            classList to careerList

        } catch (e: DataException) {
            _state.update { it.copy(error = e.error) }
            emptyList<ItemItem>() to emptyList()
        } catch (_: CancellationException) {
            emptyList<ItemItem>() to emptyList()
        } catch (_: Exception) {
            _state.update { it.copy(error = DataError.Local.UNKNOWN) }
            emptyList<ItemItem>() to emptyList()
        }
    }

    private suspend fun fetchWealth(careerPathName: String): List<Int> {
        return try {
            // PLACEHOLDER: Replace with actual condition to determine data source
            if (true) libraryDataSource.getWealth(careerPathName = careerPathName)
            else characterCreatorClient.getWealth(careerPathName = careerPathName)

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

    private fun loadTrappingsList(
        className: String,
        careerPathName: String
    ) {
        trappingsJob?.cancel()
        trappingsJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val (classList, careerList) = fetchTrappingsList(className, careerPathName)
            _state.update {
                it.copy(
                    classTrappings = classList,
                    careerTrappings = careerList
                )
            }

            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun loadWealthList(careerPathName: String) {
        wealthJob?.cancel()
        wealthJob = viewModelScope.launch {
            val wealth = fetchWealth(careerPathName)
            _state.update { it.copy(wealth = wealth) }
        }
    }
}
