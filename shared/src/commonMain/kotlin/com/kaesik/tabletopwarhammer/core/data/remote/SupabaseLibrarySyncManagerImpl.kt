package com.kaesik.tabletopwarhammer.core.data.remote

import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum
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
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryClient

class SupabaseLibrarySyncManagerImpl(
    private val libraryClient: LibraryClient,
    private val library: LibraryDataSource
) : SupabaseLibrarySyncManager {

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
        val data = libraryClient.getLibraryList(LibraryEnum.ATTRIBUTE)
        data.filterIsInstance<AttributeItem>().forEach {
            library.insertAttribute(it)
        }
    }

    override suspend fun syncCareers() {
        val data = libraryClient.getLibraryList(LibraryEnum.CAREER)
        data.filterIsInstance<CareerItem>().forEach {
            library.insertCareer(it)
        }
    }

    override suspend fun syncCareerPaths() {
        val data = libraryClient.getLibraryList(LibraryEnum.CAREER_PATH)
        data.filterIsInstance<CareerPathItem>().forEach {
            library.insertCareerPath(it)
        }
    }

    override suspend fun syncClasses() {
        val data = libraryClient.getLibraryList(LibraryEnum.CLASS)
        data.filterIsInstance<ClassItem>().forEach {
            library.insertClass(it)
        }
    }

    override suspend fun syncItems() {
        val data = libraryClient.getLibraryList(LibraryEnum.ITEM)
        data.filterIsInstance<ItemItem>().forEach {
            library.insertItem(it)
        }
    }

    override suspend fun syncQualityFlaws() {
        val data = libraryClient.getLibraryList(LibraryEnum.QUALITY_FLAW)
        data.filterIsInstance<QualityFlawItem>().forEach {
            library.insertQualityFlaw(it)
        }
    }

    override suspend fun syncSkills() {
        val data = libraryClient.getLibraryList(LibraryEnum.SKILL)
        data.filterIsInstance<SkillItem>().forEach {
            library.insertSkill(it)
        }
    }

    override suspend fun syncSpecies() {
        val data = libraryClient.getLibraryList(LibraryEnum.SPECIES)
        data.filterIsInstance<SpeciesItem>().forEach {
            library.insertSpecies(it)
        }
    }

    override suspend fun syncTalents() {
        val data = libraryClient.getLibraryList(LibraryEnum.TALENT)
        data.filterIsInstance<TalentItem>().forEach {
            library.insertTalent(it)
        }
    }
}
