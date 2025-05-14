package com.kaesik.tabletopwarhammer.core.data.character

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterDataSource
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.domain.util.CommonFlow
import com.kaesik.tabletopwarhammer.core.domain.util.toCommonFlow
import com.kaesik.tabletopwarhammer.database.TabletopWarhammerDatabase
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class SqlDelightCharacterDataSource(
    database: TabletopWarhammerDatabase,
) : CharacterDataSource {

    private val queries = database.tabletopQueries

    override fun getCharacter(context: CoroutineContext): CommonFlow<List<CharacterItem>> {
        return queries
            .getCharacterEntity()
            .asFlow()
            .mapToList(context)
            .map { character ->
                character.map { it.toCharacterItem() }
            }
            .toCommonFlow()
    }

    override suspend fun insertCharacter(characterItem: CharacterItem) {
        queries.insertCharacterEntity(
            id = characterItem.id.toLong(),
            name = characterItem.name,
            species = characterItem.species,
            class_ = characterItem.cLass,
            career = characterItem.career,
            careerLevel = characterItem.careerLevel,
            careerPath = characterItem.careerPath,
            status = characterItem.status,
            age = characterItem.age,
            height = characterItem.height,
            hair = characterItem.hair,
            eyes = characterItem.eyes,
            weaponSkill = characterItem.weaponSkill.joinToString(","),
            ballisticSkill = characterItem.ballisticSkill.joinToString(","),
            strength = characterItem.strength.joinToString(","),
            toughness = characterItem.toughness.joinToString(","),
            initiative = characterItem.initiative.joinToString(","),
            agility = characterItem.agility.joinToString(","),
            dexterity = characterItem.dexterity.joinToString(","),
            intelligence = characterItem.intelligence.joinToString(","),
            willPower = characterItem.willPower.joinToString(","),
            fellowship = characterItem.fellowship.joinToString(","),
            fate = characterItem.fate.toLong(),
            fortune = characterItem.fortune.toLong(),
            resilience = characterItem.resilience.toLong(),
            resolve = characterItem.resolve.toLong(),
            motivation = characterItem.motivation,
            experience = characterItem.experience.toString(),
            movement = characterItem.movement.toLong(),
            walk = characterItem.walk.toLong(),
            run = characterItem.run.toLong(),
            basicSkills = characterItem.basicSkills.joinToString(","),
            advancedSkills = characterItem.advancedSkills.joinToString(","),
            talents = characterItem.talents.joinToString(","),
            ambitionShortTerm = characterItem.ambitionShortTerm,
            ambitionLongTerm = characterItem.ambitionLongTerm,
            partyName = characterItem.partyName,
            partyAmbitionShortTerm = characterItem.partyAmbitionShortTerm,
            partyAmbitionLongTerm = characterItem.partyAmbitionLongTerm,
            partyMembers = characterItem.partyMembers.joinToString(","),
            armour = characterItem.armour.joinToString(","),
            weapons = characterItem.weapons.joinToString(","),
            trappings = characterItem.trappings.joinToString(","),
            psychology = characterItem.psychology.joinToString(","),
            mutations = characterItem.mutations.joinToString(","),
            wealth = characterItem.wealth.joinToString(","),
            encumbrance = characterItem.encumbrance.joinToString(","),
            wounds = characterItem.wounds.joinToString(","),
            spells = characterItem.spells.joinToString(","),
            prayers = characterItem.prayers.joinToString(","),
            sin = characterItem.sin.toLong(),
        )
    }
}
