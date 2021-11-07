package com.turtle.amatda.domain.repository

import com.turtle.amatda.domain.model.Resource
import com.turtle.amatda.domain.model.Tour
import com.turtle.amatda.domain.model.TourCode
import io.reactivex.Single

interface TourRepository {
    fun getTour(
        tourCode: TourCode
    ): Single<Resource<List<Tour>>>
}