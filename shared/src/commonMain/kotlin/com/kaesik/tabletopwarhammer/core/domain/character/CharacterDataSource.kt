package com.kaesik.tabletopwarhammer.core.domain.character

import com.kaesik.tabletopwarhammer.core.domain.util.CommonFlow
import kotlin.coroutines.CoroutineContext

interface CharacterDataSource {
    fun getAllCharacters(): List<CharacterItem>
    fun getCharacter(context: CoroutineContext): CommonFlow<List<CharacterItem>>
    suspend fun deleteCharacter(id: Long)
    suspend fun insertCharacter(characterItem: CharacterItem)
}
