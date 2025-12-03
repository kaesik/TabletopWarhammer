package com.kaesik.tabletopwarhammer.core.domain.local

interface SyncStateDataSource {
    fun getLastSync(table: String): String?
    suspend fun setLastSync(table: String, epochMs: String)
}
