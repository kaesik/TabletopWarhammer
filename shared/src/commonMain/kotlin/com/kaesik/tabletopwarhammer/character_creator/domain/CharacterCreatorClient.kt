package com.kaesik.tabletopwarhammer.character_creator.domain

import com.kaesik.tabletopwarhammer.core.domain.library.items.AttributeItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ClassItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ItemItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem

interface CharacterCreatorClient {

    suspend fun getSpecies(): List<SpeciesItem>
    suspend fun getClasses(): List<ClassItem>
    suspend fun getCareers(): List<CareerItem>
    suspend fun getSkills(): List<SkillItem>
    suspend fun getTalents(): List<TalentItem>
    suspend fun getTrappings(): List<ItemItem>
    suspend fun getAttributes(): List<AttributeItem>

}
