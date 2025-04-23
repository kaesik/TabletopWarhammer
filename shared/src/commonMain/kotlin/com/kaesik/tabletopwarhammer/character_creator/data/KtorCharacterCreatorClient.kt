package com.kaesik.tabletopwarhammer.character_creator.data

import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.core.data.library.dto.AttributeDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.CareerDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.CareerPathDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.ClassDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.SkillDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.SpeciesDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.TalentDto
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toAttributeItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toCareerItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toClassItem
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
        return try {
            val speciesList = supabaseClient
                .from(LibraryEnum.SPECIES.tableName)
                .select()
                .decodeList<SpeciesDto>()
                .map { it.toSpeciesItem() }
            println("speciesList $speciesList")
            speciesList
        } catch (e: Exception) {
            println("Error fetching species list: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getSpeciesDetails(speciesName: String): SpeciesItem {
        return try {
            supabaseClient
                .from(LibraryEnum.SPECIES.tableName)
                .select {
                    filter {
                        ilike("name", "%$speciesName%")
                    }
                }
                .decodeSingle<SpeciesDto>()
                .toSpeciesItem()
        } catch (e: Exception) {
            println("Error fetching species list: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getClasses(): List<ClassItem> {
        return try {
            val classList = supabaseClient
                .from(LibraryEnum.CLASS.tableName)
                .select()
                .decodeList<ClassDto>()
                .map { it.toClassItem() }
            println("classList $classList")
            classList
        } catch (e: Exception) {
            println("Error fetching classes list: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getClassesDetails(className: String): ClassItem {
        return try {
            supabaseClient
                .from(LibraryEnum.CLASS.tableName)
                .select {
                    filter {
                        ilike("name", "%$className%")
                    }
                }
                .decodeSingle<ClassDto>()
                .toClassItem()
        } catch (e: Exception) {
            println("Error fetching species list: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getCareers(
        speciesName: String,
        className: String
    ): List<CareerItem> {
        return try {
            val careerList = supabaseClient
                .from(LibraryEnum.CAREER.tableName)
                .select {
                    filter {
                        ilike("class_name", "%${className}%")
                        ilike("limitations", "%${speciesName}%")
                    }
                }
                .decodeList<CareerDto>()
                .map { it.toCareerItem() }
            println("careerList $careerList")
            careerList
        } catch (e: Exception) {
            println("Error fetching careers list: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getCareerDetails(
        careerName: String
    ): CareerItem {
        return try {
            supabaseClient
                .from(LibraryEnum.CAREER.tableName)
                .select {
                    filter {
                        ilike("name", "%$careerName%")
                    }
                }
                .decodeSingle<CareerDto>()
                .toCareerItem()
        } catch (e: Exception) {
            println("Error fetching species list: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getAttributes(): List<AttributeItem> {
        return try {
            val attributeList = supabaseClient
                .from(LibraryEnum.ATTRIBUTE.tableName)
                .select()
                .decodeList<AttributeDto>()
                .map { it.toAttributeItem() }
            println("attributeList $attributeList")
            attributeList
        } catch (e: Exception) {
            println("Error fetching attributes list: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getAttributesDetails(
        attributeName: String,
    ): AttributeItem {
        return try {
            supabaseClient
                .from(LibraryEnum.ATTRIBUTE.tableName)
                .select {
                    filter {
                        ilike("name", "%$attributeName%")
                    }
                }
                .decodeSingle<AttributeDto>()
                .toAttributeItem()
        } catch (e: Exception) {
            println("Error fetching species list: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getSkills(
        speciesName: String,
        careerName: String,
        careerPathName: String
    ): List<List<String>> {
        return try {
            val speciesResult = supabaseClient
                .from(LibraryEnum.SPECIES.tableName)
                .select {
                    filter { ilike("name", "%$speciesName%") }
                }
                .decodeSingle<SpeciesDto>()

            val careerPathResult = supabaseClient
                .from(LibraryEnum.CAREER_PATH.tableName)
                .select {
                    filter { ilike("name", "%$careerPathName%") }
                }
                .decodeSingle<CareerPathDto>()

            fun extract(raw: String?): List<String> {
                return raw
                    ?.split(",")
                    ?.map { it.trim() }
                    ?.filter { it.isNotEmpty() }
                    ?: emptyList()
            }

            val speciesSkills = extract(speciesResult.skills)
            val careerSkills = extract(careerPathResult.skills)

            listOf(speciesSkills, careerSkills)

        } catch (e: Exception) {
            println("Error fetching skills: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getSkillsDetails(
        skillName: String
    ): SkillItem {
        return try {
            supabaseClient
                .from(LibraryEnum.SKILL.tableName)
                .select {
                    filter {
                        ilike("name", "%$skillName%")
                    }
                }
                .decodeSingle<SkillDto>()
                .toSkillItem()
        } catch (e: Exception) {
            println("Error fetching species list: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getTalents(
        speciesName: String,
        careerName: String,
        careerPathName: String
    ): List<List<String>> {
        return try {
            val speciesResult = supabaseClient
                .from(LibraryEnum.SPECIES.tableName)
                .select {
                    filter { ilike("name", "%$speciesName%") }
                }
                .decodeSingle<SpeciesDto>()

            val careerPathResult = supabaseClient
                .from(LibraryEnum.CAREER_PATH.tableName)
                .select {
                    filter { ilike("name", "%$careerPathName%") }
                }
                .decodeSingle<CareerPathDto>()

            fun extract(raw: String?): List<String> {
                return raw
                    ?.split(",")
                    ?.map { it.trim() }
                    ?.filter { it.isNotEmpty() }
                    ?: emptyList()
            }

            val speciesTalents = extract(speciesResult.talents)
            val careerTalents = extract(careerPathResult.talents)

            listOf(speciesTalents, careerTalents)

        } catch (e: Exception) {
            println("Error fetching talents: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getTalentsDetails(
        talentName: String
    ): TalentItem {
        return try {
            supabaseClient
                .from(LibraryEnum.TALENT.tableName)
                .select {
                    filter {
                        ilike("name", "%$talentName%")
                    }
                }
                .decodeSingle<TalentDto>()
                .toTalentItem()
        } catch (e: Exception) {
            println("Error fetching species list: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getTrappings(
        className: String,
        careerPathName: String
    ): List<List<String>> {
        return try {
            val classResult = supabaseClient
                .from(LibraryEnum.CLASS.tableName)
                .select {
                    filter { ilike("name", "%$className%") }
                }
                .decodeSingle<ClassDto>()

            val careerPathResult = supabaseClient
                .from(LibraryEnum.CAREER_PATH.tableName)
                .select {
                    filter { ilike("name", "%$careerPathName%") }
                }
                .decodeSingle<CareerPathDto>()

            fun extractTrappings(raw: String?): List<String> {
                return raw
                    ?.replace(" and ", ",")
                    ?.split(",")
                    ?.map { it.trim() }
                    ?.filter { it.isNotEmpty() }
                    ?: emptyList()
            }

            val classTrappings = extractTrappings(classResult.trappings)
            val careerTrappings = extractTrappings(careerPathResult.trappings)

            listOf(classTrappings, careerTrappings)

        } catch (e: Exception) {
            println("Error fetching trappings: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getTrappingsDetails(
        className: String,
        careerPathName: String
    ): ItemItem {
        TODO("Not yet implemented")
    }
}
