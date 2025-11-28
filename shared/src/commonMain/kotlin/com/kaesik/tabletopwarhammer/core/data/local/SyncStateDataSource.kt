package com.kaesik.tabletopwarhammer.core.data.local

import com.kaesik.tabletopwarhammer.database.TabletopWarhammerDatabase

interface SyncStateDataSource {
    fun getLastSync(table: String): Long?
    suspend fun setLastSync(table: String, epochMs: Long)
}

class SqlDelightSyncStateDataSource(
    database: TabletopWarhammerDatabase
) : SyncStateDataSource {

    private val q = database.tabletopQueries

    override fun getLastSync(table: String): Long? {
        return q.getSyncState(table).executeAsOneOrNull()
    }

    override suspend fun setLastSync(table: String, epochMs: Long) {
        q.setSyncState(table, epochMs)
    }
}
