package com.kaesik.tabletopwarhammer.library.domain.library

import com.kaesik.tabletopwarhammer.core.data.library.LibraryDelta
import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

interface LibraryClient {

    suspend fun getLibraryList(
        fromTable: LibraryEnum
    ): List<LibraryItem>

    suspend fun getLibraryItem(
        fromTable: LibraryEnum,
        itemId: String
    ): LibraryItem

    suspend fun getLibraryDelta(
        fromTable: LibraryEnum,
        sinceEpochMs: Long?
    ): LibraryDelta
}
