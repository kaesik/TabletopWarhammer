package com.kaesik.tabletopwarhammer.android.app

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object MainGraph : Route

    @Serializable
    data object Menu : Route

    @Serializable
    data object Library : Route

    @Serializable
    data object LibraryList : Route

    @Serializable
    data class LibraryItem(val id: String) : Route

    @Serializable
    data object CharacterSheet : Route

    @Serializable
    data object CharacterCreator : Route
}
