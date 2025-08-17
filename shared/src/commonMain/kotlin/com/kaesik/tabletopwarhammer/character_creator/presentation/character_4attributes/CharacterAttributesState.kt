package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes

import com.kaesik.tabletopwarhammer.core.domain.library.items.AttributeItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem
import com.kaesik.tabletopwarhammer.core.domain.util.DataError

data class AttributesSelection(
    val totalAttributes: List<Int>? = null,
    val fatePoints: Int? = null,
    val resiliencePoints: Int? = null,
    val exp: Int = 0,
    val message: String = ""
)

data class CharacterAttributesState(
    val error: DataError? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,

    val showFateResilienceCard: Boolean = false,

    val species: SpeciesItem? = null,

    val attributeList: List<AttributeItem> = emptyList(),

    val diceThrow: String = "",
    val diceThrows: List<String> = emptyList(),
    val hasRolledAllDice: Boolean = false,

    val rolledDiceResults: List<Int> = emptyList(),
    val reorderedDiceResults: List<Int> = emptyList(),

    val baseAttributeValues: List<Int> = emptyList(),
    val totalAttributeValues: List<Int> = emptyList(),

    val baseFatePoints: Int = 0,
    val fatePoints: Int = 0,
    val baseResiliencePoints: Int = 0,
    val resiliencePoints: Int = 0,
    val extraPoints: Int = 0,

    val hasReceivedXpForRolling: Boolean = false,

    val canRollAttributes: Boolean = false,
    val canReorderAttributes: Boolean = false,
    val canSelectAttributes: Boolean = false,

    val hasRolledAttributes: Boolean = false,
    val hasReorderedAttributes: Boolean = false,
    val hasSelectedAttributes: Boolean = false,

    val pendingRandomSelection: AttributesSelection? = null,
    val pendingAttributesSelection: AttributesSelection? = null
)
