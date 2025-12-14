package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.attributes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults.iconButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.attributes.components.AttributesCharacteristicsSection
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.attributes.components.AttributesWoundsSection
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.theme.Black1
import com.kaesik.tabletopwarhammer.core.theme.Brown1
import com.kaesik.tabletopwarhammer.core.theme.Light1
import com.kaesik.tabletopwarhammer.core.theme.Red

// TODO: CLEAN THAT THING UP LATER
@Composable
fun CharacterSheetAttributesTab(
    character: CharacterItem,
    onCharacterChange: (CharacterItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            AttributesWoundsSection(
                character = character,
                onCharacterChange = onCharacterChange,
            )
        }

        item {
            AttributesCharacteristicsSection(
                character = character,
                onCharacterChange = onCharacterChange
            )
        }
    }
}

@Preview
@Composable
private fun CharacterSheetAttributesTabPreview() {
    CharacterSheetAttributesTab(
        character = CharacterItem.default(),
        onCharacterChange = {}
    )
}
