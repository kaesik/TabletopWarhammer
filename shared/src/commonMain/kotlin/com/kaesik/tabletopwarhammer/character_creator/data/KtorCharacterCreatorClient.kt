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

    override suspend fun getAttributes(): List<String> {
        return try {
            listOf(
                "Weapon Skill",
                "Ballistic Skill",
                "Strength",
                "Toughness",
                "Agility",
                "Intelligence",
                "Willpower",
                "Fellowship"
            )
        } catch (e: Exception) {
            println("Error fetching attributes list: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getClasses(): List<String> {
        return try {
            listOf(
                "Warrior",
                "Mage",
                "Rogue",
                "Cleric",
            )
        } catch (e: Exception) {
            println("Error fetching classes list: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getCareers(): List<String> {
        return try {
            listOf(
                "Fighter",
                "Thief",
                "Mage",
                "Priest",
            )
        } catch (e: Exception) {
            println("Error fetching careers list: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getSkills(): List<String> {
        return try {
            listOf(
                "Stealth",
                "Archery",
                "Magic",
                "Healing",
            )
        } catch (e: Exception) {
            println("Error fetching skills list: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getTrappings(): List<String> {
        return try {
            listOf(
                "Sword",
                "Shield",
                "Bow",
                "Staff",
            )
        } catch (e: Exception) {
            println("Error fetching trappings list: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun getTalents(): List<String> {
        return try {
            listOf(
                "Bravery",
                "Agility",
                "Strength",
                "Intelligence",
                "Random Talent",
            )
        } catch (e: Exception) {
            println("Error fetching talents list: ${e.message}")
            handleException(e)
        }
    }
}
