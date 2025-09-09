package com.kaesik.tabletopwarhammer.core.domain.info

import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum

data class InspectRef(
    val type: LibraryEnum,
    val key: String
)

data class InfoDetails(
    val title: String,
    val subtitle: String? = null,
    val description: String,
    val source: String? = null,
    val page: Int? = null
)
