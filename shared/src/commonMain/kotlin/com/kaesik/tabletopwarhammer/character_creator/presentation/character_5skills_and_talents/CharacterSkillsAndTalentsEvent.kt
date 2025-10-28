package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents

import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SpeciesOrCareer
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem

sealed class CharacterSkillsAndTalentsEvent {
    data class InitSkillsList(
        val from: SpeciesOrCareer,
        val speciesName: String,
        val careerPathName: String,
    ) : CharacterSkillsAndTalentsEvent()

    data class InitTalentsList(
        val from: SpeciesOrCareer,
        val speciesName: String,
        val careerPathName: String,
    ) : CharacterSkillsAndTalentsEvent()

    data class InitFromSelections(
        val selectedSkillNames3: List<String>,
        val selectedSkillNames5: List<String>,
        val careerSkillPoints: Map<String, Int>,
        val selectedSpeciesTalentNames: List<String>,
        val selectedCareerTalentNames: List<String>,
        val rolledTalents: Map<Pair<Int, Int>, String>
    ) : CharacterSkillsAndTalentsEvent()

    data class OnSkillChecked(
        val skill: SkillItem,
        val isChecked: Boolean
    ) : CharacterSkillsAndTalentsEvent()

    data class OnSkillChecked3(
        val skill: SkillItem,
        val isChecked: Boolean
    ) : CharacterSkillsAndTalentsEvent()

    data class OnSkillChecked5(
        val skill: SkillItem,
        val isChecked: Boolean
    ) : CharacterSkillsAndTalentsEvent()

    data class OnTalentChecked(
        val talent: TalentItem,
        val isChecked: Boolean
    ) : CharacterSkillsAndTalentsEvent()

    data class OnRandomTalentRolled(
        val groupIndex: Int,
        val talentIndex: Int,
        val rolledName: String
    ) : CharacterSkillsAndTalentsEvent()

    data class OnCareerSkillValueChanged(
        val skill: SkillItem,
        val newValue: Int
    ) : CharacterSkillsAndTalentsEvent()

    data class RequestTalentSpecializations(val baseName: String) : CharacterSkillsAndTalentsEvent()

    data object OnSaveSkillsAndTalents : CharacterSkillsAndTalentsEvent()
    data object OnSpeciesOrCareerClick : CharacterSkillsAndTalentsEvent()
    data object OnNextClick : CharacterSkillsAndTalentsEvent()
}
