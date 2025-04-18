package com.kaesik.tabletopwarhammer.character_creator.data

import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.core.data.library.dto.AttributeDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.CareerDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.ClassDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.ItemDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.SkillDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.SpeciesDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.TalentDto
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toAttributeItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toCareerItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toClassItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toItemItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toSkillItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toSpeciesItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toTalentItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.AttributeItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ClassItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ItemItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem
import com.kaesik.tabletopwarhammer.library.data.Const
import com.kaesik.tabletopwarhammer.library.data.library.handleException
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from

class KtorCharacterCreatorClient : CharacterCreatorClient {
    private val supabaseClient = createSupabaseClient(
        supabaseKey = Const.SUPABASE_ANON_KEY,
        supabaseUrl = Const.SUPABASE_URL
    ) {
        install(Postgrest)
    }

    override suspend fun getSpecies(): List<SpeciesItem> {
        val supabaseList = supabaseClient.from(LibraryEnum.SPECIES.tableName).select()
        return try {
            supabaseList
                .decodeList<SpeciesDto>()
                .map { it.toSpeciesItem() }
        } catch (e: Exception) {
            println("Error fetching species list: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getClasses(): List<ClassItem> {
        val supabaseList = supabaseClient.from(LibraryEnum.CLASS.tableName).select()
        val classList = supabaseList
            .decodeList<ClassDto>()
            .map { it.toClassItem() }
        println("classList $classList")
        return try {
            classList
        } catch (e: Exception) {
            println("Error fetching classes list: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getCareers(): List<CareerItem> {
        val supabaseList = supabaseClient.from(LibraryEnum.CAREER.tableName).select()
        val careerList = supabaseList
            .decodeList<CareerDto>()
            .map { it.toCareerItem() }
        println("careerList $careerList")
        return try {
            careerList
        } catch (e: Exception) {
            println("Error fetching careers list: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getAttributes(): List<AttributeItem> {
        val supabaseList = supabaseClient.from(LibraryEnum.ATTRIBUTE.tableName).select()
        return try {
            supabaseList
                .decodeList<AttributeDto>()
                .map { it.toAttributeItem() }
        } catch (e: Exception) {
            println("Error fetching attributes list: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getSkills(): List<SkillItem> {
        val supabaseList = supabaseClient.from(LibraryEnum.SKILL.tableName).select()
        return try {
            supabaseList
                .decodeList<SkillDto>()
                .map { it.toSkillItem() }
        } catch (e: Exception) {
            println("Error fetching skills list: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getTrappings(): List<ItemItem> {
        val supabaseList = supabaseClient.from(LibraryEnum.ITEM.tableName).select()
        return try {
            supabaseList
                .decodeList<ItemDto>()
                .map { it.toItemItem() }
        } catch (e: Exception) {
            println("Error fetching trappings list: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getTalents(): List<TalentItem> {
        val supabaseList = supabaseClient.from(LibraryEnum.TALENT.tableName).select()
        return try {
            supabaseList
                .decodeList<TalentDto>()
                .map { it.toTalentItem() }
        } catch (e: Exception) {
            println("Error fetching talents list: ${e.message}")
            handleException(e)
        }
    }
}
