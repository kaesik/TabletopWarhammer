package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterAttributesViewModel(
    private val characterCreatorClient: CharacterCreatorClient
) : ViewModel() {
    private val _state = MutableStateFlow(CharacterAttributesState())
    val state = _state.asStateFlow()

    private var characterAttributeJob: Job? = null

    fun onEvent(event: CharacterAttributesEvent) {
        when (event) {
            is CharacterAttributesEvent.InitAttributesList -> {
                loadAttributesList()
            }

            else -> Unit
        }
    }

    private fun loadAttributesList() {
        characterAttributeJob?.cancel()
        characterAttributeJob = viewModelScope.launch {
            val attributeList = characterCreatorClient.getAttributes()
            _state.value = state.value.copy(
                attributeList = attributeList,
            )
        }
    }
}
