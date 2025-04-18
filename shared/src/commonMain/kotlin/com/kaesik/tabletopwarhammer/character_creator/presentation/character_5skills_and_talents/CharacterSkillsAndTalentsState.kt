package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents

import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem

data class CharacterSkillsAndTalentsState(
    val error: String? = null,
    val isLoading: Boolean = false,

    val skillList: List<SkillItem> = emptyList(),
    val talentList: List<TalentItem> = emptyList(),
)
