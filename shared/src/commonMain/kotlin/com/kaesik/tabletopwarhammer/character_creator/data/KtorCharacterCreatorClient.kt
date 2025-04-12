package com.kaesik.tabletopwarhammer.character_creator.data

import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.library.data.Const
import com.kaesik.tabletopwarhammer.library.data.library.handleException
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

class KtorCharacterCreatorClient : CharacterCreatorClient {
    private val supabaseClient = createSupabaseClient(
        supabaseKey = Const.SUPABASE_ANON_KEY,
        supabaseUrl = Const.SUPABASE_URL
    ) {
        install(Postgrest)
    }

    override suspend fun getSpecies(): List<String> {
        return try {
            listOf(
                "Human",
                "Elf",
                "Dwarf",
                "Halfling",
            )
        } catch (e: Exception) {
            println("Error fetching species list: ${e.message}")
            handleException(e)
        }
    }
}
