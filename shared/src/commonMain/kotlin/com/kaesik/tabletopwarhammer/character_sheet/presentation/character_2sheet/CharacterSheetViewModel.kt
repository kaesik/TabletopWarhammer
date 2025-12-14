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

            // ATTRIBUTES
            is CharacterSheetEvent.SetAttributeAdvances -> setAttributeAdvances(event)
            is CharacterSheetEvent.ChangeCurrentWounds -> changeCurrentWounds(event.delta)

            // SKILLS
            is CharacterSheetEvent.SetSkillAdvances -> setSkillAdvances(event)
            is CharacterSheetEvent.AddAdvancedSkill -> addAdvancedSkill()

            // TALENTS
            is CharacterSheetEvent.SetTalentCount -> setTalentCount(event)
            is CharacterSheetEvent.AddTalent -> addTalent()

            // PARTY
            is CharacterSheetEvent.SetPartyName -> setPartyName(event.name)
            is CharacterSheetEvent.SetPartyAmbitions -> setPartyAmbitions(event)
            is CharacterSheetEvent.SetPartyMembers -> setPartyMembers(event.members)
            is CharacterSheetEvent.AddPartyMember -> addPartyMember()
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

    // ATTRIBUTES
    private fun setAttributeAdvances(event: CharacterSheetEvent.SetAttributeAdvances) {
        updateCharacter { current ->
            fun upd(list: List<Int>, adv: Int): List<Int> {
                val base = list.getOrNull(0) ?: 0
                val newAdv = adv.coerceAtLeast(0)
                return listOf(base, newAdv, base + newAdv)
            }

            when (event.attribute) {
                CharacterSheetEvent.Attribute.WS ->
                    current.copy(weaponSkill = upd(current.weaponSkill, event.advances))

                CharacterSheetEvent.Attribute.BS ->
                    current.copy(ballisticSkill = upd(current.ballisticSkill, event.advances))

                CharacterSheetEvent.Attribute.S ->
                    current.copy(strength = upd(current.strength, event.advances))

                CharacterSheetEvent.Attribute.T ->
                    current.copy(toughness = upd(current.toughness, event.advances))

                CharacterSheetEvent.Attribute.I ->
                    current.copy(initiative = upd(current.initiative, event.advances))

                CharacterSheetEvent.Attribute.Ag ->
                    current.copy(agility = upd(current.agility, event.advances))

                CharacterSheetEvent.Attribute.Dex ->
                    current.copy(dexterity = upd(current.dexterity, event.advances))

                CharacterSheetEvent.Attribute.Int ->
                    current.copy(intelligence = upd(current.intelligence, event.advances))

                CharacterSheetEvent.Attribute.WP ->
                    current.copy(willPower = upd(current.willPower, event.advances))

                CharacterSheetEvent.Attribute.Fel ->
                    current.copy(fellowship = upd(current.fellowship, event.advances))
            }
        }
    }

    private fun changeCurrentWounds(delta: Int) {
        updateCharacter { current ->
            val list = current.wounds.toMutableList()
            while (list.size < 2) list.add(0)

            val maxW = list[1].coerceAtLeast(0)
            if (maxW <= 0) return@updateCharacter current

            val currStored = list[0]
            val effective = if (currStored <= 0) maxW else currStored
            val newVal = (effective + delta).coerceIn(0, maxW)

            list[0] = newVal
            current.copy(wounds = list)
        }
    }

    // SKILLS
    private fun setSkillAdvances(event: CharacterSheetEvent.SetSkillAdvances) {
        updateCharacter { current ->
            val value = event.advances.coerceAtLeast(0)

            fun updateList(
                list: List<List<String>>,
                name: String
            ): List<List<String>> {
                var used = false
                return list.map { entry ->
                    if (entry.getOrNull(0) != name) entry
                    else {
                        val mutable = entry.toMutableList()
                        while (mutable.size < 3) mutable.add("0")
                        val thisValue = if (!used) {
                            used = true
                            value
                        } else 0
                        mutable[2] = thisValue.toString()
                        mutable
                    }
                }
            }

            if (event.isBasic) {
                val updatedBasic = updateList(current.basicSkills, event.name)
                current.copy(basicSkills = updatedBasic)
            } else {
                val updatedAdvanced = updateList(current.advancedSkills, event.name)
                current.copy(advancedSkills = updatedAdvanced)
            }
        }
    }

    private fun addAdvancedSkill() {
        updateCharacter { current ->
            val baseName = "New Advanced Skill"
            val existingCount = current.advancedSkills.count {
                it.getOrNull(0)?.startsWith(baseName) == true
            }
            val newName = if (existingCount == 0) {
                baseName
            } else {
                "$baseName ${existingCount + 1}"
            }

            val newList = current.advancedSkills.toMutableList()
            newList.add(listOf(newName, "Int", "0"))
            current.copy(advancedSkills = newList)
        }
    }

    // TALENTS
    private fun setTalentCount(event: CharacterSheetEvent.SetTalentCount) {
        updateCharacter { current ->
            val baseList = current.talents
            val count = event.count.coerceAtLeast(0)
            val template =
                baseList.firstOrNull { it.getOrNull(0) == event.name } ?: listOf(event.name)

            val result = mutableListOf<List<String>>()
            var inserted = false

            for (entry in baseList) {
                if (entry.getOrNull(0) == event.name) {
                    if (!inserted) {
                        repeat(count) {
                            result.add(template)
                        }
                        inserted = true
                    }
                } else {
                    result.add(entry)
                }
            }

            if (!inserted && count > 0) {
                repeat(count) {
                    result.add(template)
                }
            }

            current.copy(talents = result)
        }
    }

    private fun addTalent() {
        updateCharacter { current ->
            val newList = current.talents.toMutableList()
            val newIndex = current.talents.size + 1
            newList.add(listOf("New Talent $newIndex"))
            current.copy(talents = newList)
        }
    }

    // PARTY
    private fun setPartyName(name: String) {
        updateCharacter { current ->
            current.copy(partyName = name)
        }
    }

    private fun setPartyAmbitions(event: CharacterSheetEvent.SetPartyAmbitions) {
        updateCharacter { current ->
            current.copy(
                partyAmbitionShortTerm = event.shortTerm ?: current.partyAmbitionShortTerm,
                partyAmbitionLongTerm = event.longTerm ?: current.partyAmbitionLongTerm
            )
        }
    }

    private fun setPartyMembers(members: List<List<String>>) {
        updateCharacter { current ->
            current.copy(partyMembers = members)
        }
    }

    private fun addPartyMember() {
        updateCharacter { current ->
            val updated = current.partyMembers.toMutableList()
            updated.add(listOf("New member", ""))
            current.copy(partyMembers = updated)
        }
    }

}
