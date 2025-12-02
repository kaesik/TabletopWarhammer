package com.kaesik.tabletopwarhammer.core.data.remote

import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.core.data.local.SyncStateDataSource
import com.kaesik.tabletopwarhammer.core.domain.library.LibraryDataSource
import com.kaesik.tabletopwarhammer.core.domain.library.items.AttributeItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerPathItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ClassItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ItemItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.QualityFlawItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem
import com.kaesik.tabletopwarhammer.core.domain.remote.SupabaseLibrarySyncManager
import com.kaesik.tabletopwarhammer.database.TabletopWarhammerDatabase
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryClient

class SupabaseLibrarySyncManagerImpl(
    private val libraryClient: LibraryClient,
    private val library: LibraryDataSource,
    database: TabletopWarhammerDatabase,
    private val syncState: SyncStateDataSource
) : SupabaseLibrarySyncManager {

    private val q = database.tabletopQueries

    override suspend fun syncAll() {
        syncAttributes()
        syncClasses()
        syncCareers()
        syncCareerPaths()
        syncItems()
        syncQualityFlaws()
        syncSkills()
        syncSpecies()
        syncTalents()
    }

    override suspend fun syncAttributes() {
        val key = LibraryEnum.ATTRIBUTE.tableName
        val since = syncState.getLastSync(key)
        val delta = libraryClient.getLibraryDelta(LibraryEnum.ATTRIBUTE, since)
        println("SYNC [ATTRIBUTE] start, since=${since}")

        if (delta.items.isEmpty() && delta.deletedIds.isEmpty()) return

        delta.deletedIds.forEach { id ->
            println("SYNC [ATTRIBUTE] deleting id=$id")
            q.deleteAttributeById(id)
        }
        delta.items.filterIsInstance<AttributeItem>().forEach { item ->
            println("SYNC [ATTRIBUTE] upserting id=${item.id}")
            library.insertAttribute(item)
        }
        println("SYNC [ATTRIBUTE] delta: items=${delta.items.size}, deleted=${delta.deletedIds.size}")

        syncState.setLastSync(key, delta.maxTimestampEpochMs.toString())
        println("SYNC [ATTRIBUTE] finished, newLastSync=${delta.maxTimestampEpochMs}")

    }

    override suspend fun syncCareers() {
        val key = LibraryEnum.CAREER.tableName
        val since = syncState.getLastSync(key)
        val delta = libraryClient.getLibraryDelta(LibraryEnum.CAREER, since)

        if (delta.items.isEmpty() && delta.deletedIds.isEmpty()) return

        delta.deletedIds.forEach { id ->
            q.deleteCareerById(id)
        }
        delta.items.filterIsInstance<CareerItem>().forEach { item ->
            library.insertCareer(item)
        }

        syncState.setLastSync(key, delta.maxTimestampEpochMs.toString())
    }

    override suspend fun syncCareerPaths() {
        val key = LibraryEnum.CAREER_PATH.tableName
        val since = syncState.getLastSync(key)
        val delta = libraryClient.getLibraryDelta(LibraryEnum.CAREER_PATH, since)

        if (delta.items.isEmpty() && delta.deletedIds.isEmpty()) return

        delta.deletedIds.forEach { id ->
            q.deleteCareerPathById(id)
        }
        delta.items.filterIsInstance<CareerPathItem>().forEach { item ->
            library.insertCareerPath(item)
        }

        syncState.setLastSync(key, delta.maxTimestampEpochMs.toString())
    }

    override suspend fun syncClasses() {
        val key = LibraryEnum.CLASS.tableName
        val since = syncState.getLastSync(key)
        val delta = libraryClient.getLibraryDelta(LibraryEnum.CLASS, since)

        if (delta.items.isEmpty() && delta.deletedIds.isEmpty()) return

        delta.deletedIds.forEach { id ->
            q.deleteClassById(id)
        }
        delta.items.filterIsInstance<ClassItem>().forEach { item ->
            library.insertClass(item)
        }

        syncState.setLastSync(key, delta.maxTimestampEpochMs.toString())
    }

    override suspend fun syncItems() {
        val key = LibraryEnum.ITEM.tableName
        val since = syncState.getLastSync(key)
        val delta = libraryClient.getLibraryDelta(LibraryEnum.ITEM, since)

        if (delta.items.isEmpty() && delta.deletedIds.isEmpty()) return

        delta.deletedIds.forEach { id ->
            q.deleteItemById(id)
        }
        delta.items.filterIsInstance<ItemItem>().forEach { item ->
            library.insertItem(item)
        }

        syncState.setLastSync(key, delta.maxTimestampEpochMs.toString())
    }

    override suspend fun syncQualityFlaws() {
        val key = LibraryEnum.QUALITY_FLAW.tableName
        val since = syncState.getLastSync(key)
        val delta = libraryClient.getLibraryDelta(LibraryEnum.QUALITY_FLAW, since)

        if (delta.items.isEmpty() && delta.deletedIds.isEmpty()) return

        delta.deletedIds.forEach { id ->
            q.deleteQualityFlawById(id)
        }
        delta.items.filterIsInstance<QualityFlawItem>().forEach { item ->
            library.insertQualityFlaw(item)
        }

        syncState.setLastSync(key, delta.maxTimestampEpochMs.toString())
    }

    override suspend fun syncSkills() {
        val key = LibraryEnum.SKILL.tableName
        val since = syncState.getLastSync(key)
        val delta = libraryClient.getLibraryDelta(LibraryEnum.SKILL, since)

        if (delta.items.isEmpty() && delta.deletedIds.isEmpty()) return

        delta.deletedIds.forEach { id ->
            q.deleteSkillById(id)
        }
        delta.items.filterIsInstance<SkillItem>().forEach { item ->
            library.insertSkill(item)
        }

        syncState.setLastSync(key, delta.maxTimestampEpochMs.toString())
    }

    override suspend fun syncSpecies() {
        val key = LibraryEnum.SPECIES.tableName
        val since = syncState.getLastSync(key)
        val delta = libraryClient.getLibraryDelta(LibraryEnum.SPECIES, since)

        if (delta.items.isEmpty() && delta.deletedIds.isEmpty()) return

        delta.deletedIds.forEach { id ->
            q.deleteSpeciesById(id)
        }
        delta.items.filterIsInstance<SpeciesItem>().forEach { item ->
            library.insertSpecies(item)
        }

        syncState.setLastSync(key, delta.maxTimestampEpochMs.toString())
    }

    override suspend fun syncTalents() {
        val key = LibraryEnum.TALENT.tableName
        val since = syncState.getLastSync(key)
        val delta = libraryClient.getLibraryDelta(LibraryEnum.TALENT, since)

        if (delta.items.isEmpty() && delta.deletedIds.isEmpty()) return

        delta.deletedIds.forEach { id ->
            q.deleteTalentById(id)
        }
        delta.items.filterIsInstance<TalentItem>().forEach { item ->
            library.insertTalent(item)
        }

        syncState.setLastSync(key, delta.maxTimestampEpochMs.toString())
    }
}
