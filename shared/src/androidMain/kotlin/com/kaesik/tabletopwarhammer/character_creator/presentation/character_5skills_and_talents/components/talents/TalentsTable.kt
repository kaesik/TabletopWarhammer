package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.talents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SeparatorOr
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.getBaseName
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.hasAnyMarker
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.rollRandomTalent
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem

@Composable
fun TalentsTable(
    talentsGroups: List<List<TalentItem>>,
    selectedTalents: List<TalentItem>,
    rolledTalents: Map<Pair<Int, Int>, String>,
    speciesName: String,
    careerName: String,
    isSpeciesMode: Boolean,
    onTalentChecked: (TalentItem, Boolean) -> Unit,
    onRandomTalentRolled: (groupIndex: Int, talentIndex: Int, rolledName: String) -> Unit,
    talentSpecializations: Map<String, List<String>>,
    loadingTalentSpecs: Set<String>,
    requestTalentSpecializations: (String) -> Unit
) {
    val sourceLabel = if (isSpeciesMode) speciesName else careerName

    fun isSelectedByBase(t: TalentItem): Boolean {
        val b = getBaseName(t.name)
        return selectedTalents.any { getBaseName(it.name).equals(b, true) }
    }

    LazyColumn(
        modifier = Modifier.height(300.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(
            items = talentsGroups,
            key = { idx, grp -> "talent_group_$idx:${grp.joinToString("|") { it.name }}" }
        ) { groupIndex, group ->

            when {
                // Random Talent group
                group.isNotEmpty() && group.all { it.name == "Random Talent" } -> {
                    group.forEachIndexed { talentIndex, _ ->
                        val key = groupIndex to talentIndex
                        val rolledTalentName = rolledTalents[key]

                        TalentTableRowRandom(
                            rolledTalentName = rolledTalentName,
                            onRoll = {
                                val newTalent = rollRandomTalent(
                                    selectedTalents.map { it.name } + rolledTalents.values
                                )
                                onRandomTalentRolled(groupIndex, talentIndex, newTalent)
                            }
                        )
                    }
                }

                // Choice from two talents group (OR)
                group.size > 1 && group.none {
                    it.name.equals(
                        "Random Talent",
                        ignoreCase = true
                    )
                } -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        group.forEachIndexed { idx, talent ->
                            val base = getBaseName(talent.name)
                            val options = talentSpecializations[base].orEmpty()
                            val loading = loadingTalentSpecs.contains(base)

                            if (hasAnyMarker(talent.name)) {
                                TalentTableRowSpecialization(
                                    talent = talent,
                                    isSelected = isSelectedByBase(talent),
                                    sourceLabel = sourceLabel,
                                    options = options,
                                    loading = loading,
                                    onRequestOptions = { requestTalentSpecializations(base) },
                                    onResolved = { resolved ->
                                        val resolvedBase = getBaseName(resolved.name)
                                        group.forEach { t ->
                                            val sameBase = getBaseName(t.name) == resolvedBase
                                            onTalentChecked(if (sameBase) resolved else t, sameBase)
                                        }
                                    }
                                )
                            } else {
                                TalentTableRowChoice(
                                    talent = talent,
                                    isSelected = isSelectedByBase(talent),
                                    sourceLabel = sourceLabel,
                                    onTalentSelected = {
                                        group.forEach { t -> onTalentChecked(t, t == talent) }
                                    }
                                )
                            }
                            if (idx < group.lastIndex) SeparatorOr()
                        }
                    }
                }

                // Simple single talent
                else -> {
                    if (group.isNotEmpty()) {
                        val talent = group[0]
                        if (hasAnyMarker(talent.name)) {
                            val base = getBaseName(talent.name)
                            val options = talentSpecializations[base].orEmpty()
                            val loading = loadingTalentSpecs.contains(base)

                            TalentTableRowSpecialization(
                                talent = talent,
                                isSelected = isSelectedByBase(talent),
                                sourceLabel = sourceLabel,
                                options = options,
                                loading = loading,
                                onRequestOptions = { requestTalentSpecializations(base) },
                                onResolved = { resolved ->
                                    onTalentChecked(resolved, true)
                                },
                            )
                        } else {
                            TalentTableRowSimple(
                                talent = talent,
                                sourceLabel = sourceLabel
                            )
                        }
                    }
                }
            }
        }
    }
}
