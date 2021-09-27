package com.turtle.amatda.domain.repository

import com.turtle.amatda.data.util.Resource
import com.turtle.amatda.domain.model.TourCode
import com.turtle.amatda.domain.model.Tour
import io.reactivex.Single

interface TourRepository {
    fun getTour(
        tourCode: TourCode
    ): Single<Resource<List<Tour>>>
}