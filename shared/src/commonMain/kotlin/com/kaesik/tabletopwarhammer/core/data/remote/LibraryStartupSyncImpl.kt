package com.kaesik.tabletopwarhammer.core.data.remote

import com.kaesik.tabletopwarhammer.core.domain.library.LibraryLocalDataSource
import com.kaesik.tabletopwarhammer.core.domain.remote.LibraryStartupSync
import com.kaesik.tabletopwarhammer.core.domain.remote.SupabaseLibrarySyncManager

class LibraryStartupSyncImpl(
    private val local: LibraryLocalDataSource,
    private val remoteSync: SupabaseLibrarySyncManager
) : LibraryStartupSync {

    override suspend fun run() {
        val hasLocalData = local.getAllAttributes().isNotEmpty()

        if (!hasLocalData) {
            remoteSync.syncAll()
        } else {
            try {
                remoteSync.syncAll()
            } catch (t: Throwable) {
                println("LibraryStartupSync: sync failed, using local cache: ${t.message}")
            }
        }
    }
}
