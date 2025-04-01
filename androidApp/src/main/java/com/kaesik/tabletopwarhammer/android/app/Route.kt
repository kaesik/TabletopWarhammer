package com.kaesik.tabletopwarhammer.android.app

import com.kaesik.tabletopwarhammer.library.data.library.LibraryEnum
import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object MainGraph : Route

    @Serializable
    data object Menu : Route

    @Serializable
    data object Library : Route

    @Serializable
    data class LibraryList(val fromTable: LibraryEnum) : Route

    @Serializable
    data class LibraryItem(val id: String) : Route

    @Serializable
    data object CharacterSheet : Route

    @Serializable
    data object CharacterCreator : Route
}
