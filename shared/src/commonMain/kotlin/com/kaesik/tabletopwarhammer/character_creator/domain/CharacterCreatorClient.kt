package com.kaesik.tabletopwarhammer.character_creator.domain

interface CharacterCreatorClient {

    suspend fun getSpecies(): List<String>
}
