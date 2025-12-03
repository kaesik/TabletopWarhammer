package com.kaesik.tabletopwarhammer.core.data.local

import com.kaesik.tabletopwarhammer.core.domain.local.SyncStateDataSource
import com.kaesik.tabletopwarhammer.database.TabletopWarhammerDatabase

class SqlDelightSyncStateDataSource(
    database: TabletopWarhammerDatabase
) : SyncStateDataSource {

    private val q = database.tabletopQueries

    override fun getLastSync(table: String): String? {
        return q.getSyncState(table).executeAsOneOrNull()
    }

    override suspend fun setLastSync(table: String, epochMs: String) {
        q.setSyncState(table, epochMs)
    }
}
