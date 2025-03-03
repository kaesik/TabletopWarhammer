package com.kaesik.tabletopwarhammer.library.domain.library

import com.kaesik.tabletopwarhammer.core.domain.util.Resource
import com.kaesik.tabletopwarhammer.main

class Library(
    private val client: LibraryClient,
) {
    suspend fun loadLibrary(
        fromTable: String
    ): Resource<List<Any>> {
        println("Library.loadLibrary")
        return try {
            val library = when (fromTable) {
                "attribute" -> {
                    client.getAttributes()
                }

                "career" -> {
                    client.getCareers()
                }

                else -> emptyList()
            }
            println(library)
            Resource.Success(library)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}
