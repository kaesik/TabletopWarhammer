package com.kaesik.tabletopwarhammer.main.presentation.settings

import com.kaesik.tabletopwarhammer.core.domain.util.DataError

data class SettingsState(
    val error: DataError? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
)
