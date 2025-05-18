package com.kaesik.tabletopwarhammer.library.data.library

import com.kaesik.tabletopwarhammer.library.domain.library.LibraryError
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.CancellationException

internal fun handleException(e: Throwable): Nothing {
    throw when (e) {
        is CancellationException -> e
        is ClientRequestException -> LibraryException(LibraryError.CLIENT_ERROR)
        is RedirectResponseException -> LibraryException(LibraryError.CLIENT_ERROR)
        is ServerResponseException -> LibraryException(LibraryError.SERVER_ERROR)
        is HttpRequestTimeoutException -> LibraryException(LibraryError.SERVICE_UNAVAILABLE)
        is ResponseException -> LibraryException(LibraryError.UNKNOWN_ERROR)
        else -> LibraryException(LibraryError.UNKNOWN_ERROR)
    }
}
