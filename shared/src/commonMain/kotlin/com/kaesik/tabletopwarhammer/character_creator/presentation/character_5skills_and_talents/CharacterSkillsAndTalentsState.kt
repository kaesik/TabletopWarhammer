package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents

import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SpeciesOrCareer
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem
import com.kaesik.tabletopwarhammer.core.domain.util.DataError

data class CharacterSkillsAndTalentsState(
    val error: DataError? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,

    val speciesOrCareer: SpeciesOrCareer = SpeciesOrCareer.SPECIES,

    // SKILLS
    val skillList: List<List<SkillItem>> = emptyList(),
    val basicSkills: List<SkillItem> = emptyList(),
    val speciesSkills: List<SkillItem> = emptyList(),
    val careerSkills: List<SkillItem> = emptyList(),
    val selectedSkills: List<SkillItem> = emptyList(),
    val selectedSkills3: List<SkillItem> = emptyList(),
    val selectedSkills5: List<SkillItem> = emptyList(),
    val careerSkillPoints: Map<String, Int> = emptyMap(),
    val skillOrGroups: Map<String, Set<String>> = emptyMap(),
    val totalAllocatedPoints: Int = 0,

    // TALENTS
    val speciesTalentsList: List<List<TalentItem>> = emptyList(),
    val careerTalentsList: List<List<TalentItem>> = emptyList(),
    val talentList: List<List<TalentItem>> = emptyList(),
    val selectedTalents: List<TalentItem> = emptyList(),
    val selectedSpeciesTalents: List<TalentItem> = emptyList(),
    val selectedCareerTalents: List<TalentItem> = emptyList(),
    val rolledTalents: Map<Pair<Int, Int>, String> = emptyMap(),
)
