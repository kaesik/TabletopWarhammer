package com.kaesik.tabletopwarhammer.android.library.presentation.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.android.library.presentation.components.Button1
import com.kaesik.tabletopwarhammer.library.presentation.library.LibraryEvent
import com.kaesik.tabletopwarhammer.library.presentation.library.LibraryState

@Composable
fun LibraryScreen(
    state: LibraryState,
    onEvent: (LibraryEvent) -> Unit
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
                    Text("Library Screen")
                    Button1(
                        text = "attribute",
                        onClick = {
                            onEvent(LibraryEvent.LoadLibrary("attribute"))
                        }
                    )
                    Button1(
                        text = "career",
                        onClick = {
                            onEvent(LibraryEvent.LoadLibrary("career"))
                        }
                    )
                    Button1(
                        text = "careerpath",
                        onClick = {
                            onEvent(LibraryEvent.LoadLibrary("careerpath"))
                        }
                    )
                    Button1(
                        text = "class",
                        onClick = {
                            onEvent(LibraryEvent.LoadLibrary("class"))
                        }
                    )
                    Button1(
                        text = "item",
                        onClick = {
                            onEvent(LibraryEvent.LoadLibrary("item"))
                        }
                    )
                    Button1(
                        text = "qualityflaw",
                        onClick = {
                            onEvent(LibraryEvent.LoadLibrary("qualityflaw"))
                        }
                    )
                    Button1(
                        text = "skill",
                        onClick = {
                            onEvent(LibraryEvent.LoadLibrary("skill"))
                        }
                    )
                    Button1(
                        text = "species",
                        onClick = {
                            onEvent(LibraryEvent.LoadLibrary("species"))
                        }
                    )
                    Button1(
                        text = "talent",
                        onClick = {
                            onEvent(LibraryEvent.LoadLibrary("talent"))
                        }
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun LibraryScreenPreview() {
    LibraryScreen(
        state = LibraryState(),
        onEvent = {}
    )
}
