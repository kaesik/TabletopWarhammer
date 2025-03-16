package com.kaesik.tabletopwarhammer.library.data.library

import com.kaesik.tabletopwarhammer.library.domain.library.LibraryError
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException

internal fun handleException(e: Exception): Nothing {
    when (e) {
        is ClientRequestException -> throw LibraryException(LibraryError.CLIENT_ERROR)
        is ServerResponseException -> throw LibraryException(LibraryError.SERVER_ERROR)
        is HttpRequestTimeoutException -> throw LibraryException(LibraryError.SERVICE_UNAVAILABLE)
        else -> throw LibraryException(LibraryError.UNKNOWN_ERROR)
    }
}
