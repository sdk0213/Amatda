package com.turtle.amatda.domain.repository

import com.turtle.amatda.data.util.Resource
import com.turtle.amatda.domain.model.Area
import com.turtle.amatda.domain.model.AreaCode
import com.turtle.amatda.domain.model.Tour
import com.turtle.amatda.domain.model.Weather
import io.reactivex.Single

interface TourRepository {
    fun getTour(
        areaCode: AreaCode
    ): Single<Resource<List<Tour>>>
}