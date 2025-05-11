import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem

@Composable
fun TalentRadioItem(
    talent: TalentItem,
    isSelected: Boolean,
    onTalentSelected: (TalentItem) -> Unit
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
                RadioButton(
                    selected = isSelected,
                    onClick = { onTalentSelected(talent) }
                )
            }
        }
    }
}

@Preview
@Composable
fun TalentRadioItemPreview() {
    TalentRadioItem(
        talent = TalentItem(name = "Suave", id = ""),
        isSelected = true,
        onTalentSelected = {}
    )
}
