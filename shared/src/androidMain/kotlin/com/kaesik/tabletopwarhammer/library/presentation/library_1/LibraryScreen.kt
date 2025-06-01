package com.kaesik.tabletopwarhammer.library.presentation.library_1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.core.presentation.MainScaffold
import com.kaesik.tabletopwarhammer.core.presentation.components.WarhammerButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun LibraryScreenRoot(
    viewModel: AndroidLibraryViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onLibraryListSelect: (LibraryEnum) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LibraryScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is LibraryEvent.OnLibraryListSelect -> onLibraryListSelect(event.fromTable)
                is LibraryEvent.OnBackClick -> onBackClick()
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
    MainScaffold(
        title = "Library",
        onBackClick = { onEvent(LibraryEvent.OnBackClick) },
        modifier = Modifier.fillMaxSize(),
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        for (enum in LibraryEnum.entries) {
                            WarhammerButton(
                                text = enum.name,
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    println("LibraryScreen:LibraryScreen ${enum.name}")
                                    onEvent(LibraryEvent.OnLibraryListSelect(enum))
                                },
                                isLoading = state.isLoading,
                            )
                        }
                    }
                }
            }

        },
    )
}

@Preview
@Composable
fun LibraryScreenPreview() {
    LibraryScreen(
        state = LibraryState(),
        onEvent = {},
    )
}
