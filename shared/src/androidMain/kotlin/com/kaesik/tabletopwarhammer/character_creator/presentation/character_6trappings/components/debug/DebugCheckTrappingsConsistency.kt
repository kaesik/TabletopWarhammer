package com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings.components.debug

import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.core.data.library.dto.CareerPathDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.ClassDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.ItemDto
import com.kaesik.tabletopwarhammer.library.data.Const
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from

suspend fun debugCheckTrappingsConsistency() {
    val supabaseClient = createSupabaseClient(
        supabaseKey = Const.SUPABASE_ANON_KEY,
        supabaseUrl = Const.SUPABASE_URL
    ) {
        install(Postgrest)
    }
    try {
        val classList = supabaseClient
            .from(LibraryEnum.CLASS.tableName)
            .select()
            .decodeList<ClassDto>()

        val careerPathList = supabaseClient
            .from(LibraryEnum.CAREER_PATH.tableName)
            .select()
            .decodeList<CareerPathDto>()

        val allItemDto = supabaseClient
            .from(LibraryEnum.ITEM.tableName)
            .select()
            .decodeList<ItemDto>()

        val allItemNames = allItemDto.map { it.name.trim().lowercase() }.toSet()

        fun extractTrappings(raw: String?): List<String> {
            if (raw.isNullOrBlank()) return emptyList()
            return raw.split(",")
                .flatMap { part ->
                    val trimmed = part.trim()
                    if (trimmed.contains(" containing ", ignoreCase = true)) {
                        val (main, contained) = trimmed.split(" containing ", limit = 2)
                        val containedItems = contained
                            .replace(" and ", ",")
                            .split(",")
                            .map { it.trim() }
                        listOf(main.trim()) + containedItems
                    } else {
                        listOf(trimmed)
                    }
                }
                .map {
                    it.removePrefix("a ").removePrefix("an ").removePrefix("the ").trim()
                }
                .filter { it.isNotEmpty() }
        }

        println("==== 🔍 CHECKING CLASS TRAPPINGS ====")
        classList.forEach { classDto ->
            val names = extractTrappings(classDto.trappings)
            val missing = names.filter { it.lowercase() !in allItemNames }
            val matched = names.size - missing.size

            println("📚 Class: ${classDto.name}")
            println("🧾 RAW: ${classDto.trappings}")
            println("🎒 TRAPPING: ${names.joinToString()}")

            if (missing.isEmpty()) {
                println("✅ $matched/${names.size} trappings exist")
            } else {
                println("❌ $matched/${names.size} trappings exist")
                println("   Missing: ${missing.joinToString()}")
            }
        }

        println("\n==== 🔍 CHECKING CAREER PATH TRAPPINGS ====")
        careerPathList.forEach { cpDto ->
            val names = extractTrappings(cpDto.trappings)
            val missing = names.filter { it.lowercase() !in allItemNames }
            val matched = names.size - missing.size

            println("🏛 Career Path: ${cpDto.name}")
            println("🧾 RAW: ${cpDto.trappings}")
            println("🎒 TRAPPING: ${names.joinToString()}")

            if (missing.isEmpty()) {
                println("✅ $matched/${names.size} trappings exist")
            } else {
                println("❌ $matched/${names.size} trappings exist")
                println("   Missing: ${missing.joinToString()}")
            }
        }

    } catch (e: Exception) {
        println("🔥 Error during trappings debug: ${e.message}")
    }
}
