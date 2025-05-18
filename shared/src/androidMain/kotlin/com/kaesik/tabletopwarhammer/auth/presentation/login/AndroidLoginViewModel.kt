package com.kaesik.tabletopwarhammer.auth.presentation.login

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.auth.domain.AuthClient
import com.kaesik.tabletopwarhammer.auth.domain.di.UserDataValidator

class AndroidLoginViewModel(
    private val userDataValidator: UserDataValidator,
    private val repository: AuthClient
) : ViewModel() {
    private val viewModel by lazy {
        LoginViewModel(
            userDataValidator = userDataValidator,
            client = repository
        )
    }

    val state = viewModel.state

    fun onEvent(event: LoginEvent) {
        viewModel.onEvent(event)
    }
}
