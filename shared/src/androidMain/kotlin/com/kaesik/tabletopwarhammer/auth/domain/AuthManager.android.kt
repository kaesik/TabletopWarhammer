@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.kaesik.tabletopwarhammer.auth.domain

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.kaesik.tabletopwarhammer.core.data.remote.SupabaseClient
import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.core.domain.util.Resource
import com.kaesik.tabletopwarhammer.library.data.Const.GOOGLE_WEB_CLIENT_ID
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.exceptions.RestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.security.MessageDigest
import java.util.UUID

actual class AuthManager(private val context: Context) {

    actual fun loginGoogleUser(): Flow<Resource<Unit, DataError.Network>> = flow {
        val hashedNonce = createNonce()

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(GOOGLE_WEB_CLIENT_ID)
            .setAutoSelectEnabled(false)
            .setNonce(hashedNonce)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val credentialManager = CredentialManager.create(context)

        try {
            val result = withContext(Dispatchers.IO) {
                credentialManager.getCredential(
                    context = context,
                    request = request
                )
            }

            val googleIdTokenCredential = GoogleIdTokenCredential
                .createFrom(result.credential.data)

            val googleIdToken = googleIdTokenCredential.idToken

            SupabaseClient.supabaseClient.auth.signInWith(IDToken) {
                idToken = googleIdToken
                provider = Google
            }

            emit(Resource.Success(Unit))

        } catch (e: Exception) {
            Log.e("GoogleAuth", e.localizedMessage ?: "Unknown error")
            Log.e(
                "GoogleAuth",
                "Error class: ${e::class.java.name}, message: ${e.localizedMessage}",
                e
            )

            val error = when (e) {
                is NoSuchElementException,
                is GetCredentialException -> DataError.Network.UNAUTHORIZED

                is SocketTimeoutException -> DataError.Network.REQUEST_TIMEOUT
                is UnknownHostException -> DataError.Network.NO_INTERNET
                is RestException -> mapRestError(e.statusCode)
                else -> DataError.Network.UNKNOWN
            }

            emit(Resource.Error(error))
        }
    }

    private fun mapRestError(statusCode: Int): DataError.Network = when (statusCode) {
        401 -> DataError.Network.UNAUTHORIZED
        409 -> DataError.Network.CONFLICT
        413 -> DataError.Network.PAYLOAD_TOO_LARGE
        429 -> DataError.Network.TOO_MANY_REQUESTS
        in 500..599 -> DataError.Network.SERVER_ERROR
        else -> DataError.Network.UNKNOWN
    }

    private fun createNonce(): String {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}
