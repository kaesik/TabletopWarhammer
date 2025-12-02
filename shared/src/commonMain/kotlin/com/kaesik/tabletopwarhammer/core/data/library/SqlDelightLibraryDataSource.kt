package com.kaesik.tabletopwarhammer.core.data.library

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
import com.kaesik.tabletopwarhammer.database.TabletopWarhammerDatabase

class SqlDelightLibraryDataSource(
    database: TabletopWarhammerDatabase
) : LibraryDataSource {

    private val queries = database.tabletopQueries

    // ATTRIBUTES
    override fun getAllAttributes(): List<AttributeItem> {
        return queries.getAttributeEntity()
            .executeAsList()
            .map { it.toAttributeItem() }
    }

    override fun getAttribute(attributeName: String): AttributeItem {
        return queries.getAttributeEntityByName(attributeName)
            .executeAsOne()
            .toAttributeItem()
    }

    override suspend fun insertAttribute(item: AttributeItem) {
        queries.insertAttributeEntity(
            id = item.id,
            name = item.name,
            description = item.description,
            shortName = item.shortName,
            page = item.page?.toLong(),
            updatedAt = item.updatedAt.toString(),
            deletedAt = null
        )
    }

    // CAREERS
    override fun getAllCareers(): List<CareerItem> {
        return queries.getCareerEntity()
            .executeAsList()
            .map { it.toCareerItem() }
    }

    override fun getFilteredCareers(speciesName: String, className: String): List<CareerItem> {
        return queries.getCareerEntityFiltered(className, speciesName)
            .executeAsList()
            .map { it.toCareerItem() }
    }

    override fun getCareer(careerName: String): CareerItem {
        return queries.getCareerEntityByName(careerName)
            .executeAsOne()
            .toCareerItem()
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
            page = item.page?.toLong(),
            updatedAt = item.updatedAt.toString(),
            deletedAt = null
        )
    }

    // CAREER PATHS
    override fun getAllCareerPaths(): List<CareerPathItem> {
        return queries.getCareerPathEntity()
            .executeAsList()
            .map { it.toCareerPathItem() }
    }

    override fun getCareerPath(pathName: String): CareerPathItem {
        return queries.getCareerPathEntityByName(pathName)
            .executeAsOne()
            .toCareerPathItem()
    }

    override suspend fun insertCareerPath(item: CareerPathItem) {
        queries.insertCareerPathEntity(
            id = item.id,
            name = item.name,
            status = item.status,
            skills = item.skills,
            trappings = item.trappings,
            talents = item.talents,
            updatedAt = item.updatedAt.toString(),
            deletedAt = null
        )
    }

    // CLASSES
    override fun getAllClasses(): List<ClassItem> {
        return queries.getClassEntity()
            .executeAsList()
            .map { it.toClassItem() }
    }

    override fun getClass(className: String): ClassItem {
        return queries.getClassEntityByName(className)
            .executeAsOne()
            .toClassItem()
    }

    override suspend fun insertClass(item: ClassItem) {
        queries.insertClassEntity(
            id = item.id,
            name = item.name,
            description = item.description,
            trappings = item.trappings,
            careers = item.careers,
            page = item.page?.toLong(),
            updatedAt = item.updatedAt.toString(),
            deletedAt = null
        )
    }

    // ITEMS
    override fun getAllItems(): List<ItemItem> {
        return queries.getItemEntity()
            .executeAsList()
            .map { it.toItemItem() }
    }

    override fun getItem(itemName: String): ItemItem {
        return queries.getItemEntityByName(itemName)
            .executeAsOne()
            .toItemItem()
    }

    override fun getTrappings(
        className: String,
        careerPathName: String
    ): List<List<ItemItem>> {

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

        val classEntity = queries.getClassEntityByName(className).executeAsOne()
        val careerPathEntity = queries.getCareerPathEntityByName(careerPathName).executeAsOne()

        val classTrappingNames = extractTrappings(classEntity.trappings)
        val careerTrappingNames = extractTrappings(careerPathEntity.trappings)

        val allItems = queries.getItemEntity().executeAsList().map { it.toItemItem() }

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

        return listOf(classTrappings, careerTrappings)
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
            page = item.page?.toLong(),
            updatedAt = item.updatedAt.toString(),
            deletedAt = null
        )
    }

    // QUALITY FLAWS
    override fun getAllQualityFlaws(): List<QualityFlawItem> {
        return queries.getQualityFlawEntity()
            .executeAsList()
            .map { it.toQualityFlawItem() }
    }

    override fun getQualityFlaw(name: String): QualityFlawItem {
        return queries.getQualityFlawEntityByName(name)
            .executeAsOne()
            .toQualityFlawItem()
    }

    override suspend fun insertQualityFlaw(item: QualityFlawItem) {
        queries.insertQualityFlawEntity(
            id = item.id,
            name = item.name,
            group = item.group,
            description = item.description,
            isQuality = if (item.isQuality == true) 1 else 0,
            source = item.source,
            page = item.page?.toLong(),
            updatedAt = item.updatedAt.toString(),
            deletedAt = null
        )
    }

    // SKILLS
    override fun getAllSkills(): List<SkillItem> {
        return queries.getSkillEntity()
            .executeAsList()
            .map { it.toSkillItem() }
    }

    override fun getBasicSkills(): List<SkillItem> {
        return queries.getSkillEntity()
            .executeAsList()
            .map { it.toSkillItem() }
            .filter { it.isBasic == true }
    }

    override fun getSkillSpecializations(skillNameOrBase: String): List<String> {
        val base = skillNameOrBase.substringBefore(" (").trim()
        val entity = queries.getSkillEntityByName(base).executeAsOneOrNull()
        return entity?.specialization
            ?.split(",")
            ?.map { it.trim() }
            ?.filter { it.isNotEmpty() }
            ?: emptyList()
    }

    override fun getSkill(skillName: String): SkillItem {
        return queries.getSkillEntityByName(skillName)
            .executeAsOne()
            .toSkillItem()
    }

    override fun getFilteredSkills(
        speciesName: String,
        careerPathName: String
    ): List<List<SkillItem>> {
        fun extractSkills(raw: String?): List<String> {
            return raw
                ?.split(",")
                ?.map { it.trim() }
                ?.filter { it.isNotEmpty() }
                ?: emptyList()
        }

        val speciesEntity = queries.getSpeciesEntityByName(speciesName).executeAsOne()
        val careerPathEntity = queries.getCareerPathEntityByName(careerPathName).executeAsOne()

        val speciesSkillNames = extractSkills(speciesEntity.skills)
        val careerSkillNames = extractSkills(careerPathEntity.skills)

        val allSkills = queries.getSkillEntity().executeAsList().map { it.toSkillItem() }

        fun matchSkill(name: String): SkillItem? {
            val base = name.substringBefore(" (").trim()
            val spec = name.substringAfter(" (", "").removeSuffix(")").trim()

            val baseSkill = allSkills.find { it.name == base }
            return when {
                baseSkill != null && spec.isNotEmpty() -> baseSkill.copy(
                    name = "$base ($spec)",
                    specialization = spec,
                    isBasic = false
                )

                else -> allSkills.find { it.name == name }
            }
        }

        val speciesSkills = speciesSkillNames.mapNotNull(::matchSkill)
        val careerSkills = careerSkillNames.mapNotNull(::matchSkill)

        return listOf(speciesSkills, careerSkills)
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
            page = item.page?.toLong(),
            updatedAt = item.updatedAt.toString(),
            deletedAt = null
        )
    }

    // SPECIES
    override fun getAllSpecies(): List<SpeciesItem> {
        return queries.getSpeciesEntity()
            .executeAsList()
            .map { it.toSpeciesItem() }
    }

    override fun getSpecies(speciesName: String): SpeciesItem {
        return queries.getSpeciesEntityByName(speciesName)
            .executeAsOne()
            .toSpeciesItem()
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
            names = item.names,
            updatedAt = item.updatedAt.toString(),
            deletedAt = null
        )
    }

    // TALENTS
    override fun getAllTalents(): List<TalentItem> {
        return queries.getTalentEntity()
            .executeAsList()
            .map { it.toTalentItem() }
    }

    override fun getTalentSpecializations(talentNameOrBase: String): List<String> {
        val base = talentNameOrBase.substringBefore(" (").trim()
        val entity = queries.getTalentEntityByName(base).executeAsOneOrNull()
        return entity?.specialization
            ?.split(",")
            ?.map { it.trim() }
            ?.filter { it.isNotEmpty() }
            ?: emptyList()
    }

    override fun getFilteredTalents(
        speciesName: String,
        careerPathName: String
    ): List<List<List<TalentItem>>> {

        fun extractTalents(raw: String?): List<List<String>> {
            return raw
                ?.split(";")
                ?.map { group -> group.split(",").map { it.trim() }.filter { it.isNotEmpty() } }
                ?.filter { it.isNotEmpty() }
                ?: emptyList()
        }

        val speciesEntity = queries.getSpeciesEntityByName(speciesName).executeAsOne()
        val careerPathEntity = queries.getCareerPathEntityByName(careerPathName).executeAsOne()

        val speciesTalentGroups = extractTalents(speciesEntity.talents)
        val careerTalentGroups = extractTalents(careerPathEntity.talents)

        val allTalents = queries.getTalentEntity().executeAsList().map { it.toTalentItem() }

        fun resolveTalentGroup(group: List<String>): List<TalentItem> {
            return group.map { rawName ->
                allTalents.find { it.name == rawName.trim() }
                    ?: TalentItem(name = rawName.trim(), id = "")
            }
        }

        val speciesTalents = speciesTalentGroups.map(::resolveTalentGroup)
        val careerTalents = careerTalentGroups.map(::resolveTalentGroup)

        return listOf(speciesTalents, careerTalents)
    }

    override fun getTalent(talentName: String): TalentItem {
        return queries.getTalentEntityByName(talentName)
            .executeAsOne()
            .toTalentItem()
    }

    override suspend fun insertTalent(item: TalentItem) {
        queries.insertTalentEntity(
            id = item.id,
            name = item.name,
            max = item.max,
            tests = item.tests,
            description = item.description,
            specialization = item.specialization,
            source = item.source,
            page = item.page?.toLong(),
            updatedAt = item.updatedAt.toString(),
            deletedAt = null
        )
    }

    // OTHERS

    override fun getWealth(careerPathName: String): List<Int> {
        val careerPath = try {
            queries.getCareerPathEntityByName(careerPathName).executeAsOneOrNull()
        } catch (e: Exception) {
            null
        }

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
