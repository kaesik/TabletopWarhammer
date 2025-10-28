package com.kaesik.tabletopwarhammer.character_creator.data

import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.extractTalents
import com.kaesik.tabletopwarhammer.core.data.handleException
import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.core.data.library.dto.AttributeDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.CareerDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.CareerPathDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.ClassDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.ItemDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.SkillDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.SpeciesDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.TalentDto
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toAttributeItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toCareerItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toCareerPathItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toClassItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toItemItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toSkillItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toSpeciesItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toTalentItem
import com.kaesik.tabletopwarhammer.core.data.remote.SupabaseClient
import com.kaesik.tabletopwarhammer.core.domain.library.items.AttributeItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerPathItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ClassItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ItemItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem
import io.github.jan.supabase.postgrest.from
import kotlin.coroutines.cancellation.CancellationException

class CharacterCreatorClientImpl : CharacterCreatorClient {
    private val supabaseClient = SupabaseClient.supabaseClient

    // SPECIES
    override suspend fun getAllSpecies(): List<SpeciesItem> {
        return try {
            val speciesList = supabaseClient
                .from(LibraryEnum.SPECIES.tableName)
                .select()
                .decodeList<SpeciesDto>()
                .map { it.toSpeciesItem() }
            speciesList
        } catch (e: Exception) {
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
            handleException(e)
        }
    }

    // CLASS & CAREER
    override suspend fun getAllClasses(): List<ClassItem> {
        return try {
            val classList = supabaseClient
                .from(LibraryEnum.CLASS.tableName)
                .select()
                .decodeList<ClassDto>()
                .map { it.toClassItem() }
            classList
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
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
            handleException(e)
        }
    }

