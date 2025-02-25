package com.kaesik.tabletopwarhammer.android.menu.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
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
    event: (MenuEvent) -> Unit
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
fun MenuScreenPreview() {
    MenuScreen(
        state = MenuState(),
        event = {}
    )
}
