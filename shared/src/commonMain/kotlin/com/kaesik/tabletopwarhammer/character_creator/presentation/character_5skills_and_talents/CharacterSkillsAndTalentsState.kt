package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents

import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SpeciesOrCareer
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem

data class CharacterSkillsAndTalentsState(
    val error: String? = null,
    val isLoading: Boolean = false,

    val skillList: List<List<SkillItem>> = emptyList(),
    val talentList: List<List<TalentItem>> = emptyList(),

    val selectedSkills: List<SkillItem> = emptyList(),
    val speciesOrCareer: SpeciesOrCareer = SpeciesOrCareer.SPECIES,
)
