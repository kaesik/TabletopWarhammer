package com.kaesik.tabletopwarhammer.features.presentation.info

import com.kaesik.tabletopwarhammer.core.domain.info.InfoDetails
import com.kaesik.tabletopwarhammer.core.domain.info.InspectRef
import com.kaesik.tabletopwarhammer.core.domain.util.DataError

data class InfoDialogState(
    val error: DataError? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,

    val isOpen: Boolean = false,

    val details: InfoDetails? = null,
    val currentRef: InspectRef? = null
)
