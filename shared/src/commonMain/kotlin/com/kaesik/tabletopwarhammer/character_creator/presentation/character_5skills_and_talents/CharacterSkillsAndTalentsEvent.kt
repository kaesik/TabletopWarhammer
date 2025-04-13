package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents

import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SpeciesOrCareer

sealed class CharacterSkillsAndTalentsEvent {
    data class InitSkillsList(val from: SpeciesOrCareer) : CharacterSkillsAndTalentsEvent()
    data class InitTalentsList(val from: SpeciesOrCareer) : CharacterSkillsAndTalentsEvent()

    data object OnNextClick : CharacterSkillsAndTalentsEvent()
}
