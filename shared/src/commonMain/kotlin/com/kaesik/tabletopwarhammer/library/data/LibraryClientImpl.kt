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
import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.core.domain.util.DataException
import com.kaesik.tabletopwarhammer.di.libraryList
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryClient
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.datetime.Instant

class LibraryClientImpl : LibraryClient {
    private val supabaseClient = SupabaseClient.supabaseClient

    override suspend fun getLibraryList(
        fromTable: LibraryEnum
    ): List<LibraryItem> {
        return try {
            val supabaseList = supabaseClient.from(fromTable.tableName).select()
            when (fromTable) {
                LibraryEnum.ATTRIBUTE -> {
                    supabaseList
                        .decodeList<AttributeDto>()
                        .map { it.toAttributeItem() }
                }

                LibraryEnum.CAREER -> {
                    supabaseList
                        .decodeList<CareerDto>()
                        .map { it.toCareerItem() }
                }

                LibraryEnum.CAREER_PATH -> {
                    supabaseList
                        .decodeList<CareerPathDto>()
                        .map { it.toCareerPathItem() }
                }

                LibraryEnum.CLASS -> {
                    supabaseList
                        .decodeList<ClassDto>()
                        .map { it.toClassItem() }
                }

                LibraryEnum.ITEM -> {
                    supabaseList
                        .decodeList<ItemDto>()
                        .map { it.toItemItem() }
                }

                LibraryEnum.QUALITY_FLAW -> {
                    supabaseList
                        .decodeList<QualityFlawDto>()
                        .map { it.toQualityFlawItem() }
                }

                LibraryEnum.SKILL -> {
                    supabaseList
                        .decodeList<SkillDto>()
                        .map { it.toSkillItem() }
                }

                LibraryEnum.SPECIES -> {
                    supabaseList
                        .decodeList<SpeciesDto>()
                        .map { it.toSpeciesItem() }
                }

                LibraryEnum.TALENT -> {
                    supabaseList
                        .decodeList<TalentDto>()
                        .map { it.toTalentItem() }
                }

            }
        } catch (e: Exception) {
            println("Error fetching library list: ${e.message}")
            handleException(e)
        }

    }

    override suspend fun getLibraryItem(
        fromTable: LibraryEnum,
        itemId: String
    ): LibraryItem {
        return try {
            libraryList = getLibraryList(fromTable)
            libraryList.find { it.id == itemId }
                ?: throw DataException(DataError.Network.UNKNOWN)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun getLibraryDelta(
        fromTable: LibraryEnum,
        sinceEpochMs: String?
    ): LibraryDelta {
        return try {
            val tableName = fromTable.tableName
            val sinceIso =
                sinceEpochMs?.let { Instant.fromEpochMilliseconds(it.toLong()).toString() }
            println("DELTA [$fromTable] sinceEpochMs=$sinceEpochMs, sinceIso=$sinceIso")

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

            val items: List<LibraryItem>
            val updatedTimestamps: List<Long> = when (fromTable) {

                LibraryEnum.ATTRIBUTE -> {
                    val dtos = updatedDtos.decodeList<AttributeDto>()
                    items = dtos.map { it.toAttributeItem() }
                    dtos.mapNotNull {
                        it.updatedAt?.let { ts ->
                            Instant.parse(ts).toEpochMilliseconds()
                        }
                    }
                }

                LibraryEnum.CAREER -> {
                    val dtos = updatedDtos.decodeList<CareerDto>()
                    items = dtos.map { it.toCareerItem() }
                    dtos.mapNotNull {
                        it.updatedAt?.let { ts ->
                            Instant.parse(ts).toEpochMilliseconds()
                        }
                    }
                }

                LibraryEnum.CAREER_PATH -> {
                    val dtos = updatedDtos.decodeList<CareerPathDto>()
                    items = dtos.map { it.toCareerPathItem() }
                    dtos.mapNotNull {
                        it.updatedAt?.let { ts ->
                            Instant.parse(ts).toEpochMilliseconds()
                        }
                    }
                }

                LibraryEnum.CLASS -> {
                    val dtos = updatedDtos.decodeList<ClassDto>()
                    items = dtos.map { it.toClassItem() }
                    dtos.mapNotNull {
                        it.updatedAt?.let { ts ->
                            Instant.parse(ts).toEpochMilliseconds()
                        }
                    }
                }

                LibraryEnum.ITEM -> {
                    val dtos = updatedDtos.decodeList<ItemDto>()
                    items = dtos.map { it.toItemItem() }
                    dtos.mapNotNull {
                        it.updatedAt?.let { ts ->
                            Instant.parse(ts).toEpochMilliseconds()
                        }
                    }
                }

                LibraryEnum.QUALITY_FLAW -> {
                    val dtos = updatedDtos.decodeList<QualityFlawDto>()
                    items = dtos.map { it.toQualityFlawItem() }
                    dtos.mapNotNull {
                        it.updatedAt?.let { ts ->
                            Instant.parse(ts).toEpochMilliseconds()
                        }
                    }
                }

                LibraryEnum.SKILL -> {
                    val dtos = updatedDtos.decodeList<SkillDto>()
                    items = dtos.map { it.toSkillItem() }
                    dtos.mapNotNull {
                        it.updatedAt?.let { ts ->
                            Instant.parse(ts).toEpochMilliseconds()
                        }
                    }
                }

                LibraryEnum.SPECIES -> {
                    val dtos = updatedDtos.decodeList<SpeciesDto>()
                    items = dtos.map { it.toSpeciesItem() }
                    dtos.mapNotNull {
                        it.updatedAt?.let { ts ->
                            Instant.parse(ts).toEpochMilliseconds()
                        }
                    }
                }

                LibraryEnum.TALENT -> {
                    val dtos = updatedDtos.decodeList<TalentDto>()
                    items = dtos.map { it.toTalentItem() }
                    dtos.mapNotNull {
                        it.updatedAt?.let { ts ->
                            Instant.parse(ts).toEpochMilliseconds()
                        }
                    }
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
            println("DELTA [$fromTable] updatedDtos=${updatedDtos}")
            println("DELTA [$fromTable] items=${items.size}, timestamps=${updatedTimestamps.size}")
            println("DELTA [$fromTable] deletedIds=${deletedIds.size}")

            val maxTs = updatedTimestamps.maxOrNull() ?: sinceEpochMs?.toLong() ?: 0L
            println("DELTA [$fromTable] maxTimestamp=${maxTs}")

            LibraryDelta(
                items = items,
                deletedIds = deletedIds,
                maxTimestampEpochMs = maxTs
            )

        } catch (e: Exception) {
            handleException(e)
        }
    }

}
