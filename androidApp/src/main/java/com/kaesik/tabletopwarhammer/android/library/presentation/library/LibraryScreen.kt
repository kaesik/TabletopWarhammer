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
import com.kaesik.tabletopwarhammer.library.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.library.presentation.library.LibraryEvent
import com.kaesik.tabletopwarhammer.library.presentation.library.LibraryState
//@Composable
//fun LibraryScreenRoot(
//    viewModel: AndroidLibraryViewModel
//)

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
                    for (enum in LibraryEnum.entries) {
                        Button1(
                            text = enum.name,
                            onClick = {
                                println("dupa")
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
