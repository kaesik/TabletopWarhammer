package com.kaesik.tabletopwarhammer.core.data.local

interface SyncStateDataSource {
    fun getLastSync(table: String): String?
    suspend fun setLastSync(table: String, epochMs: String)
}
