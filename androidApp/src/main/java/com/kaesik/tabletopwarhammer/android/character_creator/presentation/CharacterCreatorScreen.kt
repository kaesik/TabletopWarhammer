package com.kaesik.tabletopwarhammer.android.character_creator.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.android.character_creator.presentation.components.Button1
import com.kaesik.tabletopwarhammer.character_creator.presentation.CharacterCreatorEvent
import com.kaesik.tabletopwarhammer.character_creator.presentation.CharacterCreatorState
import com.kaesik.tabletopwarhammer.character_sheet.presentation.CharacterSheetEvent
import com.kaesik.tabletopwarhammer.character_sheet.presentation.CharacterSheetState

@Composable
fun CharacterCreatorScreen(
    state: CharacterCreatorState,
    event: (CharacterCreatorEvent) -> Unit
) {
    Scaffold (

    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button1(
                        text = "Button 1",
                        onClick = { }
                    )
                    Button1(
                        text = "Button 2",
                        onClick = { }
                    )
                    Button1(
                        text = "Button 3",
                        onClick = { }
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun CharacterCreatorScreenPreview() {
    CharacterCreatorScreen(
        state = CharacterCreatorState(),
        event = {}
    )
}
