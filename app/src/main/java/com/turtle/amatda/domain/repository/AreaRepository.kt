package com.turtle.amatda.domain.repository

import com.turtle.amatda.domain.model.Area
import com.turtle.amatda.domain.model.Resource
import io.reactivex.Single

interface AreaRepository {
    fun getArea(areaCode: String): Single<Resource<List<Area>>>
}