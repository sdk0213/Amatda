package com.turtle.amatda.domain.model

sealed class Resource<T>(
    val data: T? = null,
    val code: Int? = null,
    val message: String? = null
) {
    class Success<T>(data: T, code : Int? = null, message: String? = null) : Resource<T>(data, code, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(data: T? = null, code : Int? = null, message: String?) : Resource<T>(data, code, message)
}


