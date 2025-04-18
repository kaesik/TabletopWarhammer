package com.kaesik.tabletopwarhammer.library.data.library

import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.core.data.library.dto.AttributeDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.CareerDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.CareerPathDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.ClassDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.ItemDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.QualityFlawDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.SkillDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.SpeciesDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.TalentDto
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toAttributeItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toCareerItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toCareerPathItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toClassItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toItemItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toQualityFlawItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toSkillItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toSpeciesItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toTalentItem
import com.kaesik.tabletopwarhammer.di.libraryList
import com.kaesik.tabletopwarhammer.library.data.Const
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryClient
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryError
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryException
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from

class KtorLibraryClient : LibraryClient {
    private val supabaseClient = createSupabaseClient(
        supabaseKey = Const.SUPABASE_ANON_KEY,
        supabaseUrl = Const.SUPABASE_URL
    ) {
        install(Postgrest)
    }

    override suspend fun getLibraryList(
        fromTable: LibraryEnum
    ): List<LibraryItem> {
        return try {
            println("Fetching data from table: $fromTable")
            val supabaseList = supabaseClient.from(fromTable.tableName).select()
            println("Supabase response: $supabaseList")
            when (fromTable) {
                LibraryEnum.ATTRIBUTE -> {
                    supabaseList
                        .decodeList<AttributeDto>()
                        .map { it.toAttributeItem() }
                }

                LibraryEnum.CAREER -> {
                    supabaseList
                        .decodeList<CareerDto>()
                        .map { it.toCareerItem() }
                }

                LibraryEnum.CAREER_PATH -> {
                    supabaseList
                        .decodeList<CareerPathDto>()
                        .map { it.toCareerPathItem() }
                }

                LibraryEnum.CLASS -> {
                    supabaseList
                        .decodeList<ClassDto>()
                        .map { it.toClassItem() }
                }

                LibraryEnum.ITEM -> {
                    supabaseList
                        .decodeList<ItemDto>()
                        .map { it.toItemItem() }
                }

                LibraryEnum.QUALITY_FLAW -> {
                    supabaseList
                        .decodeList<QualityFlawDto>()
                        .map { it.toQualityFlawItem() }
                }

                LibraryEnum.SKILL -> {
                    supabaseList
                        .decodeList<SkillDto>()
                        .map { it.toSkillItem() }
                }

                LibraryEnum.SPECIES -> {
                    supabaseList
                        .decodeList<SpeciesDto>()
                        .map { it.toSpeciesItem() }
                }

                LibraryEnum.TALENT -> {
                    supabaseList
                        .decodeList<TalentDto>()
                        .map { it.toTalentItem() }
                }

            }
        } catch (e: Exception) {
            println("Error fetching library list: ${e.message}")
            handleException(e)
        }

    }

    override suspend fun getLibraryItem(
        itemId: String,
        fromTable: LibraryEnum
    ): LibraryItem {
        println("KtorLibraryClient.getLibraryItem")
        return try {
            libraryList = getLibraryList(fromTable)
            libraryList.find { it.id == itemId }
                ?: throw LibraryException(LibraryError.UNKNOWN_ERROR)
        } catch (e: Exception) {
            handleException(e)
        }
    }
}
