package com.kaesik.tabletopwarhammer.library.domain.library

import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

interface LibraryClient {

    suspend fun getLibraryList(
        fromTable: String
    ): List<LibraryItem>

    suspend fun getLibraryItem(
        id: String,
        libraryList: List<LibraryItem>
    ): LibraryItem
}
