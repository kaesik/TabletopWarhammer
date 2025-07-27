package com.kaesik.tabletopwarhammer.core.domain

import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum
import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object MainGraph : Route

    @Serializable
    data object Menu : Route

    @Serializable
    data object Settings : Route

    @Serializable
    data object About : Route

    @Serializable
    data object Help : Route

    // AUTH
    @Serializable
    data object AuthGraph : Route

    @Serializable
    data object Intro : Route

    @Serializable
    data object Login : Route

    @Serializable
    data object Register : Route

    // USER
    @Serializable
    data object UserGraph : Route

    @Serializable
    data object User : Route

    // LIBRARY
    @Serializable
    data object LibraryGraph : Route

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

    // CHARACTER SHEET
    @Serializable
    data object CharacterSheetGraph : Route

    @Serializable
    data object CharacterSheetList : Route

    @Serializable
    data class CharacterSheet(
        val characterId: Int,
    ) : Route

    // CHARACTER CREATOR
    @Serializable
    data object CharacterCreatorGraph : Route

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

    @Serializable
    data object CharacterFinal : Route
}
