package com.kaesik.tabletopwarhammer.library.domain.library

import com.kaesik.tabletopwarhammer.library.domain.library.items.AttributeItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.CareerPathItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.ClassItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.ItemItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.QualityFlawItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.SpeciesItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.TalentItem

interface LibraryClient {
    suspend fun getAttributes(): List<AttributeItem>
    suspend fun getCareers(): List<CareerItem>
    suspend fun getCareerPaths(): List<CareerPathItem>
    suspend fun getClasses(): List<ClassItem>
    suspend fun getItems(): List<ItemItem>
    suspend fun getQualitiesFlaws(): List<QualityFlawItem>
    suspend fun getSkills(): List<SkillItem>
    suspend fun getSpecies(): List<SpeciesItem>
    suspend fun getTalents(): List<TalentItem>
    suspend fun getLibraryItem(id: String, libraryList: List<LibraryItem>): LibraryItem
}
