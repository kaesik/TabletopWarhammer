package com.kaesik.tabletopwarhammer.library.domain.library

import com.kaesik.tabletopwarhammer.library.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

interface LibraryClient {

    suspend fun getLibraryList(
        fromTable: LibraryEnum
    ): List<LibraryItem>

    suspend fun getLibraryItem(
        itemId: String,
        fromTable: LibraryEnum
    ): LibraryItem
}
