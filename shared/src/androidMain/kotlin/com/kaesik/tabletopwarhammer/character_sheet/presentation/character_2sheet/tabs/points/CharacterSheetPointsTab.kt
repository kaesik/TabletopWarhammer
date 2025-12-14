package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.points

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.CharacterSheetEvent
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.points.components.PointsAddPointsSection
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.points.components.PointsCorruptionSection
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.points.components.PointsExperienceSection
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.points.components.PointsFateSection
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.points.components.PointsMutationsSection
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.points.components.PointsResilienceSection
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem

// TODO: CLEAN THAT THING UP LATER
@Composable
fun CharacterSheetPointsTab(
    character: CharacterItem,
    onEvent: (CharacterSheetEvent) -> Unit
) {
    val currentXp = character.experience.getOrNull(0) ?: 0
    val spentXp = character.experience.getOrNull(1) ?: 0
    val totalXp = character.experience.getOrNull(2) ?: 0

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            PointsAddPointsSection(
                currentXp = currentXp,
                resilience = character.resilience,
                fate = character.fate,
                onAddExperience = { delta ->
                    onEvent(CharacterSheetEvent.AddExperience(delta))
                },
                onResilienceChange = { newValue ->
                    onEvent(CharacterSheetEvent.SetResilience(resilience = newValue))
                },
                onFateChange = { newValue ->
                    onEvent(CharacterSheetEvent.SetFate(fate = newValue))
                }
            )
        }

        item {
            PointsExperienceSection(
                current = currentXp,
                spent = spentXp,
                total = totalXp,
                onCurrentChange = { newValue ->
                    onEvent(
                        CharacterSheetEvent.SetExperienceValues(
                            current = newValue
                        )
                    )
                },
                onSpentChange = { newValue ->
                    onEvent(
                        CharacterSheetEvent.SetExperienceValues(
                            spent = newValue
                        )
                    )
                },
                onTotalChange = { newValue ->
                    onEvent(
                        CharacterSheetEvent.SetExperienceValues(
                            total = newValue
                        )
                    )
                },
            )
        }

        item {
            PointsResilienceSection(
                resilience = character.resilience,
                resolve = character.resolve,
                motivation = character.motivation,
                onResilienceChange = { newValue ->
                    onEvent(
                        CharacterSheetEvent.SetResilience(
                            resilience = newValue
                        )
                    )
                },
                onResolveChange = { newValue ->
                    onEvent(
                        CharacterSheetEvent.SetResilience(
                            resolve = newValue
                        )
                    )
                },
                onMotivationChange = { newValue ->
                    onEvent(
                        CharacterSheetEvent.SetResilience(
                            motivation = newValue
                        )
                    )
                }
            )
        }

        item {
            PointsFateSection(
                fate = character.fate,
                fortune = character.fortune,
                onFateChange = { newValue ->
                    onEvent(
                        CharacterSheetEvent.SetFate(
                            fate = newValue
                        )
                    )
                },
                onFortuneChange = { newValue ->
                    onEvent(
                        CharacterSheetEvent.SetFate(
                            fortune = newValue
                        )
                    )
                }
            )
        }

        item {
            PointsCorruptionSection(
                corruption = character.corruption,
                onCorruptionChange = { newValue ->
                    onEvent(
                        CharacterSheetEvent.SetCorruption(
                            value = newValue
                        )
                    )
                }
            )
        }

        item {
            PointsMutationsSection(
                mutations = character.mutations,
                onAddMutation = {
                    onEvent(CharacterSheetEvent.AddMutation)
                }
            )
        }
    }
}


@Preview
@Composable
private fun CharacterSheetPointsTabPreview() {
    CharacterSheetPointsTab(
        character = CharacterItem.default(),
        onEvent = {}
    )
}
