package com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings.components.ClassOrCareer
import com.kaesik.tabletopwarhammer.core.domain.library.LibraryDataSource
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
            is CharacterTrappingsEvent.InitTrappingsList -> {
                loadTrappingsList(
                    event.from, event.className, event.careerPathName
                )
            }

            is CharacterTrappingsEvent.InitWealthList -> loadWealthList(event.careerPathName)

            else -> Unit
        }
    }

    private fun loadTrappingsList(
        from: ClassOrCareer,
        className: String,
        careerPathName: String
    ) {
        trappingsJob?.cancel()
        trappingsJob = viewModelScope.launch {
            val trappingList = if (true) {
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

            _state.update {
                when (from) {
                    ClassOrCareer.CLASS -> it.copy(classTrappingList = trappingList)
                    ClassOrCareer.CAREER -> it.copy(careerTrappingList = trappingList)
                }.copy(trappingList = trappingList)
            }
        }
    }

    private fun loadWealthList(
        careerPathName: String
    ) {
        wealthJob?.cancel()
        wealthJob = viewModelScope.launch {
            val wealthList = if (true) {
                libraryDataSource.getWealth(
                    careerPathName = careerPathName
                )
            } else {
                characterCreatorClient.getWealth(
                    careerPathName = careerPathName
                )
            }

            val result =
                _state.update { it.copy(wealth = wealthList) }
        }
    }
}
