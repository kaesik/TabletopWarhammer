package com.kaesik.tabletopwarhammer.core.domain.util

sealed interface DataError : Error {
    enum class Network : DataError {
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        CONFLICT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local : DataError {
        DISK_FULL,
        FILE_NOT_FOUND,
        FILE_CORRUPTED,
        PERMISSION_DENIED,
        INSUFFICIENT_STORAGE,
        INVALID_DATA_FORMAT,
        DATABASE_LOCKED,
        DATABASE_ERROR,
        UNKNOWN
    }
}
