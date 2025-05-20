package com.kaesik.tabletopwarhammer.auth.presentation.login

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaesik.tabletopwarhammer.auth.presentation.components.WarhammerPasswordTextField
import com.kaesik.tabletopwarhammer.auth.presentation.components.WarhammerTextField
import com.kaesik.tabletopwarhammer.core.domain.util.SessionManager
import com.kaesik.tabletopwarhammer.core.presentation.WarhammerButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    onGuestClick: () -> Unit,
    viewModel: AndroidLoginViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = viewModel.state.collectAsState().value

    state.error?.let { errorMessage ->
        LaunchedEffect(errorMessage) {
            keyboardController?.hide()
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            viewModel.onEvent(LoginEvent.ClearError)
        }
    }

    LaunchedEffect(state.isLoggingIn, state.error) {
        if (!state.isLoggingIn && state.error == null && state.isLoggedIn) {
            keyboardController?.hide()
            Toast.makeText(context, "Successfully logged in", Toast.LENGTH_LONG).show()
            onLoginSuccess()
        }
    }

    LoginScreen(
        state = state,
        onSignUpClick = onSignUpClick,
        onEvent = { event ->
            viewModel.onEvent(event)
            when (event) {
                LoginEvent.OnGuestClick -> {
                    SessionManager.isLoggedIn = false
                    onGuestClick()
                }

                else -> Unit
            }
        }
    )
}

@Composable
private fun LoginScreen(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    onSignUpClick: () -> Unit
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
                    text = "Hi There!",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            item {
                Text(
                    text = "Welcome back to the app!",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            item {
                Spacer(modifier = Modifier.height(48.dp))
            }
            item {
                WarhammerTextField(
                    value = state.email,
                    startIcon = Icons.Default.Email,
                    endIcon = if (state.isEmailValid) Icons.Default.Check else null,
                    hint = "example@email.com",
                    title = "Email",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardType = KeyboardType.Email,
                    onValueChange = { onEvent(LoginEvent.OnEmailChange(it)) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                WarhammerPasswordTextField(
                    value = state.password,
                    hint = "Password",
                    title = "Password",
                    modifier = Modifier.fillMaxWidth(),
                    isPasswordVisible = state.isPasswordVisible,
                    onTogglePasswordVisibility = { onEvent(LoginEvent.OnTogglePasswordVisibility) },
                    onValueChange = { onEvent(LoginEvent.OnPasswordChange(it)) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
            item {
                WarhammerButton(
                    text = "Login",
                    isLoading = state.isLoggingIn,
                    enabled = state.canLogin,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onEvent(LoginEvent.OnLoginClick) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                WarhammerButton(
                    text = "Continue as Guest",
                    isLoading = state.isLoggingIn,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onEvent(LoginEvent.OnGuestClick) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                WarhammerButton(
                    text = "Login with Google",
                    isLoading = state.isLoggingIn,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onEvent(LoginEvent.LoginWithGoogle) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                val annotatedString = buildAnnotatedString {
                    append("Don't have an account? ")
                    pushStringAnnotation(tag = "register", annotation = "register")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        append("Sign Up")
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    ClickableText(
                        text = annotatedString,
                        style = MaterialTheme.typography.bodyMedium,
                        onClick = { offset ->
                            annotatedString.getStringAnnotations(
                                tag = "register",
                                start = offset,
                                end = offset
                            ).firstOrNull()?.let { onSignUpClick() }
                        }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        state = LoginState(),
        onEvent = {},
        onSignUpClick = {},
    )
}
