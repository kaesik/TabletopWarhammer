package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes

import com.kaesik.tabletopwarhammer.core.domain.library.items.AttributeItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem

data class CharacterAttributesState(
    val error: String? = null,
    val isLoading: Boolean = false,
    val showFateResilienceCard: Boolean = false,

    val species: SpeciesItem? = null,

    val attributeList: List<AttributeItem> = emptyList(),
    val totalAttributeValues: List<Int> = emptyList(),
    val diceThrows: List<String> = emptyList(),

    val baseFatePoints: Int = 0,
    val fatePoints: Int = 0,
    val baseResiliencePoints: Int = 0,
    val resiliencePoints: Int = 0,
    val extraPoints: Int = 0,
)
