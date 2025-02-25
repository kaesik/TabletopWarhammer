package com.kaesik.tabletopwarhammer.library.presentation

import com.kaesik.tabletopwarhammer.library.domain.library.LibraryError

data class LibraryState(
    val error: LibraryError? = null
)
