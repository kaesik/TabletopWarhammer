package com.kaesik.tabletopwarhammer.core.data.library

data class LibraryDelta(
    val items: List<Any>,
    val deletedIds: List<String>,
    val maxTimestampEpochMs: Long
)
