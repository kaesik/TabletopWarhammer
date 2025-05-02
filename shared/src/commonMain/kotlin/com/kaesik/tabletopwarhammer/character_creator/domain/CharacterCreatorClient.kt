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

    suspend fun getSpecies(): List<SpeciesItem>

    suspend fun getSpeciesDetails(
        speciesName: String
    ): SpeciesItem

    suspend fun getClasses(): List<ClassItem>

    suspend fun getClassesDetails(
        className: String
    ): ClassItem

    suspend fun getCareers(
        speciesName: String,
        className: String
    ): List<CareerItem>

    suspend fun getCareerDetails(
        careerName: String
    ): CareerItem

    suspend fun getCareerPath(
        pathName: String
    ): CareerPathItem

    suspend fun getAttributes(): List<AttributeItem>

    suspend fun getAttributesDetails(
        attributeName: String
    ): AttributeItem

    suspend fun getSkills(
        speciesName: String,
        careerPathName: String
    ): List<List<SkillItem>>

    suspend fun getSkillsDetails(
        skillName: String
    ): SkillItem

    suspend fun getTalents(
        speciesName: String,
        careerPathName: String
    ): List<List<TalentItem>>

    suspend fun getTalentsDetails(
        talentName: String
    ): TalentItem

    suspend fun getTrappings(
        className: String,
        careerPathName: String
    ): List<List<String>>

    suspend fun getTrappingsDetails(
        className: String,
        careerPathName: String
    ): ItemItem
}
