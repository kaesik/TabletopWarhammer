package com.kaesik.tabletopwarhammer.character_creator.domain

import com.kaesik.tabletopwarhammer.core.domain.library.items.AttributeItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerPathItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ClassItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ItemItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem

interface CharacterCreatorClient {

    // SPECIES
    suspend fun getAllSpecies(): List<SpeciesItem>

    suspend fun getSpeciesDetails(
        speciesName: String
    ): SpeciesItem

    // CLASS & CAREER
    suspend fun getAllClasses(): List<ClassItem>

    suspend fun getClassesDetails(
        className: String
    ): ClassItem

    suspend fun getFilteredCareers(
        speciesName: String,
        className: String
    ): List<CareerItem>

    suspend fun getCareerDetails(
        careerName: String
    ): CareerItem

    suspend fun getCareerPath(
        pathName: String
    ): CareerPathItem

    // ATTRIBUTES
    suspend fun getAllAttributes(): List<AttributeItem>

    suspend fun getAttributesDetails(
        attributeName: String
    ): AttributeItem

    // SKILLS
    suspend fun getBasicSkills(): List<SkillItem>

    suspend fun getSkillSpecializations(skillNameOrBase: String): List<String>

    suspend fun getFilteredSkills(
        speciesName: String,
        careerPathName: String
    ): List<List<SkillItem>>

    suspend fun getSkillsDetails(
        skillName: String
    ): SkillItem

    // TALENTS
    suspend fun getFilteredTalents(
        speciesName: String,
        careerPathName: String
    ): List<List<List<TalentItem>>>

    suspend fun getTalentSpecializations(talentNameOrBase: String): List<String>

    suspend fun getTalentsDetails(
        talentName: String
    ): TalentItem

    // TRAPPINGS
    suspend fun getTrappings(
        className: String,
        careerPathName: String
    ): List<List<ItemItem>>

    suspend fun getTrappingsDetails(
        className: String,
        careerPathName: String
    ): ItemItem

    // WEALTH
    suspend fun getWealth(
        careerPathName: String
    ): List<Int>
}
