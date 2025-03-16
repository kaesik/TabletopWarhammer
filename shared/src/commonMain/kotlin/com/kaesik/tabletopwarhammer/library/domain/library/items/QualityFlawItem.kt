package com.kaesik.tabletopwarhammer.library.domain.library.items

data class QualityFlawItem(
    override val id: String,
    override val name: String,
    val group: String?,
    val description: String?,
    val isQuality: Boolean?,
    val source: String?,
    val page: Int?,
): LibraryItem
