import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.RandomTalentDiceButton
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.rollRandomTalent
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem

@Composable
fun TalentsTable(
    talentsGroups: List<List<TalentItem>>,
    selectedTalents: List<TalentItem>,
    onTalentChecked: (TalentItem, Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier.height(300.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(talentsGroups.size) { groupIndex ->
            val group = talentsGroups[groupIndex]

            when {
                group.size == 1 && " or " !in group[0].name && group[0].name != "Random Talent" -> {
                    Text(text = group[0].name)
                }

                group.all { it.name == "Random Talent" } -> {
                    var rolledTalentName by remember { mutableStateOf<String?>(null) }
                    RandomTalentDiceButton(
                        onRoll = {
                            rolledTalentName = rollRandomTalent(
                                selectedTalents.map { it.name } + rolledTalentName.orEmpty()
                            )
                        },
                        rolledTalentName = rolledTalentName
                    )
                }

                else -> {
                    group.forEach { talent ->
                        TalentRadioItem(
                            talent = talent,
                            isSelected = selectedTalents.contains(talent),
                            onTalentSelected = { selected ->
                                group.forEach { t ->
                                    onTalentChecked(t, t == selected)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun TalentsTablePreview() {
    TalentsTable(
        talentsGroups = listOf(
            listOf(TalentItem(name = "Doomed", id = "")),
            listOf(
                TalentItem(name = "Savvy", id = ""),
                TalentItem(name = "Suave", id = "")
            ),
            listOf(
                TalentItem(name = "Random Talent", id = ""),
                TalentItem(name = "Random Talent", id = "")
            )
        ),
        selectedTalents = listOf(),
        onTalentChecked = { _, _ -> }
    )
}
