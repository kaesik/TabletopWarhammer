package com.kaesik.tabletopwarhammer.core.presentation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Menu: Route

    @Serializable
    data object Library: Route

    @Serializable
    data class LibraryList(val fromTable: String): Route

    @Serializable
    data class LibraryItem(val fromTable: String, val id: String): Route
}
