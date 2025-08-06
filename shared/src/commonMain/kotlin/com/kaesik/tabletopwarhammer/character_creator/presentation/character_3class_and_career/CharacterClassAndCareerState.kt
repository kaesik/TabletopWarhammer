package com.kaesik.tabletopwarhammer.character_creator.presentation.character_3class_and_career

import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ClassItem
import com.kaesik.tabletopwarhammer.core.domain.util.DataError

data class CharacterClassAndCareerState(
    val error: DataError? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,

    val selectedClass: ClassItem? = null,
    val selectedCareer: CareerItem? = null,

    var canSelectClass: Boolean = false,
    var canSelectCareer: Boolean = false,
    val hasRolledClassAndCareer: Boolean = false,

    val classList: List<ClassItem> = emptyList(),
    val careerList: List<CareerItem> = emptyList()
)
