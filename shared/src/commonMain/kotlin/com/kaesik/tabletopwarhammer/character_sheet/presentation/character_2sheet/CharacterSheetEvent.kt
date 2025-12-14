package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet

import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem

sealed class CharacterSheetEvent {
    data class ShowMessage(val message: String) : CharacterSheetEvent()
    data object ClearMessage : CharacterSheetEvent()

    data class LoadCharacterById(val id: Int) : CharacterSheetEvent()
    data object SaveCharacter : CharacterSheetEvent()

    data class UpdateCharacter(val character: CharacterItem) : CharacterSheetEvent()

    data class SetGeneralInfo(
        val name: String? = null,
        val species: String? = null,
        val cLass: String? = null,
        val career: String? = null,
        val careerLevel: String? = null,
        val careerPath: String? = null,
        val status: String? = null,
        val age: String? = null,
        val height: String? = null,
        val hair: String? = null,
        val eyes: String? = null,
        val ambitionShortTerm: String? = null,
        val ambitionLongTerm: String? = null
    ) : CharacterSheetEvent()

    // POINTS
    data class AddExperience(val delta: Int) : CharacterSheetEvent()
    data class SetExperienceValues(
        val current: Int? = null,
        val spent: Int? = null,
        val total: Int? = null
    ) : CharacterSheetEvent()

    data class SetResilience(
        val resilience: Int? = null,
        val resolve: Int? = null,
        val motivation: String? = null
    ) : CharacterSheetEvent()

    data class SetFate(
        val fate: Int? = null,
        val fortune: Int? = null
    ) : CharacterSheetEvent()

    data class SetCorruption(val value: Int) : CharacterSheetEvent()
    data object AddMutation : CharacterSheetEvent()

    // ATTRIBUTES
    enum class Attribute {
        WS, BS, S, T, I, Ag, Dex, Int, WP, Fel
    }

    data class SetAttributeAdvances(
        val attribute: Attribute,
        val advances: Int
    ) : CharacterSheetEvent()

    data class ChangeCurrentWounds(val delta: Int) : CharacterSheetEvent()

    // SKILLS
    data class SetSkillAdvances(
        val name: String,
        val isBasic: Boolean,
        val advances: Int
    ) : CharacterSheetEvent()

    data object AddAdvancedSkill : CharacterSheetEvent()

    // TALENTS
    data class SetTalentCount(
        val name: String,
        val count: Int
    ) : CharacterSheetEvent()

    data object AddTalent : CharacterSheetEvent()

    // PARTY
    data class SetPartyName(val name: String) : CharacterSheetEvent()

    data class SetPartyAmbitions(
        val shortTerm: String? = null,
        val longTerm: String? = null
    ) : CharacterSheetEvent()

    data class SetPartyMembers(val members: List<List<String>>) : CharacterSheetEvent()

    data object AddPartyMember : CharacterSheetEvent()

}
