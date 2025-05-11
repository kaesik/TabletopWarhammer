import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem

@Composable
fun TalentTableItem(
    talent: TalentItem,
    isSelected: Boolean,
    onTalentChecked: (TalentItem, Boolean) -> Unit,
    onRandomTalentRoll: (TalentItem) -> Unit = {}
) {
    Surface(shadowElevation = 4.dp) {
        Row {
            Box(
                modifier = Modifier.weight(2f),
                contentAlignment = Alignment.Center
            ) {
                Text(text = talent.name)
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                when {
                    talent.name == "Random Talent" -> {
                        IconButton(onClick = { onRandomTalentRoll(talent) }) {
                            Icon(
                                imageVector = Icons.Default.Casino,
                                contentDescription = "Roll Random Talent"
                            )
                        }
                    }

                    " or " in talent.name -> {
                        Checkbox(
                            checked = isSelected,
                            onCheckedChange = { isChecked -> onTalentChecked(talent, isChecked) }
                        )
                    }

                    else -> {
                        // Automatyczny talent, brak akcji, brak checkboxa
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun TalentTableItemPreview() {
    TalentTableItem(
        talent = TalentItem(name = "Random Talent", id = ""),
        isSelected = false,
        onTalentChecked = { _, _ -> },
        onRandomTalentRoll = { println("Roll talent!") }
    )
}
