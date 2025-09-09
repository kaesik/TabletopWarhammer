package com.kaesik.tabletopwarhammer.core.domain.info

import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.core.domain.library.LibraryDataSource

class InfoRepository(
    private val library: LibraryDataSource,
    private val mappers: InfoMappers
) {
    fun getDetails(ref: InspectRef): InfoDetails? = when (ref.type) {
        LibraryEnum.TALENT -> runCatching { library.getTalent(ref.key) }.getOrNull()
            ?.let(mappers::fromTalent)

        LibraryEnum.SKILL -> runCatching { library.getSkill(ref.key) }.getOrNull()
            ?.let(mappers::fromSkill)

        LibraryEnum.ITEM -> runCatching { library.getItem(ref.key) }.getOrNull()
            ?.let(mappers::fromItem)

        LibraryEnum.ATTRIBUTE -> runCatching { library.getAttribute(ref.key) }.getOrNull()
            ?.let(mappers::fromAttribute)

        LibraryEnum.CAREER -> runCatching { library.getCareer(ref.key) }.getOrNull()
            ?.let(mappers::fromCareer)

        LibraryEnum.CAREER_PATH -> runCatching { library.getCareerPath(ref.key) }.getOrNull()
            ?.let(mappers::fromCareerPath)

        LibraryEnum.CLASS -> runCatching { library.getClass(ref.key) }.getOrNull()
            ?.let(mappers::fromClass)

        LibraryEnum.SPECIES -> runCatching { library.getSpecies(ref.key) }.getOrNull()
            ?.let(mappers::fromSpecies)

        LibraryEnum.QUALITY_FLAW -> runCatching { library.getQualityFlaw(ref.key) }.getOrNull()
            ?.let(mappers::fromQualityFlaw)
    }
}
