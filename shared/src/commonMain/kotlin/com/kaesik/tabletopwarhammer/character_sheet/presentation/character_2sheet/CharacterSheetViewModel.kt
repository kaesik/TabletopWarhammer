package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterDataSource
import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.core.domain.util.DataException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
                    _state.value = _state.value.copy(isLoading = true, error = null)
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
                                error = DataError.Local.FILE_NOT_FOUND,
                                isLoading = false
                            )
                        }
                    } catch (e: DataException) {
                        _state.value = _state.value.copy(
                            error = e.error,
                            isLoading = false
                        )
                    } catch (e: Exception) {
                        _state.value = _state.value.copy(
                            error = DataError.Local.UNKNOWN,
                            isLoading = false
                        )
                    }
                }
            }

            is CharacterSheetEvent.ShowMessage -> {
                _state.update { it.copy(message = event.message) }
            }

            is CharacterSheetEvent.ClearMessage -> {
                _state.update { it.copy(message = null) }
            }

            else -> Unit
        }
    }
}
