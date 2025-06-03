package com.kaesik.tabletopwarhammer.main.data.menu

import com.kaesik.tabletopwarhammer.core.data.remote.SupabaseClient
import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.core.domain.util.EmptyResult
import com.kaesik.tabletopwarhammer.core.domain.util.Resource
import com.kaesik.tabletopwarhammer.core.domain.util.asEmptyResult
import com.kaesik.tabletopwarhammer.main.domain.menu.MenuClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException

class MenuClientImpl : MenuClient {
    private val supabaseClient = SupabaseClient.supabaseClient

    override suspend fun logout(): EmptyResult<DataError.Network> {
        return try {
            supabaseClient.auth.signOut()
            Resource.Success<Unit, DataError.Network>(Unit).asEmptyResult()
        } catch (e: AuthRestException) {
            Resource.Error<Unit, DataError.Network>(mapSupabaseError(e)).asEmptyResult()
        } catch (e: Exception) {
            Resource.Error<Unit, DataError.Network>(DataError.Network.UNKNOWN).asEmptyResult()
        }
    }

    private fun mapSupabaseError(e: AuthRestException): DataError.Network = when (e.statusCode) {
        400 -> DataError.Network.CONFLICT
        401 -> DataError.Network.UNAUTHORIZED
        408 -> DataError.Network.REQUEST_TIMEOUT
        429 -> DataError.Network.TOO_MANY_REQUESTS
        500 -> DataError.Network.SERVER_ERROR
        else -> DataError.Network.UNKNOWN
    }
}
