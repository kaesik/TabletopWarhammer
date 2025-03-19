package com.kaesik.tabletopwarhammer.library.domain.library

import com.kaesik.tabletopwarhammer.core.domain.util.Resource
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

class Library(
    private val client: LibraryClient,
) {
    suspend fun loadLibrary(
        fromTable: String
    ): Resource<List<LibraryItem>> {
        return try {
            val library = client.getLibraryList(fromTable)
            println("Library.loadLibrary: $library")
            Resource.Success(library)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    suspend fun loadLibraryList(
        id: String,
        libraryList: List<LibraryItem>,
    ): Resource<LibraryItem> {
        return try {
            val libraryItem = client.getLibraryItem(id, libraryList)
            Resource.Success(libraryItem)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}
