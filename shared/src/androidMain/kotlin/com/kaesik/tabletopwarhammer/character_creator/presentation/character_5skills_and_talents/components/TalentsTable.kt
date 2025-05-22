import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.TalentTableItem
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.TalentTableRadioItem
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.TalentTableRandomItem
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
    onRandomTalentRolled: (groupIndex: Int, talentIndex: Int, rolledName: String) -> Unit
) {
    val sourceLabel = if (isSpeciesMode) speciesName else careerName

    LazyColumn(
        modifier = Modifier.height(300.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(talentsGroups.size) { groupIndex ->
            val group = talentsGroups[groupIndex]

            when {
                group.size == 1 && " or " !in group[0].name && group[0].name != "Random Talent" -> {
                    TalentTableItem(
                        talent = group[0],
                        isSelected = selectedTalents.contains(group[0]),
                        sourceLabel = sourceLabel,
                        onTalentChecked = onTalentChecked
                    )
                }

                group.all { it.name == "Random Talent" } -> {
                    group.forEachIndexed { talentIndex, _ ->
                        val key = groupIndex to talentIndex
                        val rolledTalentName = rolledTalents[key]

                        TalentTableRandomItem(
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

                else -> {
                    group.forEach { talent ->
                        TalentTableRadioItem(
                            talent = talent,
                            isSelected = selectedTalents.contains(talent),
                            onTalentSelected = { selected ->
                                group.forEach { t -> onTalentChecked(t, t == selected) }
                            },
                            sourceLabel = sourceLabel
                        )
                    }
                }
            }
        }
    }
}
