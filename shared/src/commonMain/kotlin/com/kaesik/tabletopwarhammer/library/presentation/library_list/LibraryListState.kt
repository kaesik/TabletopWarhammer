package com.kaesik.tabletopwarhammer.library.presentation.library_list

data class LibraryListState (
    val error: String? = null,
    val name: String? = null,
    val result: List<String> = emptyList(),
    val isLoading: Boolean = false
)
