package com.turtle.amatda.data.mapper

import com.turtle.amatda.data.util.Resource
import retrofit2.Response

interface ResponseMapper<E, D> {
    fun responseToResource(response: Response<E>): Resource<D>
}