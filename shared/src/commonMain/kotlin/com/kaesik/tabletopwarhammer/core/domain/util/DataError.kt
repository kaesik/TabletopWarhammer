package com.kaesik.tabletopwarhammer.core.domain.util

sealed interface DataError : Error {
    enum class Network : DataError {
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        EMAIL_NOT_CONFIRMED,
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

class DataException(val error: DataError) : Exception() {
    override val message: String
        get() = when (error) {
            is DataError.Network -> when (error) {
                DataError.Network.REQUEST_TIMEOUT -> "The request timed out."
                DataError.Network.UNAUTHORIZED -> "Unauthorized access."
                DataError.Network.EMAIL_NOT_CONFIRMED -> "Email address is not confirmed."
                DataError.Network.CONFLICT -> "Account already exists."
                DataError.Network.TOO_MANY_REQUESTS -> "Too many requests. Please try again later."
                DataError.Network.NO_INTERNET -> "No internet connection."
                DataError.Network.PAYLOAD_TOO_LARGE -> "The request payload is too large."
                DataError.Network.SERVER_ERROR -> "A server error occurred."
                DataError.Network.SERIALIZATION -> "Data serialization error."
                DataError.Network.UNKNOWN -> "An unknown network error occurred."
            }

            is DataError.Local -> when (error) {
                DataError.Local.DISK_FULL -> "Disk is full."
                DataError.Local.FILE_NOT_FOUND -> "File not found."
                DataError.Local.FILE_CORRUPTED -> "File is corrupted."
                DataError.Local.PERMISSION_DENIED -> "Permission denied."
                DataError.Local.INSUFFICIENT_STORAGE -> "Insufficient storage space."
                DataError.Local.INVALID_DATA_FORMAT -> "Invalid data format."
                DataError.Local.DATABASE_LOCKED -> "Database is locked."
                DataError.Local.DATABASE_ERROR -> "A database error occurred."
                DataError.Local.UNKNOWN -> "An unknown local error occurred."
            }
        }
}
