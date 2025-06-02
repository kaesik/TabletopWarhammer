package com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator

import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ClassItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem
import com.kaesik.tabletopwarhammer.core.domain.util.DataError

data class CharacterCreatorState(
    val error: DataError? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,

    val character: CharacterItem = CharacterItem.default(),

    val selectedSpecies: SpeciesItem? = null,
    val selectedClass: ClassItem? = null,
    val selectedCareer: CareerItem? = null,
)
