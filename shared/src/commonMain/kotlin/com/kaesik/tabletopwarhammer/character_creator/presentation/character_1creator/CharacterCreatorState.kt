package com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator

import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ClassItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem

data class CharacterCreatorState(
    val error: String? = null,
    val isLoading: Boolean = false,

    val character: CharacterItem = CharacterItem.default(),

    val selectedSpecies: SpeciesItem? = null,
    val selectedClass: ClassItem? = null,
    val selectedCareer: CareerItem? = null,
)
