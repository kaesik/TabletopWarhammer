package com.kaesik.tabletopwarhammer.core.domain.library.items

import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

data class SpeciesItem(
    override val id: String,
    override val name: String,
    val description: String? = null,
    val opinions: String? = null,
    val source: String? = null,
    val weaponSkill: String? = null,
    val ballisticSkill: String? = null,
    val strength: String? = null,
    val toughness: String? = null,
    val agility: String? = null,
    val dexterity: String? = null,
    val intelligence: String? = null,
    val willpower: String? = null,
    val fellowship: String? = null,
    val wounds: String? = null,
    val fatePoints: String? = null,
    val resilience: String? = null,
    val extraPoints: String? = null,
    val movement: String? = null,
    val skills: String? = null,
    val talents: String? = null,
    val forenames: String? = null,
    val surnames: String? = null,
    val clans: String? = null,
    val epithets: String? = null,
    val age: String? = null,
    val eyeColour: String? = null,
    val hairColour: String? = null,
    val height: String? = null,
    val initiative: String? = null,
    val page: Int? = null,
    val names: String? = null,
    override val updatedAt: String? = null
) : LibraryItem {
    companion object {
        fun default(): SpeciesItem {
            return SpeciesItem(
                id = "",
                name = "",
                description = null,
                opinions = null,
                source = null,
                weaponSkill = null,
                ballisticSkill = null,
                strength = null,
                toughness = null,
                agility = null,
                dexterity = null,
                intelligence = null,
                willpower = null,
                fellowship = null,
                wounds = null,
                fatePoints = null,
                resilience = null,
                extraPoints = null,
                movement = null,
                skills = null,
                talents = null,
                forenames = null,
                surnames = null,
                clans = null,
                epithets = null,
                age = null,
                eyeColour = null,
                hairColour = null,
                height = null,
                initiative = null,
                page = null,
                names = null
            )
        }
    }
}
