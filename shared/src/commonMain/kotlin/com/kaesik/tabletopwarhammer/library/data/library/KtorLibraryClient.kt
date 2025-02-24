package com.kaesik.tabletopwarhammer.library.data.library

import com.kaesik.tabletopwarhammer.library.data.Const
import com.kaesik.tabletopwarhammer.library.data.library.dto.AttributeDto
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryClient
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryError
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryException
import com.kaesik.tabletopwarhammer.library.domain.library.items.AttributeItem
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.HttpRequestTimeoutException
import com.kaesik.tabletopwarhammer.library.data.library.mappers.toAttributeItem
import com.kaesik.tabletopwarhammer.library.data.library.dto.CareerDto
import com.kaesik.tabletopwarhammer.library.data.library.mappers.toCareerItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.CareerPathItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.ClassItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.ItemItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.QualityFlawItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.SpeciesItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.TalentItem

class KtorLibraryClient : LibraryClient {
    private val supabaseClient = createSupabaseClient(
        supabaseKey = Const.SUPABASE_ANON_KEY,
        supabaseUrl = Const.SUPABASE_URL
    ) {
        install(Postgrest)
    }

    override suspend fun getAttributes(): List<AttributeItem> {
        return try {
            supabaseClient
                .from("attribute")
                .select()
                .decodeList<AttributeDto>()
                .map { it.toAttributeItem() }

        } catch (e: ClientRequestException) {
//            println(e)
            throw LibraryException(LibraryError.CLIENT_ERROR)
        } catch (e: ServerResponseException) {
//            println(e)
            throw LibraryException(LibraryError.SERVER_ERROR)
        } catch (e: HttpRequestTimeoutException) {
//            println(e)
            throw LibraryException(LibraryError.SERVICE_UNAVAILABLE)
        } catch (e: Exception) {
//            println(e)
            throw LibraryException(LibraryError.UNKNOWN_ERROR)
        }
    }

    override suspend fun getCareers(): List<CareerItem> {
        return try {
            supabaseClient
                .from("career")
                .select()
                .decodeList<CareerDto>()
                .map { it.toCareerItem() }

        } catch (e: ClientRequestException) {
//            println(e)
            throw LibraryException(LibraryError.CLIENT_ERROR)
        } catch (e: ServerResponseException) {
//            println(e)
            throw LibraryException(LibraryError.SERVER_ERROR)
        } catch (e: HttpRequestTimeoutException) {
//            println(e)
            throw LibraryException(LibraryError.SERVICE_UNAVAILABLE)
        } catch (e: Exception) {
//            println(e)
            throw LibraryException(LibraryError.UNKNOWN_ERROR)
        }
    }

    override suspend fun getCareerPaths(): List<CareerPathItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getClasses(): List<ClassItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getItems(): List<ItemItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getQualitiesFlaws(): List<QualityFlawItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getSkills(): List<SkillItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getSpecies(): List<SpeciesItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getTalents(): List<TalentItem> {
        TODO("Not yet implemented")
    }
}