    override suspend fun getFilteredCareers(
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
        } catch (e: CancellationException) {
            println("Careers fetch cancelled")
            throw e
        } catch (e: Exception) {
            println("Error fetching careers list: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getCareerDetails(careerName: String): CareerItem {
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

    override suspend fun getCareerPath(pathName: String): CareerPathItem {
        return try {
            supabaseClient
                .from(LibraryEnum.CAREER_PATH.tableName)
                .select {
                    filter {
                        ilike("name", "%$pathName%")
                    }
                }
                .decodeSingle<CareerPathDto>()
                .toCareerPathItem()
        } catch (e: Exception) {
            println("Error fetching career path: ${e.message}")
            handleException(e)
        }
    }

    // ATTRIBUTES
    override suspend fun getAllAttributes(): List<AttributeItem> {
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

    override suspend fun getAttributesDetails(attributeName: String): AttributeItem {
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

    // SKILLS
    override suspend fun getBasicSkills(): List<SkillItem> {
        return try {
            supabaseClient
                .from(LibraryEnum.SKILL.tableName)
                .select {
                    filter {
                        eq("is_basic", true)
                    }
                }
                .decodeList<SkillDto>()
                .map { it.toSkillItem() }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun getSkillSpecializations(skillNameOrBase: String): List<String> {
        return try {
            val base = skillNameOrBase.substringBefore(" (").trim()
            val dto = supabaseClient
                .from(LibraryEnum.SKILL.tableName)
                .select {
                    filter { ilike("name", "%$base%") }
                    limit(1)
                }
                .decodeSingle<SkillDto>()

            dto.specialization
                ?.split(",")
                ?.map { it.trim() }
                ?.filter { it.isNotEmpty() }
                ?: emptyList()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun getFilteredSkills(
        speciesName: String,
        careerPathName: String
    ): List<List<SkillItem>> {
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

            val speciesSkillsNames = extract(speciesResult.skills)
            val careerSkillsNames = extract(careerPathResult.skills)

            val allSkillDtos = supabaseClient
                .from(LibraryEnum.SKILL.tableName)
                .select()
                .decodeList<SkillDto>()
            val allSkills = allSkillDtos.map { it.toSkillItem() }

            fun findSkill(name: String): SkillItem? {
                val baseName = name.substringBefore(" (").trim()
                val specialization = name.substringAfter(" (", "").removeSuffix(")").trim()

                val match = allSkills.find { it.name == baseName }

                return if (match != null && specialization.isNotEmpty()) {
                    match.copy(
                        name = "$baseName ($specialization)",
                        specialization = specialization,
                        isBasic = false
                    )
                } else {
                    allSkills.find { it.name == name }
                }
            }

            val speciesSkills = speciesSkillsNames.mapNotNull(::findSkill)
            val careerSkills = careerSkillsNames.mapNotNull(::findSkill)

            listOf(speciesSkills, careerSkills)
        } catch (e: CancellationException) {
            println("Skills fetch cancelled")
            throw e
        } catch (e: Exception) {
            println("Error fetching skills: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getSkillsDetails(skillName: String): SkillItem {
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

    // TALENTS
    override suspend fun getFilteredTalents(
        speciesName: String,
        careerPathName: String
    ): List<List<List<TalentItem>>> {
        return try {
            val speciesResult = supabaseClient
                .from(LibraryEnum.SPECIES.tableName)
                .select { filter { ilike("name", "%$speciesName%") } }
                .decodeSingle<SpeciesDto>()

            val careerPathResult = supabaseClient
                .from(LibraryEnum.CAREER_PATH.tableName)
                .select { filter { ilike("name", "%$careerPathName%") } }
                .decodeSingle<CareerPathDto>()

            val speciesTalentGroups = extractTalents(speciesResult.talents)
            val careerTalentGroups = extractTalents(careerPathResult.talents)

            val allTalentDtos = supabaseClient
                .from(LibraryEnum.TALENT.tableName)
                .select()
                .decodeList<TalentDto>()

            val allTalents = allTalentDtos.map { it.toTalentItem() }

            fun resolveTalentGroup(group: List<String>): List<TalentItem> {
                return group.map { rawName ->
                    allTalents.find { it.name == rawName.trim() }
                        ?: TalentItem(name = rawName.trim(), id = "")
                }
            }

            val speciesTalents = speciesTalentGroups.map(::resolveTalentGroup)
            val careerTalents = careerTalentGroups.map(::resolveTalentGroup)

            listOf(speciesTalents, careerTalents)
        } catch (e: Exception) {
            println("Error fetching talents: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getTalentSpecializations(talentNameOrBase: String): List<String> {
        return try {
            val base = talentNameOrBase.substringBefore(" (").trim()
            val dto = supabaseClient
                .from(LibraryEnum.TALENT.tableName)
                .select {
                    filter { ilike("name", "%$base%") }
                    limit(1)
                }
                .decodeSingle<TalentDto>()

            dto.specialization
                ?.split(",")
                ?.map { it.trim() }
                ?.filter { it.isNotEmpty() }
                ?: emptyList()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun getTalentsDetails(talentName: String): TalentItem {
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

    // TRAPPINGS
    override suspend fun getTrappings(
        className: String,
        careerPathName: String
    ): List<List<ItemItem>> {
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
                    .filter { it.isNotEmpty() }
            }

            println("classResult ${classResult.trappings}")
            println("careerPathResult ${careerPathResult.trappings}")
            val classTrappingNames = extractTrappings(classResult.trappings)
            val careerTrappingNames = extractTrappings(careerPathResult.trappings)

            val allItemDto = supabaseClient
                .from(LibraryEnum.ITEM.tableName)
                .select()
                .decodeList<ItemDto>()

            val allItems = allItemDto.map { it.toItemItem() }

            fun resolveItems(names: List<String>): List<ItemItem> {
                return names.map { name ->
                    allItems.find { it.name.equals(name, ignoreCase = true) }
                        ?: ItemItem(
                            id = "",
                            name = name,
                            group = null,
                            source = null,
                            ap = null,
                            availability = null,
                            carries = null,
                            damage = null,
                            description = name,
                            encumbrance = null,
                            isTwoHanded = null,
                            locations = null,
                            penalty = null,
                            price = null,
                            qualitiesAndFlaws = null,
                            quantity = null,
                            range = null,
                            meeleRanged = null,
                            type = null,
                            page = null
                        )
                }
            }

            val classTrappings = resolveItems(classTrappingNames)
            val careerTrappings = resolveItems(careerTrappingNames)

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

    // WEALTH
    override suspend fun getWealth(careerPathName: String): List<Int> {
        val careerPath = supabaseClient
            .from("careerpath")
            .select()
            .decodeList<CareerPathDto>()
            .firstOrNull { it.name == careerPathName }

        val status = careerPath?.status ?: return listOf(0, 0, 0)

        val parts = status.trim().split(" ")
        if (parts.size != 2) return listOf(0, 0, 0)

        val tier = parts[0].lowercase()
        val level = parts[1].toIntOrNull() ?: return listOf(0, 0, 0)

        return when (tier) {
            "brass" -> listOf((1..2).sumOf { (1..10).random() } * level, 0, 0)
            "silver" -> listOf(0, (1..10).sumOf { (1..10).random() } * level, 0)
            "gold" -> listOf(0, 0, level)
            else -> listOf(0, 0, 0)
        }
    }
}
