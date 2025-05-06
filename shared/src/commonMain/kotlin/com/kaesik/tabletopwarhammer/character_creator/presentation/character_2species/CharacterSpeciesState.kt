package com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species

import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem

data class CharacterSpeciesState(
    val error: String? = null,
    val isLoading: Boolean = false,

    val selectedSpecies: SpeciesItem? = null,
    val hasRolledSpecies: Boolean = false,

    val speciesList: List<SpeciesItem> = emptyList(),
)
