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

    override suspend fun getAttributes(): List<AttributeItem> {
        println("KtorLibraryClient.getAttributes")
        return try {
            supabaseClient
                .from("attribute")
                .select()
                .decodeList<AttributeDto>()
                .map { it.toAttributeItem() }

        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun getCareers(): List<CareerItem> {
        println("KtorLibraryClient.getCareers")
        return try {
            supabaseClient
                .from("career")
                .select()
                .decodeList<CareerDto>()
                .map { it.toCareerItem() }

        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun getCareerPaths(): List<CareerPathItem> {
        println("KtorLibraryClient.getCareerPaths")
        return try {
            supabaseClient
                .from("careerpath")
                .select()
                .decodeList<CareerPathDto>()
                .map { it.toCareerPathItem() }

        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun getClasses(): List<ClassItem> {
        println("KtorLibraryClient.getClasses")
        return try {
            supabaseClient
                .from("class")
                .select()
                .decodeList<ClassDto>()
                .map { it.toClassItem() }
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun getItems(): List<ItemItem> {
        println("KtorLibraryClient.getItems")
        return try {
            supabaseClient
                .from("item")
                .select()
                .decodeList<ItemDto>()
                .map { it.toItemItem() }
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun getQualitiesFlaws(): List<QualityFlawItem> {
        println("KtorLibraryClient.getQualitiesFlaws")
        return try {
            supabaseClient
                .from("qualityflaw")
                .select()
                .decodeList<QualityFlawDto>()
                .map { it.toQualityFlawItem() }
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun getSkills(): List<SkillItem> {
        println("KtorLibraryClient.getSkills")
        return try {
            supabaseClient
                .from("skill")
                .select()
                .decodeList<SkillDto>()
                .map { it.toSkillItem() }
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun getSpecies(): List<SpeciesItem> {
        println("KtorLibraryClient.getSpecies")
        return try {
            supabaseClient
                .from("species")
                .select()
                .decodeList<SpeciesDto>()
                .map { it.toSpeciesItem() }
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun getTalents(): List<TalentItem> {
        println("KtorLibraryClient.getTalents")
        return try {
            supabaseClient
                .from("talent")
                .select()
                .decodeList<TalentDto>()
                .map { it.toTalentItem() }
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun getLibraryItem(id: String, libraryList: List<LibraryItem>): LibraryItem {
        println("KtorLibraryClient.getLibraryItem")
        return try {
            libraryList.find { it.id == id }
                ?: throw LibraryException(LibraryError.UNKNOWN_ERROR)
        } catch (e: Exception) {
            handleException(e)
        }
    }
}
