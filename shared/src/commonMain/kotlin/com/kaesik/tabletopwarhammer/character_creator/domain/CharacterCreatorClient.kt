package com.kaesik.tabletopwarhammer.character_creator.domain

interface CharacterCreatorClient {

    suspend fun getSpecies(): List<String>
    suspend fun getClasses(): List<String>
    suspend fun getCareers(): List<String>
    suspend fun getSkills(): List<String>
    suspend fun getTalents(): List<String>
    suspend fun getTrappings(): List<String>
    suspend fun getAttributes(): List<String>

}
