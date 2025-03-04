package com.kaesik.tabletopwarhammer.library.domain.library

import com.kaesik.tabletopwarhammer.core.domain.util.Resource
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

class Library(
    private val client: LibraryClient,
) {
    suspend fun loadLibrary(
        fromTable: String
    ): Resource<List<LibraryItem>> {
        println("Library.loadLibrary")
        return try {
            val library = when (fromTable) {
                "attribute" -> {
                    client.getAttributes()
                }

                "career" -> {
                    client.getCareers()
                }

                "careerpath" -> {
                    client.getCareerPaths()
                }

                "class" -> {
                    client.getClasses()
                }

                "item" -> {
                    client.getItems()
                }

                "qualityflaw" -> {
                    client.getQualitiesFlaws()
                }

                "skill" -> {
                    client.getSkills()
                }

                "species" -> {
                    client.getSpecies()
                }

                "talent" -> {
                    client.getTalents()
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
