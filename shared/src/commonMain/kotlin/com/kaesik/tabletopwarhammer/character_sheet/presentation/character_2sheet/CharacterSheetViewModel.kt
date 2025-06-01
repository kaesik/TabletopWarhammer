package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterSheetViewModel(
    private val characterDataSource: CharacterDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterSheetState())
    val state = _state.asStateFlow()

    fun onEvent(event: CharacterSheetEvent) {
        when (event) {
            is CharacterSheetEvent.LoadCharacterById -> {
                viewModelScope.launch {
                    _state.value = _state.value.copy(isLoading = true)
                    try {
                        val characters = characterDataSource.getAllCharacters()
                        val character = characters.find { it.id == event.id }

                        if (character != null) {
                            _state.value = _state.value.copy(
                                character = character,
                                isLoading = false
                            )
                        } else {
                            _state.value = _state.value.copy(
                                error = "Character not found",
                                isLoading = false
                            )
                        }
                    } catch (e: Exception) {
                        _state.value = _state.value.copy(
                            error = e.message ?: "Unknown error",
                            isLoading = false
                        )
                    }
                }
            }

            is CharacterSheetEvent.OnBackClick -> {

            }

            else -> Unit
        }
    }
}
