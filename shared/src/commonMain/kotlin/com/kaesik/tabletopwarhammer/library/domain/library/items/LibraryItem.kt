package com.kaesik.tabletopwarhammer.library.domain.library.items

sealed interface LibraryItem {
    val id: String
    val name: String
}
