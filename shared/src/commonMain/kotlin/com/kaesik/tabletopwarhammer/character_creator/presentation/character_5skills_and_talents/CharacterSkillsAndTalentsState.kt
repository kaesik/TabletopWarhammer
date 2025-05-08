package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents

import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SpeciesOrCareer
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem

data class CharacterSkillsAndTalentsState(
    val error: String? = null,
    val isLoading: Boolean = false,

    val speciesOrCareer: SpeciesOrCareer = SpeciesOrCareer.SPECIES,

    val speciesSkillsList: List<List<SkillItem>> = emptyList(),
    val careerSkillsList: List<List<SkillItem>> = emptyList(),
    val skillList: List<List<SkillItem>> = emptyList(),
    val selectedSkills: List<SkillItem> = emptyList(),
    val selectedSkills3: List<SkillItem> = emptyList(),
    val selectedSkills5: List<SkillItem> = emptyList(),

    val speciesTalentsList: List<List<TalentItem>> = emptyList(),
    val careerTalentsList: List<List<TalentItem>> = emptyList(),
    val talentList: List<List<TalentItem>> = emptyList(),
    val selectedTalents: List<TalentItem> = emptyList(),
)
