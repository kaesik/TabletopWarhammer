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

    override suspend fun insertAttribute(item: AttributeItem) {
        queries.insertAttributeEntity(
            id = item.id,
            name = item.name,
            description = item.description,
            shortName = item.shortName,
            page = item.page?.toLong()
        )
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

    override suspend fun insertCareer(item: CareerItem) {
        queries.insertCareerEntity(
            id = item.id,
            name = item.name,
            limitations = item.limitations,
            description = item.description,
            advanceScheme = item.advanceScheme,
            quotations = item.quotations,
            adventuring = item.adventuring,
            source = item.source,
            careerPath = item.careerPath,
            className = item.className,
            page = item.page?.toLong()
        )
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

    override suspend fun insertCareerPath(item: CareerPathItem) {
        queries.insertCareerPathEntity(
            id = item.id,
            name = item.name,
            status = item.status,
            skills = item.skills,
            trappings = item.trappings,
            talents = item.talents
        )
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

    override suspend fun insertClass(item: ClassItem) {
        queries.insertClassEntity(
            id = item.id,
            name = item.name,
            description = item.description,
            trappings = item.trappings,
            careers = item.careers,
            page = item.page?.toLong()
        )
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

    override suspend fun insertItem(item: ItemItem) {
        queries.insertItemEntity(
            id = item.id,
            name = item.name,
            group = item.group,
            source = item.source,
            ap = item.ap,
            availability = item.availability,
            carries = item.carries,
            damage = item.damage,
            description = item.description,
            encumbrance = item.encumbrance,
            isTwoHanded = if (item.isTwoHanded == true) 1 else 0,
            locations = item.locations,
            penalty = item.penalty,
            price = item.price,
            qualitiesAndFlaws = item.qualitiesAndFlaws,
            quantity = item.quantity,
            range = item.range,
            meeleRanged = item.meeleRanged,
            type = item.type,
            page = item.page?.toLong()
        )
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

    override suspend fun insertQualityFlaw(item: QualityFlawItem) {
        queries.insertQualityFlawEntity(
            id = item.id,
            name = item.name,
            group = item.group,
            description = item.description,
            isQuality = if (item.isQuality == true) 1 else 0,
            source = item.source,
            page = item.page?.toLong()
        )
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

    override suspend fun insertSkill(item: SkillItem) {
        queries.insertSkillEntity(
            id = item.id,
            name = item.name,
            attribute = item.attribute,
            isBasic = if (item.isBasic == true) 1 else 0,
            isGrouped = if (item.isGrouped == true) 1 else 0,
            description = item.description,
            specialization = item.specialization,
            source = item.source,
            page = item.page?.toLong()
        )
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

    override suspend fun insertSpecies(item: SpeciesItem) {
        queries.insertSpeciesEntity(
            id = item.id,
            name = item.name,
            description = item.description,
            opinions = item.opinions,
            source = item.source,
            weaponSkill = item.weaponSkill,
            ballisticSkill = item.ballisticSkill,
            strength = item.strength,
            toughness = item.toughness,
            agility = item.agility,
            dexterity = item.dexterity,
            intelligence = item.intelligence,
            willpower = item.willpower,
            fellowship = item.fellowship,
            wounds = item.wounds,
            fatePoints = item.fatePoints,
            resilience = item.resilience,
            extraPoints = item.extraPoints,
            movement = item.movement,
            skills = item.skills,
            talents = item.talents,
            forenames = item.forenames,
            surnames = item.surnames,
            clans = item.clans,
            epithets = item.epithets,
            age = item.age,
            eyeColour = item.eyeColour,
            hairColour = item.hairColour,
            height = item.height,
            initiative = item.initiative,
            page = item.page?.toLong(),
            names = item.names
        )
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

    override suspend fun insertTalent(item: TalentItem) {
        return queries.insertTalentEntity(
            id = item.id,
            name = item.name,
            max = item.max,
            tests = item.tests,
            description = item.description,
            source = item.source,
            page = item.page?.toLong()
        )
    }
}
