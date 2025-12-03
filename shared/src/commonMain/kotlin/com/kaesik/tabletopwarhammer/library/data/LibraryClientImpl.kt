package com.kaesik.tabletopwarhammer.library.data

import com.kaesik.tabletopwarhammer.core.data.handleException
import com.kaesik.tabletopwarhammer.core.data.library.LibraryDelta
import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.core.data.library.dto.AttributeDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.CareerDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.CareerPathDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.ClassDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.DeletionLogDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.ItemDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.QualityFlawDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.SkillDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.SpeciesDto
import com.kaesik.tabletopwarhammer.core.data.library.dto.TalentDto
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toAttributeItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toCareerItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toCareerPathItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toClassItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toItemItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toQualityFlawItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toSkillItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toSpeciesItem
import com.kaesik.tabletopwarhammer.core.data.library.mappers.toTalentItem
import com.kaesik.tabletopwarhammer.core.data.remote.SupabaseClient
import com.kaesik.tabletopwarhammer.core.domain.library.LibraryLocalDataSource
import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.core.domain.util.DataException
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryClient
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.datetime.Instant

class LibraryClientImpl(
    private val local: LibraryLocalDataSource
) : LibraryClient {

    private val supabaseClient = SupabaseClient.supabaseClient

    private var libraryListCache: List<LibraryItem> = emptyList()

    override suspend fun getLibraryList(
        fromTable: LibraryEnum
    ): List<LibraryItem> {
        return try {
            val list: List<LibraryItem> = when (fromTable) {
                LibraryEnum.ATTRIBUTE -> local.getAllAttributes()
                LibraryEnum.CAREER -> local.getAllCareers()
                LibraryEnum.CAREER_PATH -> local.getAllCareerPaths()
                LibraryEnum.CLASS -> local.getAllClasses()
                LibraryEnum.ITEM -> local.getAllItems()
                LibraryEnum.QUALITY_FLAW -> local.getAllQualityFlaws()
                LibraryEnum.SKILL -> local.getAllSkills()
                LibraryEnum.SPECIES -> local.getAllSpecies()
                LibraryEnum.TALENT -> local.getAllTalents()
            }
            libraryListCache = list
            list
        } catch (e: Exception) {
            println("Error fetching library list from local: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getLibraryItem(
        fromTable: LibraryEnum,
        itemId: String
    ): LibraryItem {
        return try {
            val list = getLibraryList(fromTable)
            list.find { it.id == itemId }
                ?: throw DataException(DataError.Network.UNKNOWN)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun getLibraryDelta(
        fromTable: LibraryEnum,
        sinceEpochMs: String?
    ): LibraryDelta {
        println("DELTA [$fromTable] sinceEpochMs=$sinceEpochMs")

        return try {
            val tableName = fromTable.tableName

            val sinceIso = sinceEpochMs
                ?.toLongOrNull()
                ?.let { lastMs ->
                    Instant.fromEpochMilliseconds(lastMs + 1).toString()
                }

            println("DELTA [$fromTable] sinceIso=$sinceIso")

            val updatedDtos = if (sinceIso == null) {
                supabaseClient.from(tableName)
                    .select()
            } else {
                supabaseClient.from(tableName)
                    .select {
                        filter {
                            gt("updated_at", sinceIso)
                        }
                    }
            }

            val (items, updatedInstants) = when (fromTable) {

                LibraryEnum.ATTRIBUTE -> {
                    val dtos = updatedDtos.decodeList<AttributeDto>()
                    val items = dtos.map { it.toAttributeItem() }
                    val instants = dtos.mapNotNull { dto ->
                        dto.updatedAt?.let { Instant.parse(it) }
                    }
                    items to instants
                }

                LibraryEnum.CAREER -> {
                    val dtos = updatedDtos.decodeList<CareerDto>()
                    val items = dtos.map { it.toCareerItem() }
                    val instants = dtos.mapNotNull { dto ->
                        dto.updatedAt?.let { Instant.parse(it) }
                    }
                    items to instants
                }

                LibraryEnum.CAREER_PATH -> {
                    val dtos = updatedDtos.decodeList<CareerPathDto>()
                    val items = dtos.map { it.toCareerPathItem() }
                    val instants = dtos.mapNotNull { dto ->
                        dto.updatedAt?.let { Instant.parse(it) }
                    }
                    items to instants
                }

                LibraryEnum.CLASS -> {
                    val dtos = updatedDtos.decodeList<ClassDto>()
                    val items = dtos.map { it.toClassItem() }
                    val instants = dtos.mapNotNull { dto ->
                        dto.updatedAt?.let { Instant.parse(it) }
                    }
                    items to instants
                }

                LibraryEnum.ITEM -> {
                    val dtos = updatedDtos.decodeList<ItemDto>()
                    val items = dtos.map { it.toItemItem() }
                    val instants = dtos.mapNotNull { dto ->
                        dto.updatedAt?.let { Instant.parse(it) }
                    }
                    items to instants
                }

                LibraryEnum.QUALITY_FLAW -> {
                    val dtos = updatedDtos.decodeList<QualityFlawDto>()
                    val items = dtos.map { it.toQualityFlawItem() }
                    val instants = dtos.mapNotNull { dto ->
                        dto.updatedAt?.let { Instant.parse(it) }
                    }
                    items to instants
                }

                LibraryEnum.SKILL -> {
                    val dtos = updatedDtos.decodeList<SkillDto>()
                    val items = dtos.map { it.toSkillItem() }
                    val instants = dtos.mapNotNull { dto ->
                        dto.updatedAt?.let { Instant.parse(it) }
                    }
                    items to instants
                }

                LibraryEnum.SPECIES -> {
                    val dtos = updatedDtos.decodeList<SpeciesDto>()
                    val items = dtos.map { it.toSpeciesItem() }
                    val instants = dtos.mapNotNull { dto ->
                        dto.updatedAt?.let { Instant.parse(it) }
                    }
                    items to instants
                }

                LibraryEnum.TALENT -> {
                    val dtos = updatedDtos.decodeList<TalentDto>()
                    val items = dtos.map { it.toTalentItem() }
                    val instants = dtos.mapNotNull { dto ->
                        dto.updatedAt?.let { Instant.parse(it) }
                    }
                    items to instants
                }
            }

            val deletedIds = if (sinceIso == null) {
                emptyList()
            } else {
                supabaseClient
                    .from("deletion_log")
                    .select(
                        columns = Columns.list("entity_id", "deleted_at")
                    ) {
                        filter {
                            eq("table_name", tableName)
                            gt("deleted_at", sinceIso)
                        }
                    }
                    .decodeList<DeletionLogDto>()
                    .map { it.entityId }
            }

            println("DELTA [$fromTable] items=${items.size}, timestamps=${updatedInstants.size}")
            println("DELTA [$fromTable] deletedIds=${deletedIds.size}")

            val maxInstant = updatedInstants.maxOrNull()
            val maxEpochMs = when {
                maxInstant != null -> maxInstant.toEpochMilliseconds()
                sinceEpochMs != null -> sinceEpochMs.toLongOrNull() ?: 0L
                else -> 0L
            }

            println("DELTA [$fromTable] maxTimestamp=$maxEpochMs")

            LibraryDelta(
                items = items,
                deletedIds = deletedIds,
                maxTimestampEpochMs = maxEpochMs
            )

        } catch (e: Exception) {
            handleException(e)
        }
    }
}
