package com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species

import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem
import com.kaesik.tabletopwarhammer.core.domain.util.DataError

data class CharacterSpeciesState(
    val error: DataError? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,

    val selectedSpecies: SpeciesItem? = null,
    
    val canSelectSpecies: Boolean = false,

    val speciesList: List<SpeciesItem> = emptyList()
)
