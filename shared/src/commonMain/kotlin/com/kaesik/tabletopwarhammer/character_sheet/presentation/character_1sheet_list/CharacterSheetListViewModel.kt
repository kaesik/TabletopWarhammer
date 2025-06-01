package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_1sheet_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterSheetListViewModel(
    private val characterDataSource: CharacterDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterSheetListState())
    val state = _state.asStateFlow()

    fun onEvent(event: CharacterSheetListEvent) {
        when (event) {
            is CharacterSheetListEvent.LoadCharacters -> {
                viewModelScope.launch {
                    _state.value = _state.value.copy(isLoading = true, error = null)
                    try {
                        val characters = characterDataSource.getAllCharacters()
                        _state.value = _state.value.copy(
                            characters = characters,
                            isLoading = false
                        )
                    } catch (e: Exception) {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = e.message ?: "Wystąpił nieznany błąd"
                        )
                    }
                }
            }

            is CharacterSheetListEvent.OnCharacterClick -> {
                // obsługa nawigacji po stronie UI (np. przekazanie przez callback)
            }

            is CharacterSheetListEvent.OnDeleteCharacter -> {
                viewModelScope.launch {
                    try {
                        characterDataSource.deleteCharacter(event.character.id.toLong())
                        val updatedCharacters = characterDataSource.getAllCharacters()
                        _state.value = _state.value.copy(characters = updatedCharacters)
                    } catch (e: Exception) {
                        _state.value = _state.value.copy(error = "Nie udało się usunąć postaci.")
                    }
                }
            }

            else -> Unit
        }
    }
}
