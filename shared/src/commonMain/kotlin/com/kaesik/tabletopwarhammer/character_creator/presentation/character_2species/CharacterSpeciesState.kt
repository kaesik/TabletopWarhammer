package com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species

data class CharacterSpeciesState(
    val error: String? = null,
    val isLoading: Boolean = false,

    val speciesList : List<String> = emptyList(),
)
