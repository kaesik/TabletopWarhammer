package com.kaesik.tabletopwarhammer.core.domain.character

import com.kaesik.tabletopwarhammer.core.domain.util.CommonFlow
import kotlin.coroutines.CoroutineContext

interface CharacterDataSource {
    fun getCharacter(context: CoroutineContext): CommonFlow<List<CharacterItem>>
    suspend fun insertCharacter(characterItem: CharacterItem)
}
