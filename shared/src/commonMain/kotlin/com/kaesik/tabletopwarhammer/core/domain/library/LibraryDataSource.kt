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
import com.kaesik.tabletopwarhammer.core.domain.util.CommonFlow
import kotlin.coroutines.CoroutineContext

interface LibraryDataSource {
    fun getAllAttributes(): List<AttributeItem>
    fun getAttribute(context: CoroutineContext): CommonFlow<List<AttributeItem>>

    fun getAllCareers(): List<CareerItem>
    fun getCareer(context: CoroutineContext): CommonFlow<List<CareerItem>>

    fun getAllCareerPaths(): List<CareerPathItem>
    fun getCareerPath(context: CoroutineContext): CommonFlow<List<CareerPathItem>>

    fun getAllClasses(): List<ClassItem>
    fun getClass(context: CoroutineContext): CommonFlow<List<ClassItem>>

    fun getAllItems(): List<ItemItem>
    fun getItem(context: CoroutineContext): CommonFlow<List<ItemItem>>

    fun getAllQualityFlaws(): List<QualityFlawItem>
    fun getQualityFlaw(context: CoroutineContext): CommonFlow<List<QualityFlawItem>>

    fun getAllSkills(): List<SkillItem>
    fun getSkill(context: CoroutineContext): CommonFlow<List<SkillItem>>

    fun getAllSpecies(): List<SpeciesItem>
    fun getSpecies(context: CoroutineContext): CommonFlow<List<SpeciesItem>>

    fun getAllTalents(): List<TalentItem>
    fun getTalent(context: CoroutineContext): CommonFlow<List<TalentItem>>
}
