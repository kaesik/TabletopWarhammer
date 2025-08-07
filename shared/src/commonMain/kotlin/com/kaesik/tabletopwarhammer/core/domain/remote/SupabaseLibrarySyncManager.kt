package com.kaesik.tabletopwarhammer.core.domain.remote

interface SupabaseLibrarySyncManager {
    suspend fun syncAll()
    suspend fun syncAttributes()
    suspend fun syncCareers()
    suspend fun syncCareerPaths()
    suspend fun syncClasses()
    suspend fun syncItems()
    suspend fun syncQualityFlaws()
    suspend fun syncSkills()
    suspend fun syncSpecies()
    suspend fun syncTalents()
}
