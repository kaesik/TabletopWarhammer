package com.kaesik.tabletopwarhammer.core.data

import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.core.domain.util.DataException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.CancellationException

internal fun handleException(e: Throwable): Nothing {
    throw when (e) {
        is CancellationException -> e
        is HttpRequestTimeoutException -> DataException(DataError.Network.REQUEST_TIMEOUT)
        is ClientRequestException -> DataException(mapClientError(e))
        is RedirectResponseException -> DataException(DataError.Network.CONFLICT)
        is ServerResponseException -> DataException(DataError.Network.SERVER_ERROR)
        is ResponseException -> DataException(DataError.Network.UNKNOWN)
        else -> DataException(DataError.Network.UNKNOWN)
    }
}

fun mapClientError(e: ClientRequestException): DataError.Network {
    return when (e.response.status.value) {
        400 -> {
            if (e.response.status.description.contains("email not confirmed", ignoreCase = true)) {
                DataError.Network.EMAIL_NOT_CONFIRMED
            } else {
                DataError.Network.CONFLICT
            }
        }

        401 -> DataError.Network.UNAUTHORIZED
        403 -> DataError.Network.EMAIL_NOT_CONFIRMED
        413 -> DataError.Network.PAYLOAD_TOO_LARGE
        429 -> DataError.Network.TOO_MANY_REQUESTS
        else -> DataError.Network.UNKNOWN
    }
}
