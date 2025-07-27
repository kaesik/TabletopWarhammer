package com.kaesik.tabletopwarhammer.user.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.CharacterAttributesEvent
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.CharacterAttributesState
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorSnackbarHost
import com.kaesik.tabletopwarhammer.core.presentation.MainScaffold
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserScreenRoot(
    viewModel: AndroidUserViewModel = koinViewModel(),
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    UserScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                else -> viewModel.onEvent(event)
            }
        },
        snackbarHostState = snackbarHostState

    )
}

@Composable
fun UserScreen(
    state: UserState,
    onEvent: (UserEvent) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    MainScaffold(
        title = "User",
        snackbarHost = { CharacterCreatorSnackbarHost(snackbarHostState) },
        isLoading = state.isLoading,
        isError = state.isError,
        error = state.error,
        content = { padding ->
            Column(
                modifier = Modifier.padding(padding)
            ) {  }
        },
    )
}
