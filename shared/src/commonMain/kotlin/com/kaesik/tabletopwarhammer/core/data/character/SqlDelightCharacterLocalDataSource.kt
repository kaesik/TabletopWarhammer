package com.kaesik.tabletopwarhammer.core.data.character

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterLocalDataSource
import com.kaesik.tabletopwarhammer.core.domain.util.CommonFlow
import com.kaesik.tabletopwarhammer.core.domain.util.toCommonFlow
import com.kaesik.tabletopwarhammer.database.TabletopWarhammerDatabase
import kotlinx.coroutines.flow.map
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlin.coroutines.CoroutineContext

class SqlDelightCharacterLocalDataSource(
    database: TabletopWarhammerDatabase,
) : CharacterLocalDataSource {

    private val queries = database.tabletopQueries

    override fun getAllCharacters(): List<CharacterItem> {
        return queries
            .getCharacterEntity()
            .executeAsList()
            .map { it.toCharacterItem() }
    }

    override fun getCharacter(context: CoroutineContext): CommonFlow<List<CharacterItem>> {
        return queries
            .getCharacterEntity()
            .asFlow()
            .mapToList(context)
            .map { it.map { it.toCharacterItem() } }
            .toCommonFlow()
    }

    override suspend fun deleteCharacter(id: Long) {
        queries.deleteCharacterEntity(id)
    }

    override suspend fun insertCharacter(characterItem: CharacterItem) {
        val json = Json { prettyPrint = false }

        queries.insertCharacterEntity(
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
            weaponSkill = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.weaponSkill
            ),
            ballisticSkill = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.ballisticSkill
            ),
            strength = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.strength
            ),
            toughness = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.toughness
            ),
            initiative = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.initiative
            ),
            agility = json.encodeToString(ListSerializer(Int.serializer()), characterItem.agility),
            dexterity = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.dexterity
            ),
            intelligence = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.intelligence
            ),
            willPower = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.willPower
            ),
            fellowship = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.fellowship
            ),
            fate = characterItem.fate.toLong(),
            fortune = characterItem.fortune.toLong(),
            resilience = characterItem.resilience.toLong(),
            resolve = characterItem.resolve.toLong(),
            motivation = characterItem.motivation,
            experience = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.experience
            ),
            movement = characterItem.movement.toLong(),
            walk = characterItem.walk.toLong(),
            run = characterItem.run.toLong(),
            basicSkills = json.encodeToString(
                ListSerializer(ListSerializer(String.serializer())),
                characterItem.basicSkills
            ),
            advancedSkills = json.encodeToString(
                ListSerializer(ListSerializer(String.serializer())),
                characterItem.advancedSkills
            ),
            talents = json.encodeToString(
                ListSerializer(ListSerializer(String.serializer())),
                characterItem.talents
            ),
            ambitionShortTerm = characterItem.ambitionShortTerm,
            ambitionLongTerm = characterItem.ambitionLongTerm,
            partyName = characterItem.partyName,
            partyAmbitionShortTerm = characterItem.partyAmbitionShortTerm,
            partyAmbitionLongTerm = characterItem.partyAmbitionLongTerm,
            partyMembers = json.encodeToString(
                ListSerializer(ListSerializer(String.serializer())),
                characterItem.partyMembers
            ),
            armour = json.encodeToString(
                ListSerializer(ListSerializer(String.serializer())),
                characterItem.armour
            ),
            weapons = json.encodeToString(
                ListSerializer(ListSerializer(String.serializer())),
                characterItem.weapons
            ),
            trappings = json.encodeToString(
                ListSerializer(String.serializer()),
                characterItem.trappings
            ),
            psychology = json.encodeToString(
                ListSerializer(String.serializer()),
                characterItem.psychology
            ),
            mutations = json.encodeToString(
                ListSerializer(String.serializer()),
                characterItem.mutations
            ),
            corruption = characterItem.corruption.toString(),
            wealth = json.encodeToString(ListSerializer(Int.serializer()), characterItem.wealth),
            encumbrance = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.encumbrance
            ),
            wounds = json.encodeToString(ListSerializer(Int.serializer()), characterItem.wounds),
            woundsFormula = characterItem.woundsFormula,
            spells = json.encodeToString(
                ListSerializer(ListSerializer(String.serializer())),
                characterItem.spells
            ),
            prayers = json.encodeToString(
                ListSerializer(ListSerializer(String.serializer())),
                characterItem.prayers
            ),
            sin = characterItem.sin.toLong(),
            createdAt = characterItem.createdAt.toString(),
            updatedAt = characterItem.updatedAt.toString(),
            deletedAt = characterItem.deletedAt.toString(),
        )
    }

    override suspend fun updateCharacter(characterItem: CharacterItem) {
        val json = Json { prettyPrint = false }

        queries.updateCharacterEntity(
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
            weaponSkill = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.weaponSkill
            ),
            ballisticSkill = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.ballisticSkill
            ),
            strength = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.strength
            ),
            toughness = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.toughness
            ),
            initiative = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.initiative
            ),
            agility = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.agility
            ),
            dexterity = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.dexterity
            ),
            intelligence = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.intelligence
            ),
            willPower = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.willPower
            ),
            fellowship = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.fellowship
            ),
            fate = characterItem.fate.toLong(),
            fortune = characterItem.fortune.toLong(),
            resilience = characterItem.resilience.toLong(),
            resolve = characterItem.resolve.toLong(),
            motivation = characterItem.motivation,
            experience = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.experience
            ),
            movement = characterItem.movement.toLong(),
            walk = characterItem.walk.toLong(),
            run = characterItem.run.toLong(),
            basicSkills = json.encodeToString(
                ListSerializer(ListSerializer(String.serializer())),
                characterItem.basicSkills
            ),
            advancedSkills = json.encodeToString(
                ListSerializer(ListSerializer(String.serializer())),
                characterItem.advancedSkills
            ),
            talents = json.encodeToString(
                ListSerializer(ListSerializer(String.serializer())),
                characterItem.talents
            ),
            ambitionShortTerm = characterItem.ambitionShortTerm,
            ambitionLongTerm = characterItem.ambitionLongTerm,
            partyName = characterItem.partyName,
            partyAmbitionShortTerm = characterItem.partyAmbitionShortTerm,
            partyAmbitionLongTerm = characterItem.partyAmbitionLongTerm,
            partyMembers = json.encodeToString(
                ListSerializer(ListSerializer(String.serializer())),
                characterItem.partyMembers
            ),
            armour = json.encodeToString(
                ListSerializer(ListSerializer(String.serializer())),
                characterItem.armour
            ),
            weapons = json.encodeToString(
                ListSerializer(ListSerializer(String.serializer())),
                characterItem.weapons
            ),
            trappings = json.encodeToString(
                ListSerializer(String.serializer()),
                characterItem.trappings
            ),
            psychology = json.encodeToString(
                ListSerializer(String.serializer()),
                characterItem.psychology
            ),
            mutations = json.encodeToString(
                ListSerializer(String.serializer()),
                characterItem.mutations
            ),
            corruption = characterItem.corruption.toString(),
            wealth = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.wealth
            ),
            encumbrance = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.encumbrance
            ),
            wounds = json.encodeToString(
                ListSerializer(Int.serializer()),
                characterItem.wounds
            ),
            spells = json.encodeToString(
                ListSerializer(ListSerializer(String.serializer())),
                characterItem.spells
            ),
            prayers = json.encodeToString(
                ListSerializer(ListSerializer(String.serializer())),
                characterItem.prayers
            ),
            sin = characterItem.sin.toLong(),
            createdAt = characterItem.createdAt.toString(),
            updatedAt = characterItem.updatedAt.toString(),
            deletedAt = characterItem.deletedAt.toString(),
        )
    }
}
