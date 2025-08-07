package com.kaesik.tabletopwarhammer.core.data.library

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toAttributeItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toCareerItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toCareerPathItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toClassItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toItemItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toQualityFlawItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toSkillItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toSpeciesItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toTalentItem
import com.kaesik.tabletopwarhammer.core.domain.library.LibraryDataSource
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
import com.kaesik.tabletopwarhammer.core.domain.util.toCommonFlow
import com.kaesik.tabletopwarhammer.database.TabletopWarhammerDatabase
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class SqlDelightLibraryDataSource(
    database: TabletopWarhammerDatabase
) : LibraryDataSource {

    private val queries = database.tabletopQueries

    override fun getAllAttributes(): List<AttributeItem> {
        return queries.getAttributeEntity()
            .executeAsList()
            .map { it.toAttributeItem() }
    }

    override fun getAttribute(context: CoroutineContext): CommonFlow<List<AttributeItem>> {
        return queries.getAttributeEntity()
            .asFlow()
            .mapToList(context)
            .map { it.map { it.toAttributeItem() } }
            .toCommonFlow()
    }

    override fun getAllCareers(): List<CareerItem> {
        return queries.getCareerEntity()
            .executeAsList()
            .map { it.toCareerItem() }
    }

    override fun getCareer(context: CoroutineContext): CommonFlow<List<CareerItem>> {
        return queries.getCareerEntity()
            .asFlow()
            .mapToList(context)
            .map { it.map { it.toCareerItem() } }
            .toCommonFlow()
    }

    override fun getAllCareerPaths(): List<CareerPathItem> {
        return queries.getCareerPathEntity()
            .executeAsList()
            .map { it.toCareerPathItem() }
    }

    override fun getCareerPath(context: CoroutineContext): CommonFlow<List<CareerPathItem>> {
        return queries.getCareerPathEntity()
            .asFlow()
            .mapToList(context)
            .map { it.map { it.toCareerPathItem() } }
            .toCommonFlow()
    }

    override fun getAllClasses(): List<ClassItem> {
        return queries.getClassEntity()
            .executeAsList()
            .map { it.toClassItem() }
    }

    override fun getClass(context: CoroutineContext): CommonFlow<List<ClassItem>> {
        return queries.getClassEntity()
            .asFlow()
            .mapToList(context)
            .map { it.map { it.toClassItem() } }
            .toCommonFlow()
    }

    override fun getAllItems(): List<ItemItem> {
        return queries.getItemEntity()
            .executeAsList()
            .map { it.toItemItem() }
    }

    override fun getItem(context: CoroutineContext): CommonFlow<List<ItemItem>> {
        return queries.getItemEntity()
            .asFlow()
            .mapToList(context)
            .map { it.map { it.toItemItem() } }
            .toCommonFlow()
    }

    override fun getAllQualityFlaws(): List<QualityFlawItem> {
        return queries.getQualityFlawEntity()
            .executeAsList()
            .map { it.toQualityFlawItem() }
    }

    override fun getQualityFlaw(context: CoroutineContext): CommonFlow<List<QualityFlawItem>> {
        return queries.getQualityFlawEntity()
            .asFlow()
            .mapToList(context)
            .map { it.map { it.toQualityFlawItem() } }
            .toCommonFlow()
    }

    override fun getAllSkills(): List<SkillItem> {
        return queries.getSkillEntity()
            .executeAsList()
            .map { it.toSkillItem() }
    }

    override fun getSkill(context: CoroutineContext): CommonFlow<List<SkillItem>> {
        return queries.getSkillEntity()
            .asFlow()
            .mapToList(context)
            .map { it.map { it.toSkillItem() } }
            .toCommonFlow()
    }

    override fun getAllSpecies(): List<SpeciesItem> {
        return queries.getSpeciesEntity()
            .executeAsList()
            .map { it.toSpeciesItem() }
    }

    override fun getSpecies(context: CoroutineContext): CommonFlow<List<SpeciesItem>> {
        return queries.getSpeciesEntity()
            .asFlow()
            .mapToList(context)
            .map { it.map { it.toSpeciesItem() } }
            .toCommonFlow()
    }

    override fun getAllTalents(): List<TalentItem> {
        return queries.getTalentEntity()
            .executeAsList()
            .map { it.toTalentItem() }
    }

    override fun getTalent(context: CoroutineContext): CommonFlow<List<TalentItem>> {
        return queries.getTalentEntity()
            .asFlow()
            .mapToList(context)
            .map { it.map { it.toTalentItem() } }
            .toCommonFlow()
    }
}
