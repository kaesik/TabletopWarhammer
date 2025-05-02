package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents

import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SpeciesOrCareer
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem

sealed class CharacterSkillsAndTalentsEvent {
    data class InitSkillsList(val from: SpeciesOrCareer) : CharacterSkillsAndTalentsEvent()
    data class InitTalentsList(val from: SpeciesOrCareer) : CharacterSkillsAndTalentsEvent()

    data class OnSkillChecked(
        val skill: SkillItem,
        val isChecked: Boolean
    ) : CharacterSkillsAndTalentsEvent()

    data object OnSpeciesOrCareerClick : CharacterSkillsAndTalentsEvent()
    data object OnNextClick : CharacterSkillsAndTalentsEvent()
}
