package com.kaesik.tabletopwarhammer.core.domain.info

import com.kaesik.tabletopwarhammer.core.domain.library.items.AttributeItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerPathItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ClassItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ItemItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.QualityFlawItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem

interface InfoMappers {
    fun fromTalent(item: TalentItem): InfoDetails
    fun fromSkill(item: SkillItem): InfoDetails
    fun fromItem(item: ItemItem): InfoDetails
    fun fromAttribute(item: AttributeItem): InfoDetails
    fun fromCareer(item: CareerItem): InfoDetails
    fun fromCareerPath(item: CareerPathItem): InfoDetails
    fun fromClass(item: ClassItem): InfoDetails
    fun fromSpecies(item: SpeciesItem): InfoDetails
    fun fromQualityFlaw(item: QualityFlawItem): InfoDetails
}

class SimpleInfoMappers : InfoMappers {
    override fun fromTalent(item: TalentItem) = InfoDetails(
        title = item.name,
        subtitle = item.tests?.takeIf { it.isNotBlank() },
        description = buildString {
            item.description?.let { appendLine(it) }
            item.max?.takeIf { it.isNotBlank() }?.let { appendLine("Max: $it") }
        }.trim(),
        source = item.source,
        page = item.page
    )

    override fun fromSkill(item: SkillItem) = InfoDetails(
        title = item.name,
        subtitle = buildString {
            item.attribute?.let { append(it) }
            val tags = buildList {
                if (item.isBasic == true) add("Basic") else add("Advanced")
                if (item.isGrouped == true) add("Grouped")
            }
            if (tags.isNotEmpty()) {
                if (isNotEmpty()) append(" • ")
                append(tags.joinToString(", "))
            }
        }.ifBlank { null },
        description = buildString {
            item.description?.let { appendLine(it) }
            item.specialization?.takeIf { it.isNotBlank() }
                ?.let { appendLine("Specialization: $it") }
        }.trim(),
        source = item.source,
        page = item.page
    )

    override fun fromItem(item: ItemItem): InfoDetails {
        val subtitle = listOfNotNull(item.type, item.group, item.availability)
            .filter { !it.isNullOrBlank() }.joinToString(" • ").ifBlank { null }
        return InfoDetails(
            title = item.name,
            subtitle = subtitle,
            description = buildString {
                item.description?.let { appendLine(it) }
                fun add(label: String, v: String?) {
                    if (!v.isNullOrBlank()) appendLine("$label: $v")
                }
                add("Damage", item.damage)
                add("AP", item.ap)
                add("Range", item.range)
                add("Melee/Ranged", item.meeleRanged)
                add("Enc.", item.encumbrance)
                add("Price", item.price)
                add("Availability", item.availability)
                add("Qualities/Flaws", item.qualitiesAndFlaws)
            }.trim(),
            source = item.source,
            page = item.page
        )
    }

    override fun fromAttribute(item: AttributeItem) = InfoDetails(
        title = item.name,
        subtitle = item.shortName,
        description = item.description.orEmpty(),
        source = null,
        page = item.page
    )

    override fun fromCareer(item: CareerItem) = InfoDetails(
        title = item.name,
        subtitle = item.className,
        description = buildString {
            item.description?.let { appendLine(it) }
            item.advanceScheme?.takeIf { it.isNotBlank() }
                ?.let { appendLine("Advance Scheme: $it") }
            item.quotations?.takeIf { it.isNotBlank() }?.let { appendLine("Quotes: $it") }
            item.adventuring?.takeIf { it.isNotBlank() }?.let { appendLine("Adventuring: $it") }
            item.careerPath?.takeIf { it.isNotBlank() }?.let { appendLine("Path: $it") }
            item.limitations?.takeIf { it.isNotBlank() }?.let { appendLine("Limitations: $it") }
        }.trim(),
        source = item.source,
        page = item.page
    )

    override fun fromCareerPath(item: CareerPathItem) = InfoDetails(
        title = item.name,
        subtitle = item.status,
        description = buildString {
            item.skills?.takeIf { it.isNotBlank() }?.let { appendLine("Skills: $it") }
            item.talents?.takeIf { it.isNotBlank() }?.let { appendLine("Talents: $it") }
            item.trappings?.takeIf { it.isNotBlank() }?.let { appendLine("Trappings: $it") }
        }.trim(),
        source = null,
        page = null
    )

    override fun fromClass(item: ClassItem) = InfoDetails(
        title = item.name,
        subtitle = null,
        description = buildString {
            item.description?.let { appendLine(it) }
            item.trappings?.takeIf { it.isNotBlank() }?.let { appendLine("Trappings: $it") }
            item.careers?.takeIf { it.isNotBlank() }?.let { appendLine("Careers: $it") }
        }.trim(),
        source = null,
        page = item.page
    )

    override fun fromSpecies(item: SpeciesItem) = InfoDetails(
        title = item.name,
        subtitle = null,
        description = buildString {
            item.description?.let { appendLine(it) }
            item.opinions?.takeIf { it.isNotBlank() }?.let { appendLine("Opinions: $it") }
            fun add(label: String, v: String?) {
                if (!v.isNullOrBlank()) appendLine("$label: $v")
            }
            add("Skills", item.skills)
            add("Talents", item.talents)
            add("Movement", item.movement)
            add("Wounds", item.wounds)
            add("Fate Points", item.fatePoints)
            add("Resilience", item.resilience)
            add("Extra Points", item.extraPoints)
        }.trim(),
        source = item.source,
        page = item.page
    )

    override fun fromQualityFlaw(item: QualityFlawItem) = InfoDetails(
        title = item.name,
        subtitle = listOfNotNull(if (item.isQuality == true) "Quality" else "Flaw", item.group)
            .filter { !it.isNullOrBlank() }.joinToString(" • ").ifBlank { null },
        description = item.description.orEmpty(),
        source = item.source,
        page = item.page
    )
}
