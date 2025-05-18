package com.kaesik.tabletopwarhammer.core.domain.util

sealed class Resource<T, E : DataError>(val data: T?, val error: E? = null) {
    class Success<T, E : DataError>(data: T) : Resource<T, E>(data)
    class Error<T, E : DataError>(error: E) : Resource<T, E>(null, error)
}

inline fun <T, E : DataError, R> Resource<T, E>.map(transform: (T) -> R): Resource<R, E> {
    return when (this) {
        is Resource.Success -> Resource.Success(transform(this.data!!))
        is Resource.Error -> Resource.Error(this.error!!)
    }
}

fun <T, E : DataError> Resource<T, E>.asEmptyResult(): EmptyResult<E> {
    return this.map { Unit }
}

typealias EmptyResult<E> = Resource<Unit, E>
