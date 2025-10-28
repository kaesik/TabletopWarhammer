package com.kaesik.tabletopwarhammer.core.domain.library

import com.kaesik.tabletopwarhammer.core.domain.library.items.AttributeItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerPathItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ClassItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ItemItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.QualityFlawItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem

interface LibraryDataSource {
    fun getAllAttributes(): List<AttributeItem>
    fun getAttribute(attributeName: String): AttributeItem
    suspend fun insertAttribute(item: AttributeItem)

    fun getAllCareers(): List<CareerItem>
    fun getFilteredCareers(
        speciesName: String,
        className: String
    ): List<CareerItem>

    fun getCareer(careerName: String): CareerItem
    suspend fun insertCareer(item: CareerItem)

    fun getAllCareerPaths(): List<CareerPathItem>
    fun getCareerPath(pathName: String): CareerPathItem
    suspend fun insertCareerPath(item: CareerPathItem)

    fun getAllClasses(): List<ClassItem>
    fun getClass(className: String): ClassItem
    suspend fun insertClass(item: ClassItem)

    fun getAllItems(): List<ItemItem>
    fun getItem(itemName: String): ItemItem
    fun getTrappings(
        className: String,
        careerPathName: String
    ): List<List<ItemItem>>

    suspend fun insertItem(item: ItemItem)

    fun getAllQualityFlaws(): List<QualityFlawItem>
    fun getQualityFlaw(name: String): QualityFlawItem
    suspend fun insertQualityFlaw(item: QualityFlawItem)

    fun getAllSkills(): List<SkillItem>
    fun getBasicSkills(): List<SkillItem>
    fun getSkillSpecializations(skillNameOrBase: String): List<String>
    fun getFilteredSkills(
        speciesName: String,
        careerPathName: String
    ): List<List<SkillItem>>


    fun getSkill(skillName: String): SkillItem
    suspend fun insertSkill(item: SkillItem)

    fun getAllSpecies(): List<SpeciesItem>
    fun getSpecies(speciesName: String): SpeciesItem
    suspend fun insertSpecies(item: SpeciesItem)

    fun getAllTalents(): List<TalentItem>
    fun getTalentSpecializations(talentNameOrBase: String): List<String>
    fun getFilteredTalents(
        speciesName: String,
        careerPathName: String
    ): List<List<List<TalentItem>>>

    fun getTalent(talentName: String): TalentItem
    suspend fun insertTalent(item: TalentItem)

    fun getWealth(careerPathName: String): List<Int>
}
