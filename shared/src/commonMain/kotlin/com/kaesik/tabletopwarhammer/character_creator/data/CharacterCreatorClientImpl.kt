package com.kaesik.tabletopwarhammer.character_creator.data

import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.core.data.handleException
import com.kaesik.tabletopwarhammer.core.domain.library.LibraryLocalDataSource
import com.kaesik.tabletopwarhammer.core.domain.library.items.AttributeItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerPathItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ClassItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ItemItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem
import kotlin.coroutines.cancellation.CancellationException

class CharacterCreatorClientImpl(
    private val local: LibraryLocalDataSource
) : CharacterCreatorClient {

    override suspend fun getAllSpecies(): List<SpeciesItem> {
        return try {
            local.getAllSpecies()
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun getSpeciesDetails(speciesName: String): SpeciesItem {
        return try {
            local.getSpecies(speciesName)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun getAllClasses(): List<ClassItem> {
        return try {
            local.getAllClasses()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun getClassesDetails(className: String): ClassItem {
        return try {
            local.getClass(className)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun getFilteredCareers(
        speciesName: String,
        className: String
    ): List<CareerItem> {
        return try {
            val careerList = local.getFilteredCareers(
                speciesName = speciesName,
                className = className
            )
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
            local.getCareer(careerName)
        } catch (e: Exception) {
            println("Error fetching career details: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getCareerPath(pathName: String): CareerPathItem {
        return try {
            local.getCareerPath(pathName)
        } catch (e: Exception) {
            println("Error fetching career path: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getAllAttributes(): List<AttributeItem> {
        return try {
            val attributeList = local.getAllAttributes()
            println("attributeList $attributeList")
            attributeList
        } catch (e: Exception) {
            println("Error fetching attributes list: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getAttributesDetails(attributeName: String): AttributeItem {
        return try {
            local.getAttribute(attributeName)
        } catch (e: Exception) {
            println("Error fetching attribute details: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getBasicSkills(): List<SkillItem> {
        return try {
            local.getBasicSkills()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun getSkillSpecializations(skillNameOrBase: String): List<String> {
        return try {
            local.getSkillSpecializations(skillNameOrBase)
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
            local.getFilteredSkills(
                speciesName = speciesName,
                careerPathName = careerPathName
            )
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
            local.getSkill(skillName)
        } catch (e: Exception) {
            println("Error fetching skill details: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getFilteredTalents(
        speciesName: String,
        careerPathName: String
    ): List<List<List<TalentItem>>> {
        return try {
            local.getFilteredTalents(
                speciesName = speciesName,
                careerPathName = careerPathName
            )
        } catch (e: Exception) {
            println("Error fetching talents: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getTalentSpecializations(talentNameOrBase: String): List<String> {
        return try {
            local.getTalentSpecializations(talentNameOrBase)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun getTalentsDetails(talentName: String): TalentItem {
        return try {
            local.getTalent(talentName)
        } catch (e: Exception) {
            println("Error fetching talent details: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getTrappings(
        className: String,
        careerPathName: String
    ): List<List<ItemItem>> {
        return try {
            local.getTrappings(
                className = className,
                careerPathName = careerPathName
            )
        } catch (e: Exception) {
            println("Error fetching trappings: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getTrappingsDetails(
        className: String,
        careerPathName: String
    ): ItemItem {
        throw NotImplementedError("getTrappingsDetails is not implemented")
    }

    override suspend fun getWealth(careerPathName: String): List<Int> {
        return try {
            local.getWealth(careerPathName)
        } catch (e: Exception) {
            handleException(e)
        }
    }
}
