package com.kaesik.tabletopwarhammer.library.presentation.library

import com.kaesik.tabletopwarhammer.library.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryError

data class LibraryState(
    val error: LibraryError? = null,
    val isLoading: Boolean = false,

    val result: Enum<LibraryEnum>? = null,
)
