package com.kaesik.tabletopwarhammer.library.presentation.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.library.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.library.presentation.components.Button1
import org.koin.androidx.compose.koinViewModel

@Composable
fun LibraryScreenRoot(
    viewModel: AndroidLibraryViewModel = koinViewModel(),
    onLibraryListSelect: (LibraryEnum) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LibraryScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is LibraryEvent.OnLibraryListSelect -> {
                    println("LibraryScreen:LibraryScreenRoot ${event.fromTable}")
                    onLibraryListSelect(event.fromTable)
                }

                else -> Unit
            }

            viewModel.onEvent(event)
        }
    )
}

@Composable
fun LibraryScreen(
    state: LibraryState,
    onEvent: (LibraryEvent) -> Unit,
) {
    Scaffold(

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
                    Text("LibraryScreen")
                    for (enum in LibraryEnum.entries) {
                        Button1(
                            text = enum.name,
                            onClick = {
                                println("LibraryScreen:LibraryScreen ${enum.name}")
                                onEvent(LibraryEvent.OnLibraryListSelect(enum))
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
fun LibraryScreenPreview() {
    LibraryScreen(
        state = LibraryState(),
        onEvent = {}
    )
}
