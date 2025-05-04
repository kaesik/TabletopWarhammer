package com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details

data class CharacterDetailsState(
    val error: String? = null,
    val isLoading: Boolean = false,

    val name: String = "",
    val age: String = "",
    val height: String = "",
    val hairColor: String = "",
    val eyeColor: String = "",

    val forename: String = "",
    val surname: String = "",
    val clan: String = "",
    val epithet: String = "",
)
