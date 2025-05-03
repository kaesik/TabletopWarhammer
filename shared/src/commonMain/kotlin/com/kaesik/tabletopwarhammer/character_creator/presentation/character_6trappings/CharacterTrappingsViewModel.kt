package com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings.components.ClassOrCareer
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterTrappingsViewModel(
    private val characterCreatorClient: CharacterCreatorClient
) : ViewModel() {
    private val _state = MutableStateFlow(CharacterTrappingsState())
    val state = _state.asStateFlow()

    private var trappingsJob: Job? = null

    fun onEvent(event: CharacterTrappingsEvent) {
        when (event) {
            is CharacterTrappingsEvent.InitTrappingsList -> loadTrappingsList(
                event.from, event.className, event.careerPathName
            )

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
            val result = characterCreatorClient.getTrappings(
                className = className,
                careerPathName = careerPathName
            )
            _state.update {
                when (from) {
                    ClassOrCareer.CLASS -> it.copy(classTrappingList = result)
                    ClassOrCareer.CAREER -> it.copy(careerTrappingList = result)
                }.copy(trappingList = result)
            }
        }
    }
}
