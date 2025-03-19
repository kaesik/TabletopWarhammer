package com.kaesik.tabletopwarhammer.library.data.library

import com.kaesik.tabletopwarhammer.library.data.Const
import com.kaesik.tabletopwarhammer.library.data.library.dto.AttributeDto
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryClient
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryError
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryException
import com.kaesik.tabletopwarhammer.library.domain.library.items.AttributeItem
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.HttpRequestTimeoutException
import com.kaesik.tabletopwarhammer.library.data.library.mappers.toAttributeItem
import com.kaesik.tabletopwarhammer.library.data.library.dto.CareerDto
import com.kaesik.tabletopwarhammer.library.data.library.dto.CareerPathDto
import com.kaesik.tabletopwarhammer.library.data.library.dto.ClassDto
import com.kaesik.tabletopwarhammer.library.data.library.dto.ItemDto
import com.kaesik.tabletopwarhammer.library.data.library.dto.QualityFlawDto
import com.kaesik.tabletopwarhammer.library.data.library.dto.SkillDto
import com.kaesik.tabletopwarhammer.library.data.library.dto.SpeciesDto
import com.kaesik.tabletopwarhammer.library.data.library.dto.TalentDto
import com.kaesik.tabletopwarhammer.library.data.library.mappers.toCareerItem
import com.kaesik.tabletopwarhammer.library.data.library.mappers.toCareerPathItem
import com.kaesik.tabletopwarhammer.library.data.library.mappers.toClassItem
import com.kaesik.tabletopwarhammer.library.data.library.mappers.toItemItem
import com.kaesik.tabletopwarhammer.library.data.library.mappers.toQualityFlawItem
import com.kaesik.tabletopwarhammer.library.data.library.mappers.toSkillItem
import com.kaesik.tabletopwarhammer.library.data.library.mappers.toSpeciesItem
import com.kaesik.tabletopwarhammer.library.data.library.mappers.toTalentItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.CareerPathItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.ClassItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.ItemItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.QualityFlawItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.SpeciesItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.TalentItem

class KtorLibraryClient : LibraryClient {
    private val supabaseClient = createSupabaseClient(
        supabaseKey = Const.SUPABASE_ANON_KEY,
        supabaseUrl = Const.SUPABASE_URL
    ) {
        install(Postgrest)
    }

    override suspend fun getLibraryList(
        fromTable: String
    ): List<LibraryItem> {
        return try {
            val supabaseList = supabaseClient
                .from(fromTable)
                .select()
            when (fromTable) {
                "attribute" -> {
                    supabaseList
                        .decodeList<AttributeDto>()
                        .map { it.toAttributeItem() }
                }
                "career" -> {
                    supabaseList
                        .decodeList<CareerDto>()
                        .map { it.toCareerItem() }
                }
                "careerpath" -> {
                    supabaseList
                        .decodeList<CareerPathDto>()
                        .map { it.toCareerPathItem() }
                }
                "class" -> {
                    supabaseList
                        .decodeList<ClassDto>()
                        .map { it.toClassItem() }
                }
                "item" -> {
                    supabaseList
                        .decodeList<ItemDto>()
                        .map { it.toItemItem() }
                }
                "qualityflaw" -> {
                    supabaseList
                        .decodeList<QualityFlawDto>()
                        .map { it.toQualityFlawItem() }
                }
                "skill" -> {
                    supabaseList
                        .decodeList<SkillDto>()
                        .map { it.toSkillItem() }
                }
                "species" -> {
                    supabaseList
                        .decodeList<SpeciesDto>()
                        .map { it.toSpeciesItem() }
                }
                "talent" -> {
                    supabaseList
                        .decodeList<TalentDto>()
                        .map { it.toTalentItem() }
                }
                else -> {
                    throw LibraryException(LibraryError.UNKNOWN_ERROR)
                }
            }
        } catch (e: Exception) {
            handleException(e)
        }

    }

    override suspend fun getLibraryItem(
        id: String,
        libraryList: List<LibraryItem>
    ): LibraryItem {
        println("KtorLibraryClient.getLibraryItem")
        return try {
            libraryList.find { it.id == id }
                ?: throw LibraryException(LibraryError.UNKNOWN_ERROR)
        } catch (e: Exception) {
            handleException(e)
        }
    }
}
