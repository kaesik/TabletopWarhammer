package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes

import com.kaesik.tabletopwarhammer.core.domain.library.items.AttributeItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem
import com.kaesik.tabletopwarhammer.core.domain.util.DataError

data class CharacterAttributesState(
    val error: DataError? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,

    val showFateResilienceCard: Boolean = false,

    val species: SpeciesItem? = null,

    val attributeList: List<AttributeItem> = emptyList(),

    val baseAttributeValues: List<Int> = emptyList(),
    val totalAttributeValues: List<Int> = emptyList(),

    val baseFatePoints: Int = 0,
    val fatePoints: Int = 0,
    val baseResiliencePoints: Int = 0,
    val resiliencePoints: Int = 0,
    val extraPoints: Int = 0,

    //DICE THROWS
    val diceThrow: String = "",
    val diceThrows: List<String> = emptyList(),
    val rolledDiceResults: List<Int> = emptyList(),
    val hasRolledAllDice: Boolean = false,

    // ROLL
    val canRollAttributes: Boolean = false,

    // REORDER
    val canReorderAttributes: Boolean = false,

    //ALLOCATE
    val canAllocateAttributes: Boolean = false,
    val allocatedDiceResults: List<Int> = emptyList(),
    val allocatePointsLeft: Int = 100,
)
