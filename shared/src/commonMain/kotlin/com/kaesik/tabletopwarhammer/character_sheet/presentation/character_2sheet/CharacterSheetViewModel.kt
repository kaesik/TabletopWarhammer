package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterLocalDataSource
import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.core.domain.util.DataException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterSheetViewModel(
    private val characterLocalDataSource: CharacterLocalDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterSheetState())
    val state = _state.asStateFlow()

    fun onEvent(event: CharacterSheetEvent) {
        when (event) {
            is CharacterSheetEvent.LoadCharacterById -> loadCharacter(event.id)
            is CharacterSheetEvent.SaveCharacter -> saveCharacter()
            is CharacterSheetEvent.UpdateCharacter -> {
                _state.update { it.copy(character = event.character) }
            }

            is CharacterSheetEvent.ShowMessage -> {
                _state.update { it.copy(message = event.message) }
            }

            is CharacterSheetEvent.ClearMessage -> {
                _state.update { it.copy(message = null) }
            }
            // GENERAL INFO
            is CharacterSheetEvent.SetGeneralInfo -> updateGeneralInfo(event)

            // POINTS
            is CharacterSheetEvent.AddExperience -> addExperience(event.delta)
            is CharacterSheetEvent.SetExperienceValues -> setExperienceValues(
                currentVal = event.current,
                spentVal = event.spent,
                totalVal = event.total
            )

            is CharacterSheetEvent.SetResilience -> setResilienceAndResolve(event)
            is CharacterSheetEvent.SetFate -> setFateAndFortune(event)
            is CharacterSheetEvent.SetCorruption -> setCorruption(event.value)
            is CharacterSheetEvent.AddMutation -> addMutation()

        }
    }

    private fun loadCharacter(id: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    error = null,
                    isError = false
                )
            }

            try {
                val characters = characterLocalDataSource.getAllCharacters()
                val character = characters.find { it.id == id }

                if (character != null) {
                    _state.update {
                        it.copy(
                            character = character,
                            isLoading = false,
                            isError = false
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            error = DataError.Local.FILE_NOT_FOUND,
                            isLoading = false,
                            isError = true
                        )
                    }
                }
            } catch (e: DataException) {
                _state.update {
                    it.copy(
                        error = e.error,
                        isLoading = false,
                        isError = true
                    )
                }
            } catch (_: Throwable) {
                _state.update {
                    it.copy(
                        error = DataError.Local.UNKNOWN,
                        isLoading = false,
                        isError = true
                    )
                }
            }
        }
    }

    private inline fun updateCharacter(
        crossinline block: (CharacterItem) -> CharacterItem
    ) {
        val current = _state.value.character ?: return
        _state.update { it.copy(character = block(current)) }
    }

    private fun saveCharacter() {
        val characterToSave = _state.value.character ?: return

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isSaving = true,
                    error = null,
                    isError = false
                )
            }

            try {
                characterLocalDataSource.updateCharacter(characterToSave)

                _state.update {
                    it.copy(
                        character = characterToSave,
                        isSaving = false,
                        isError = false,
                        message = "Zapisano kartę postaci"
                    )
                }
            } catch (e: DataException) {
                _state.update {
                    it.copy(
                        error = e.error,
                        isSaving = false,
                        isError = true
                    )
                }
            } catch (_: Throwable) {
                _state.update {
                    it.copy(
                        error = DataError.Local.UNKNOWN,
                        isSaving = false,
                        isError = true
                    )
                }
            }
        }
    }

    private fun updateGeneralInfo(event: CharacterSheetEvent.SetGeneralInfo) {
        val current = _state.value.character ?: return

        val updated = current.copy(
            name = event.name ?: current.name,
            species = event.species ?: current.species,
            cLass = event.cLass ?: current.cLass,
            career = event.career ?: current.career,
            careerLevel = event.careerLevel ?: current.careerLevel,
            careerPath = event.careerPath ?: current.careerPath,
            status = event.status ?: current.status,
            age = event.age ?: current.age,
            height = event.height ?: current.height,
            hair = event.hair ?: current.hair,
            eyes = event.eyes ?: current.eyes,
            ambitionShortTerm = event.ambitionShortTerm ?: current.ambitionShortTerm,
            ambitionLongTerm = event.ambitionLongTerm ?: current.ambitionLongTerm
        )

        _state.update { it.copy(character = updated) }
    }

    // POINTS

    private fun addExperience(delta: Int) {
        updateCharacter { current ->
            val list = current.experience.toMutableList()
            while (list.size < 3) list.add(0)

            val newCurrent = (list[0] + delta).coerceAtLeast(0)
            val newTotal = (list[2] + delta).coerceAtLeast(0)

            list[0] = newCurrent
            list[2] = newTotal

            current.copy(experience = list)
        }
    }

    private fun setExperienceValues(
        currentVal: Int?,
        spentVal: Int?,
        totalVal: Int?
    ) {
        updateCharacter { current ->
            val list = current.experience.toMutableList()
            while (list.size < 3) list.add(0)

            if (currentVal != null) {
                list[0] = currentVal.coerceAtLeast(0)
            }
            if (spentVal != null) {
                list[1] = spentVal.coerceAtLeast(0)
            }
            if (totalVal != null) {
                list[2] = totalVal.coerceAtLeast(0)
            }

            current.copy(experience = list)
        }
    }

    private fun setResilienceAndResolve(event: CharacterSheetEvent.SetResilience) {
        updateCharacter { current ->
            current.copy(
                resilience = event.resilience?.coerceAtLeast(0) ?: current.resilience,
                resolve = event.resolve?.coerceAtLeast(0) ?: current.resolve,
                motivation = event.motivation ?: current.motivation
            )
        }
    }

    private fun setFateAndFortune(event: CharacterSheetEvent.SetFate) {
        updateCharacter { current ->
            current.copy(
                fate = event.fate?.coerceAtLeast(0) ?: current.fate,
                fortune = event.fortune?.coerceAtLeast(0) ?: current.fortune
            )
        }
    }

    private fun setCorruption(value: Int) {
        updateCharacter { current ->
            current.copy(
                corruption = value.coerceAtLeast(0)
            )
        }
    }

    private fun addMutation() {
        updateCharacter { current ->
            val updated = current.mutations.toMutableList()
            updated.add("Mutation:Description")
            current.copy(mutations = updated)
        }
    }
}
