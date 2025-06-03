package com.kaesik.tabletopwarhammer.main.presentation.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.core.presentation.MainScaffold
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun AboutScreenRoot(
    viewModel: AndroidAboutViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    AboutScreen(
        state = state,
        onEvent = { event ->
            coroutineScope.launch {
                when (event) {
                    else -> Unit
                }

                viewModel.onEvent(event)
            }
        }
    )
}

@Composable
fun AboutScreen(
    state: AboutState,
    onEvent: (AboutEvent) -> Unit
) {
    MainScaffold(
        title = "About us",
        isLoading = state.isLoading,
        isError = state.isError,
        error = state.error,
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
            }
        }
    )
}

@Preview
@Composable
fun AboutScreenPreview() {
    AboutScreen(
        state = AboutState(),
        onEvent = {},
    )
}
