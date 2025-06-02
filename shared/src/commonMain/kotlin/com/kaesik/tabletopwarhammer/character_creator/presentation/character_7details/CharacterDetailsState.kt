package com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details

import com.kaesik.tabletopwarhammer.core.domain.util.DataError

data class CharacterDetailsState(
    val error: DataError? = null,
    val message: String? = null,
    val isError: Boolean = false,
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
