package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_1sheet_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterDataSource
import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.core.domain.util.DataException
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
                    } catch (e: DataException) {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = e.error
                        )
                    } catch (e: Exception) {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = DataError.Local.UNKNOWN
                        )
                    }
                }
            }

            is CharacterSheetListEvent.OnDeleteCharacter -> {
                viewModelScope.launch {
                    try {
                        characterDataSource.deleteCharacter(event.character.id.toLong())
                        val updatedCharacters = characterDataSource.getAllCharacters()
                        _state.value = _state.value.copy(characters = updatedCharacters)
                    } catch (e: DataException) {
                        _state.value = _state.value.copy(error = e.error)
                    } catch (e: Exception) {
                        _state.value = _state.value.copy(error = DataError.Local.UNKNOWN)
                    }
                }
            }

            else -> Unit
        }
    }

}
