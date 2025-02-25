package com.kaesik.tabletopwarhammer.android.menu.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.android.menu.presentation.components.Button1
import com.kaesik.tabletopwarhammer.menu.presentation.MenuEvent
import com.kaesik.tabletopwarhammer.menu.presentation.MenuState

@Composable
fun MenuScreen(
    state: MenuState,
    onEvent: (MenuEvent) -> Unit
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
                    Text("Menu Screen")
                    Button1(
                        text = "LibraryScreen",
                        onClick = { onEvent(MenuEvent.NavigateToLibraryScreen)}
                    )
                    Button1(
                        text = "CharacterSheetScreen",
                        onClick = { onEvent(MenuEvent.NavigateToCharacterSheetScreen) }
                    )
                    Button1(
                        text = "CharacterCreatorScreen",
                        onClick = { onEvent(MenuEvent.NavigateToCharacterCreatorScreen) }
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun MenuScreenPreview() {
    MenuScreen(
        state = MenuState(),
        onEvent = {}
    )
}
