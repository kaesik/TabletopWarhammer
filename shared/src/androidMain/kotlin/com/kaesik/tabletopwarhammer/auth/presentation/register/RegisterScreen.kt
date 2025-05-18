package com.kaesik.tabletopwarhammer.auth.presentation.register

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaesik.tabletopwarhammer.auth.domain.di.PasswordValidationState
import com.kaesik.tabletopwarhammer.auth.presentation.components.WarhammerPasswordTextField
import com.kaesik.tabletopwarhammer.auth.presentation.components.WarhammerTextField
import com.kaesik.tabletopwarhammer.core.presentation.Button2
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    onSignInClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: AndroidRegisterViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = viewModel.state.collectAsState().value

    state.error?.let { errorMessage ->
        LaunchedEffect(errorMessage) {
            keyboardController?.hide()
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            viewModel.onEvent(RegisterEvent.ClearError)
        }
    }

    if (state.isRegistering && state.error == null) {
        LaunchedEffect(Unit) {
            keyboardController?.hide()
            Toast.makeText(context, "Successfully registered", Toast.LENGTH_LONG).show()
            onSuccessfulRegistration()
        }
    }

    RegisterScreen(
        state = state,
        onEvent = { event -> viewModel.onEvent(event) },
        onSignInClick = onSignInClick
    )
}

@Composable
private fun RegisterScreen(
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
    onSignInClick: () -> Unit
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
                    text = "Create Account",
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                val annotatedString = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = FontFamily.Cursive,
                            color = Color.DarkGray
                        )
                    ) {
                        append("Already have a account.")
                        pushStringAnnotation(tag = "clickable_text", annotation = "login")
                        withStyle(
                            style = SpanStyle(
                                fontFamily = FontFamily.Cursive,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            append("Login")
                        }
                    }
                }
                ClickableText(
                    text = annotatedString,
                    style = MaterialTheme.typography.bodyMedium,
                    onClick = { offset ->
                        annotatedString.getStringAnnotations("clickable_text", offset, offset)
                            .firstOrNull()?.let { onSignInClick() }
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(48.dp))
            }
            item {
                WarhammerTextField(
                    value = state.email ?: "",
                    startIcon = Icons.Default.Lock,
                    endIcon = if (state.isEmailValid) Icons.Default.Check else null,
                    hint = "example@email.com",
                    title = "Email",
                    modifier = Modifier.fillMaxWidth(),
                    additionalInfo = "info",
                    keyboardType = KeyboardType.Email,
                    onValueChange = { onEvent(RegisterEvent.OnEmailChange(it)) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                WarhammerPasswordTextField(
                    value = state.password ?: "",
                    hint = "Password",
                    title = "Password",
                    modifier = Modifier.fillMaxWidth(),
                    isPasswordVisible = state.isPasswordVisible,
                    onTogglePasswordVisibility = {
                        onEvent(RegisterEvent.OnTogglePasswordVisibilityClick)
                    },
                    onValueChange = { onEvent(RegisterEvent.OnPasswordChange(it)) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                // Password Requirements
                listOf(
                    "Minimal length is 8 characters" to state.passwordValidationState.hasMinimalLength,
                    "Need have a number" to state.passwordValidationState.hasNumber,
                    "Need have a lowercase character" to state.passwordValidationState.hasLowerCaseChar,
                    "Need hava a uppercase character" to state.passwordValidationState.hasUpperCaseChar
                ).forEach { (resId, isValid) ->
                    PasswordRequirement(
                        text = resId,
                        isValid = isValid
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
            item {
                Button2(
                    text = "Register",
                    isLoading = state.isRegistering,
                    enabled = state.canRegister,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onEvent(RegisterEvent.OnRegisterClick) }
                )
            }
        }
    }
}


@Composable
fun PasswordRequirement(
    text: String,
    isValid: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isValid) {
                Icons.Default.Check
            } else {
                Icons.Default.Cancel
            },
            contentDescription = null,
            tint = if (isValid) {
                Color.Green
            } else {
                Color.Red
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp,
        )
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen(
        state = RegisterState(
            passwordValidationState = PasswordValidationState(
                hasMinimalLength = true,
                hasNumber = true,
                hasLowerCaseChar = true,
                hasUpperCaseChar = true,
            )
        ),
        onEvent = {},
        onSignInClick = {},
    )
}
