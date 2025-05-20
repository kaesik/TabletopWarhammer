package com.kaesik.tabletopwarhammer.auth.presentation.intro

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.core.domain.util.SessionManager
import com.kaesik.tabletopwarhammer.core.presentation.WarhammerButton
import org.koin.androidx.compose.koinViewModel


@Composable
fun IntroScreenRoot(
    viewModel: AndroidIntroViewModel = koinViewModel(),
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit,
    onGuestClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    IntroScreen(
        state = state,
        onEvent = { event ->
            viewModel.onEvent(event)
            when (event) {
                IntroEvent.OnSignInClick -> onSignInClick()
                IntroEvent.OnSignUpClick -> onSignUpClick()
                IntroEvent.OnGuestClick -> {
                    SessionManager.isLoggedIn = false
                    onGuestClick()
                }
            }
        }
    )
}


@Composable
private fun IntroScreen(
    state: IntroState,
    onEvent: (IntroEvent) -> Unit
) {
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(16.dp)
        ) {
            item {
                Text(
                    text = "Welcome",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp,
                )
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                WarhammerButton(
                    text = "Sign in",
                    isLoading = state.isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onEvent(IntroEvent.OnSignInClick) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                WarhammerButton(
                    text = "Sign up",
                    isLoading = state.isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onEvent(IntroEvent.OnSignUpClick) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                WarhammerButton(
                    text = "Continue as Guest",
                    isLoading = state.isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onEvent(IntroEvent.OnGuestClick) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun IntroScreenPreview() {
    IntroScreen(
        state = IntroState(),
        onEvent = {},
    )
}
