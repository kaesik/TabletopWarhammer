package com.kaesik.tabletopwarhammer.auth.presentation.login

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.auth.domain.AuthClient
import com.kaesik.tabletopwarhammer.auth.domain.AuthManager
import com.kaesik.tabletopwarhammer.auth.domain.di.UserDataValidator

class AndroidLoginViewModel(
    private val userDataValidator: UserDataValidator,
    private val client: AuthClient,
    private val authManager: AuthManager
) : ViewModel() {
    private val viewModel by lazy {
        LoginViewModel(
            userDataValidator = userDataValidator,
            client = client,
            authManager = authManager,
        )
    }

    val state = viewModel.state

    fun onEvent(event: LoginEvent) {
        viewModel.onEvent(event)
    }
}
