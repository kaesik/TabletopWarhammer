package com.kaesik.tabletopwarhammer.character_creator.presentation.character_3class_and_career

import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ClassItem

data class CharacterClassAndCareerState(
    val error: String? = null,
    val isLoading: Boolean = false,

    val classList: List<ClassItem> = emptyList(),
    val careerList: List<CareerItem> = emptyList(),
)
