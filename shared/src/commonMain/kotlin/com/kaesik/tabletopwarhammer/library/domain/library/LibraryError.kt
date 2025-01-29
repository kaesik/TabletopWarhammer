package com.kaesik.tabletopwarhammer.library.domain.library

enum class LibraryError {
    SERVICE_UNAVAILABLE,
    CLIENT_ERROR,
    SERVER_ERROR,
    UNKNOWN_ERROR
}

class LibraryException(
    val error: LibraryError
) : Exception("An error occurred: $error")
