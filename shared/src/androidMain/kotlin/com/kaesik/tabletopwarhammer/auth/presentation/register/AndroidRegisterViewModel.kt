package com.kaesik.tabletopwarhammer.auth.presentation.register

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.auth.domain.AuthClient
import com.kaesik.tabletopwarhammer.auth.domain.di.UserDataValidator

class AndroidRegisterViewModel(
    private val userDataValidator: UserDataValidator,
    private val client: AuthClient
) : ViewModel() {
    private val viewModel by lazy {
        RegisterViewModel(
            userDataValidator = userDataValidator,
            client = client
        )
    }

    val state = viewModel.state

    fun onEvent(event: RegisterEvent) {
        viewModel.onEvent(event)
    }
}
