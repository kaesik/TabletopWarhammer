package com.kaesik.tabletopwarhammer.library.domain.library

import com.kaesik.tabletopwarhammer.library.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

interface LibraryClient {

    suspend fun getLibraryList(
        fromTable: LibraryEnum
    ): List<LibraryItem>

    fun getLibraryItem(
        id: String,
        libraryList: List<LibraryItem>
    ): LibraryItem
}
