package com.kaesik.tabletopwarhammer.auth.data

import com.kaesik.tabletopwarhammer.auth.domain.AuthClient
import com.kaesik.tabletopwarhammer.core.data.remote.SupabaseClient
import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.core.domain.util.EmptyResult
import com.kaesik.tabletopwarhammer.core.domain.util.Resource
import com.kaesik.tabletopwarhammer.core.domain.util.asEmptyResult
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Email

class AuthClientImpl : AuthClient {

    private val supabaseClient = SupabaseClient.supabaseClient

    override suspend fun login(email: String, password: String): EmptyResult<DataError.Network> {
        return try {
            supabaseClient.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            Resource.Success<Unit, DataError.Network>(Unit).asEmptyResult()
        } catch (e: AuthRestException) {
            Resource.Error<Unit, DataError.Network>(mapSupabaseError(e)).asEmptyResult()
        } catch (e: Exception) {
            Resource.Error<Unit, DataError.Network>(DataError.Network.UNKNOWN).asEmptyResult()
        }
    }

    override suspend fun register(email: String, password: String): EmptyResult<DataError.Network> {
        return try {
            supabaseClient.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            Resource.Success<Unit, DataError.Network>(Unit).asEmptyResult()
        } catch (e: AuthRestException) {
            Resource.Error<Unit, DataError.Network>(mapSupabaseError(e)).asEmptyResult()
        } catch (e: Exception) {
            Resource.Error<Unit, DataError.Network>(DataError.Network.UNKNOWN).asEmptyResult()
        }
    }

    private fun mapSupabaseError(e: AuthRestException): DataError.Network {
        println("Supabase Auth Error: Code=${e.statusCode}, Msg=${e.message}")
        return when (e.statusCode) {
            400 -> if (e.message?.contains("email not confirmed", ignoreCase = true) == true) {
                DataError.Network.EMAIL_NOT_CONFIRMED
            } else {
                DataError.Network.CONFLICT
            }

            401 -> DataError.Network.UNAUTHORIZED
            403 -> DataError.Network.EMAIL_NOT_CONFIRMED
            408 -> DataError.Network.REQUEST_TIMEOUT
            429 -> DataError.Network.TOO_MANY_REQUESTS
            500 -> DataError.Network.SERVER_ERROR
            else -> DataError.Network.UNKNOWN
        }
    }
}
