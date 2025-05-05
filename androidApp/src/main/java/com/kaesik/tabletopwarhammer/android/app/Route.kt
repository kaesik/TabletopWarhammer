package com.kaesik.tabletopwarhammer.android.app

import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum
import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object MainGraph : Route

    @Serializable
    data object Menu : Route

    @Serializable
    data object Library : Route

    @Serializable
    data class LibraryList(
        val fromTable: LibraryEnum
    ) : Route

    @Serializable
    data class LibraryItem(
        val itemId: String,
        val fromTable: LibraryEnum
    ) : Route

    @Serializable
    data object CharacterSheet : Route

    @Serializable
    data object CharacterCreator : Route

    @Serializable
    data object CharacterSpecies : Route

    @Serializable
    data class CharacterClassAndCareer(
        val characterSpecies: String
    ) : Route

    @Serializable
    data class CharacterAttributes(
        val characterSpecies: String
    ) : Route

    @Serializable
    data class CharacterSkillsAndTalents(
        val characterSpecies: String,
        val characterCareer: String,
    ) : Route

    @Serializable
    data class CharacterTrappings(
        val characterClass: String,
        val characterCareerPath: String,
    ) : Route

    @Serializable
    data class CharacterDetails(
        val characterSpecies: String,
    ) : Route

    @Serializable
    data object CharacterTenQuestions : Route

    @Serializable
    data object CharacterAdvancement : Route
}
