package com.turtle.amatda.data.mapper

import com.turtle.amatda.data.util.Resource
import retrofit2.Response

interface ResponseMapper<T> {
    fun responseToMap(response: Response<T>): Resource<T>
}